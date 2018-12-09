package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.impl.NewsRepositoryImpl;
import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

class Page {
    private UserService userService = new UserService();
    private NewsRepository newsRepository = new NewsRepositoryImpl();

     void before(HttpServletRequest request, Map<String, Object> view) {
         view.put("userService", userService);
         view.put("newsList", newsRepository.findAll());
         view.put("usersCount", userService.findCount());
         if (request.getSession().getAttribute("user") != null)
             view.put("user", request.getSession().getAttribute("user"));
    }

     void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("usersCount", userService.findCount());

    }
}


/*Зачем вообще before & after одновременно, если они одинаковые
Почему их надо юзать до и после action?
 */