package ru.itmo.webmail.web.page;

import javafx.util.Pair;
import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private List<Pair<Article, String>> find(HttpServletRequest request, Map<String, Object> view) {
        List<Article> articles = getArticleService().findAll();
        List<Pair<Article, String>> articlesAndUsers = new ArrayList<>();

        for (int i = 0; i < articles.size(); ++i) {
            String userName = getUserService().find(articles.get(i).getUserId()).getLogin();
            Pair<Article, String> pair = new Pair<Article, String> (articles.get(i), userName);
            articlesAndUsers.add(pair);
        }
        return articlesAndUsers;
    }

}
