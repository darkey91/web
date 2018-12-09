package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    public void action(HttpServletRequest request, Map<String, Object> view) {
        //nothing
    }

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    public Map<String, Object> create(HttpServletRequest request, Map<String, Object> view) {
        before(request, view);
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setHidden(false);

        try {
            getArticleService().validateArticle(article);
        } catch (ValidationException e) {
            view.put("text", article.getText());
            view.put("title", article.getTitle());
            view.put("error", e.getMessage());
            view.put("success", false);
            return view;
        }

        article.setUserId(getUser().getId());
         getArticleService().saveArticle(article);
        view.put("success", true );
        return view;
    }
}
