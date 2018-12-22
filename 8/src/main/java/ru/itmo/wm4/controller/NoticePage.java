package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wm4.domain.Comment;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Role;
import ru.itmo.wm4.security.AnyRole;
import ru.itmo.wm4.security.Guest;
import ru.itmo.wm4.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @AnyRole({Role.Name.USER, Role.Name.ADMIN})
    @GetMapping(path = "/notice")
    public String noticeGet(Model model) {
        model.addAttribute("notice", new Notice());
        return "CreateNoticePage";
    }

    @AnyRole(Role.Name.ADMIN)
    @PostMapping(path = "/notice")
    public String noticePost(@Valid @ModelAttribute("notice") Notice notice,
                             @RequestParam("tagsString") String tagsParameter,
                             BindingResult bindingResult, HttpSession httpSession,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "CreateNoticePage";
        }
        if (notice.getText().isEmpty()) {
            model.addAttribute("textError", "Text can't be empty");
            return "CreateNoticePage";
        }
        if (tagsParameter.isEmpty()) {
            model.addAttribute("tagError", "String can't be empty");
            return "CreateNoticePage";
        }

        String[] tags = tagsParameter.split(" ");

        for (int i = 0; i < tags.length; ++i) {
            if (!tags[i].matches("[a-z]*")) {
                model.addAttribute("tagError", "String can contain lowercase latin letter");
                return "CreateNoticePage";
            }
            notice.addTag(getTagService().makeTag(tags[i]));
        }

        getNoticeService().save(notice, getUser(httpSession));
        return "redirect:/notices";
    }

    @Guest
    @GetMapping(path = "notice/{noticeId}")
    public String getCurrentNotice(@PathVariable Long noticeId, Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("notice", noticeService.findById(noticeId));
        return "NoticePage";
    }

    @AnyRole({Role.Name.ADMIN, Role.Name.USER})
    @PostMapping(path = "notice/{noticeId}")
    public String addComment(@PathVariable("noticeId") Long id,
                             @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult bindingResult, HttpSession session, Model model) {
        if (!bindingResult.hasErrors()) {
            getCommentService().save(comment, getNoticeService().findById(id), getUser(session));
        } else if (comment.getText().isEmpty()) {
            model.addAttribute("commentError", "Comment can't be empty");
        }

        return "redirect:/notice/{noticeId}";
    }

}
