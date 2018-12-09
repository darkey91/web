package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wm4.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/{userId}")
    public String main(Model model, @PathVariable String userId) {
        long id = Long.parseLong(userId);
        model.addAttribute("userInfo", userService.findById(id));
        return "UserPage";
    }
}
