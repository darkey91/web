package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.News;
import java.util.List;

public interface NewsRepository {
    public List<News> findAll();
    public void save(News news);
}
