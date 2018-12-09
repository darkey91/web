package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page {
    public void before (HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }

    public void after (HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
    }

    private void action() {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private void entranceDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have successfully entered!");
    }

    private void newsPublished(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have successfully published your news!");
    }

    private void registerBefore(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "Register before!");
    }
}
