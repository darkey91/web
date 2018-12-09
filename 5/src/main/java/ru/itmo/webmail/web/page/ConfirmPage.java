package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {

    private void action(HttpServletRequest request, Map<String, Object> view) throws ServletException {
        String secret = request.getParameter("secret");
        long userId = getEmailConfirmationService().findUserIdBySecret(secret);

        if (userId != -1) {
            User user = new User();
            user.setId(userId);
            getUserService().confirmEmail(user);
            throw new RedirectException("/index", "successfulConfirmation");
        } else {
            throw new RedirectException("/notfoundpage");
        }
    }
}

