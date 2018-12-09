package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterPage extends Page {
    private UserService userService = new UserService();

    private void register(HttpServletRequest request, Map<String, Object> view) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
       user.setEmail(request.getParameter("email"));

        try {
            userService.validateRegistration(user, password, passwordConfirmation);
        } catch (ValidationException e) {
            view.put("login", user.getLogin());
            view.put("password", password);
            view.put("error", e.getMessage());
            return;
        }

        userService.register(user, password);
        request.getSession().setAttribute("user", user.getLogin());
        throw new RedirectException("/index", "registrationDone");
    }

    public void before (HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }

     public void after (HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
    }

    private void action() {
        // No operations.
    }
}
