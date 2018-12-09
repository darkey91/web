package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.impl.NewsRepositoryImpl;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class AddnewsPage extends Page {
    private NewsRepository newsRepository = new NewsRepositoryImpl();
    private UserService userService = new UserService();

    public void action() {
        //no operations
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
    }

    public void addnews(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        String newsText = request.getParameter("news");

        try {
            long userId = userService.getIdByLogin(user.toString());
            News news = new News(userId, newsText, user.toString());
            newsRepository.save(news);
            throw new RedirectException("/index", "newsPublished");
        } catch (NullPointerException e) {
            throw new RedirectException( "/index", "registerBefore");
        }
    }


}
