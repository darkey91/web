package ru.itmo.webmail.model.repository.impl;

import javafx.util.Pair;
import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public User find(long userId) {
        return findBySmth("id", Long.toString(userId));
    }

    @Override
    public User findByLogin(String login) {
        return findBySmth("login", login);
    }


    @Override
    public User findByEmail(String email) {
        return findBySmth("email", email);
    }

    private User findBySmth(String smthWord, String smth) {
        String query = "SELECT * FROM User WHERE " + smthWord + "=?";
        String errorMessage = "Can't find User by " + smthWord;
        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage, smth);
            if (answer.getKey().next()) {
                return toUser(answer.getValue(), answer.getKey());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find by smth");
        }
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        String query = "SELECT * FROM User WHERE passwordSha=? AND (login=? OR email=?)";
        String errorMessage = "Can't find User by login or email";
        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage, passwordSha, loginOrEmail, loginOrEmail);
            if (answer.getKey().next()) {
                return toUser(answer.getValue(), answer.getKey());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by id and passwordSha.", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User ORDER BY id";
        String errorMessage = "Can't find users";
        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage);
            while (answer.getKey().next()) {
                users.add(toUser(answer.getValue(), answer.getKey()));
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
        return users;
    }


    @Override
    public void confirmEmail(User user) {
        String query = "UPDATE User SET confirmed=? WHERE id=?";
        String errorMessage = "Can't confirm email";
        DatabaseUtils.executeUpdate(query, errorMessage, true, user.getId());
        user.setConfirmed(true);
    }

    @Override
    public void save(User user, String passwordSha) {
        String query = "INSERT INTO User (login, passwordSha,email, creationTime) VALUES (?, ?, ?, NOW())";
        String errorMessage = "Can't save user";

        try {
            ResultSet resultSet = DatabaseUtils.executeUpdate(query, errorMessage, user.getLogin(), passwordSha, user.getEmail());
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setCreationTime(findCreationTime(user.getId()));
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
    }

    private Date findCreationTime(long userId) {
        String query = "SELECT creationTIme  FROM User WHERE id=?";
        String errorMessage = "Can't set time of creation";
        return DatabaseUtils.findCreationTime(userId, query, errorMessage);
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                user.setId(resultSet.getLong(i));
            } else if ("login".equalsIgnoreCase(columnName)) {
                user.setLogin(resultSet.getString(i));
            } else if ("passwordSha".equalsIgnoreCase(columnName)) {
                // No operations.
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                user.setCreationTime(resultSet.getTimestamp(i));
            } else if ("email".equalsIgnoreCase(columnName)) {
                user.setEmail(resultSet.getString(i));
            } else if ("confirmed".equalsIgnoreCase(columnName)) {
                user.setConfirmed(resultSet.getBoolean(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return user;
    }
}
