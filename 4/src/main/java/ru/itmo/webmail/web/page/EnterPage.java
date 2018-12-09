package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {
    private UserService userService = new UserService();

    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            userService.validateEntrance(login, password);
        } catch (ValidationException e) {
            view.put("login", login);
            view.put("password", password);
            view.put("error", e.getMessage());
            return;
        }

        User user = new User();
        user.setLogin(login);
        request.getSession().setAttribute("user", user.getLogin());
        throw new RedirectException("/index", "entranceDone");
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
    }

    private void action() {
        // No operations.
    }
}
