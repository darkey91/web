package ru.itmo.webmail.model.repository.impl;

import javafx.util.Pair;
import org.apache.commons.lang.RandomStringUtils;
import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EmailConfirmationRepository;
import ru.itmo.webmail.model.service.UserService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Random;

public class EmailConfirmationRepositoryImpl implements EmailConfirmationRepository {

    @Override
    public void saveConfirmation(User user) {
        String query = "INSERT INTO EmailConfirmation (userId, secret, creationTime) VALUES (?, ?, NOW())";
        String errorMessage = "Can't save secret link";
        String randomSecret = RandomStringUtils.random(new Random().nextInt(15) + 15, true, true);
        DatabaseUtils.executeUpdate(query, errorMessage, user.getId(), randomSecret);
    }

    @Override
    public long findUserIdBySecret(String secret) {
        String query = "SELECT * FROM EmailConfirmation WHERE secret=?";
        String errorMessage = "Can't find user by secret link";

        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage, secret);
            if (answer.getKey().next()) {
                return getUserId(answer.getValue(), answer.getKey());
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find by smth");
        }
    }

    private long getUserId(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if ("userId".equalsIgnoreCase(metaData.getColumnName(i))) {
                return resultSet.getLong(i);
            }
        }
        return -1;
    }


}
