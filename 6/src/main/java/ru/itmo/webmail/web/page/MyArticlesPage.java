package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class MyArticlesPage extends Page {
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        List<Article> myArticles = getArticleService().findForUser(getUser().getId());
        if (!myArticles.isEmpty()) {
            view.put("myArticles", myArticles);
        }
    }

    private void hideShow(HttpServletRequest request, Map<String, Object> view) {
        String id = request.getParameter("articleId");
        id = id.replaceAll("\\s+", "");
        String userId = request.getParameter("userId");
        userId = userId.replaceAll("\\s+", "");
        if (getUser().getId() == Long.parseLong(userId)) {
            long articleId = Long.parseLong(id);
            boolean setHidden = Boolean.parseBoolean(request.getParameter("setHidden"));

            getArticleService().hideOrShow(articleId, setHidden);
        } else {
            //так не бывает
        }

    }
}
