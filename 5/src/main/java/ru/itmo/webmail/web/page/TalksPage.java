package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() != null)
            view.put("messages", getTalkService().findAllForUser(getUser().getId()));
    }

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }
    private void send(HttpServletRequest request, Map<String, Object> view) {
        String text = request.getParameter("text");
        String targetLogin = request.getParameter("targetLogin");

        if (!text.isEmpty()) {
            Talk talk = new Talk();
            talk.setText(text);
            talk.setSourceUserId(getUser().getId());

            try {
                talk.setTargetUserId(getUserService().findByLogin(targetLogin).getId());
            } catch (NullPointerException e) {
                view.put("text", text);
                view.put("error", "User " + targetLogin + " wasn't found");
                return;
            }

            getTalkService().saveMessage(talk);
        }
        throw new RedirectException("/talks", "action");
    }

}
