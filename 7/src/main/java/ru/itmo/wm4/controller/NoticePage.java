package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.form.validator.NoticeValidator;
import ru.itmo.wm4.service.NoticeService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeValidator noticeValidator;

    public NoticePage(NoticeService noticeService, NoticeValidator noticeValidator) {
        this.noticeService = noticeService;
        this.noticeValidator = noticeValidator;
    }

    @PostMapping(path = "/save")
    public String addNoticePost(@Valid @ModelAttribute("notice") Notice notice,
                               BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.save(notice);
        return "redirect:/";
    }

    @GetMapping(path="/save")
    public String addNoticeGet(Model model) {
        return "NoticePage";
    }

    @GetMapping(path = "/notice")
    public String getNoticePage(Model model) {
        model.addAttribute("notice", new Notice());
        return "NoticePage";
    }


    @InitBinder("notice")
    public void initNoticeBinder(@RequestParam("notice") WebDataBinder binder) {
        binder.addValidators(noticeValidator);
    }

}
