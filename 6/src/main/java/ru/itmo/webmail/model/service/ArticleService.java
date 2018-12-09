package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        if (article.getText().isEmpty()) {
            throw new ValidationException("Text can't be empty.");
        }

        if (article.getTitle().isEmpty()) {
            throw new ValidationException("Article must have a title.");
        }

        if (articleRepository.findByTitle(article.getTitle()) != null) {
            throw new ValidationException("There is an article with such title. Please, write new title.");
        }

    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }
    public List<Article> findAll() { return articleRepository.findAll(); }
    public List<Article> findForUser(long userId) { return articleRepository.findForUser(userId); }
    public void hideOrShow(long id, boolean setHidden) {
         articleRepository.hideOrShow(id, setHidden);
    }

}
