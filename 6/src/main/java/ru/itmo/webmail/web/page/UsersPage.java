package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.web.exception.RedirectException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class UsersPage extends Page{
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index");
        }
        view.put("user", getUser());
    }

    private List<User> find(HttpServletRequest request, Map<String, Object> view) {
        return getUserService().findAll();
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("user", getUser());
        // No operations.
    }

    private boolean changePermission(HttpServletRequest request, Map<String, Object> view) {
        long userId = Long.parseLong(request.getParameter("userId"));
        boolean value = request.getParameter("value").equals("1") ;
        if (!getUser().admin() ) return false;
        getUserService().changePermission(userId, value);

        return getUser().getId() != userId;
    }
}
