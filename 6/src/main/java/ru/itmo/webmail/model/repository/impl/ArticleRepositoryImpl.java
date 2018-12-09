package ru.itmo.webmail.model.repository.impl;

import javafx.util.Pair;
import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.ArticleRepository;


import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository {

    @Override
    public void save(Article article) {
        String query = "INSERT INTO `Article` (userId, title, text, creationTime, isHidden) VALUES (?, ?, ?, NOW(), false)";
        String errorMessage = "Can't save article.";

        try {
            ResultSet resultSet = DatabaseUtils.executeUpdate(query, errorMessage, article.getUserId(), article.getTitle(), article.getText());
            if (resultSet.next()) {
                article.setId(resultSet.getLong(1));
                article.setCreationTime(findCreationTime(article.getId()));
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
    }

    @Override
    public List<Article> findForUser(long userId) {
        List<Article> myArticles = new ArrayList<>();
        String query = "SELECT * FROM Article WHERE userId=?";
        String errorMessage = "Can't find Article by userId";
        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage, userId);
            while (answer.getKey().next()) {
                myArticles.add(toArticle(answer.getValue(), answer.getKey()));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find by userId");
        }
        return myArticles;
    }

    @Override
    public List<Article> findAll() {
        List<Article> users = new ArrayList<>();
        String query = "SELECT * FROM Article WHERE `isHidden`=false ORDER BY creationTime DESC";
        String errorMessage = "Can't find articles";
        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage);
            while (answer.getKey().next()) {
                users.add(toArticle(answer.getValue(), answer.getKey()));
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
        return users;
    }

    @Override
    public Article findByTitle(String title) {
        String query = "SELECT * FROM Article WHERE title=?";
        String errorMessage = "Can't find Article by title";
        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage, title);
            if (answer.getKey().next()) {
                return toArticle(answer.getValue(), answer.getKey());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find by title");
        }
    }

    @Override
    public void hideOrShow(long id, boolean setHidden) {
        String query = "UPDATE Article SET isHidden=? WHERE id=?";
        String errorMessage = "Can't hide/show article";
        DatabaseUtils.executeUpdate(query, errorMessage, setHidden, id);
    }

    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Article article = new Article();

        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                article.setId(resultSet.getLong(i));
            } else if ("userId".equalsIgnoreCase(columnName)) {
                article.setUserId(resultSet.getLong(i));
            } else if ("title".equalsIgnoreCase(columnName)) {
                article.setTitle(resultSet.getString(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                article.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                article.setCreationTime(resultSet.getTimestamp(i));
            } else if ("isHidden".equalsIgnoreCase(columnName)) {
                article.setHidden(resultSet.getBoolean(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return article;
    }

    private Date findCreationTime(long articleId) {
        return DatabaseUtils.findCreationTime(articleId, "Article");
    }
}
