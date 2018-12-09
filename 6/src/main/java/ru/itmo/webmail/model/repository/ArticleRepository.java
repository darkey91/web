package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    List<Article> findAll();
    Article findByTitle(String title);
    List<Article> findForUser(long userId);
    void hideOrShow(long id, boolean setHidden);
}
