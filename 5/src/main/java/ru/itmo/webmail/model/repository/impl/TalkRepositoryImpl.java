package ru.itmo.webmail.model.repository.impl;

import javafx.util.Pair;
import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TalkRepositoryImpl implements TalkRepository {

    @Override
    public void saveMessage(Talk message) {
        String query = "INSERT INTO Talk (sourceUserId, targetUserId, text, creationTime) VALUES (?, ?, ?, NOW())";
        String errorMessage = "Can't save talk";

        try {
            ResultSet resultSet = DatabaseUtils.executeUpdate(query, errorMessage, message.getSourceUserId(), message.getTargetUserId(), message.getText());
            if (resultSet.next()) {
                message.setId(resultSet.getLong(1));
                message.setCreationTime(findCreationTime(message.getId()));
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
    }

    @Override
    public List<Talk> findAllForUser(long userId) {
        List<Talk> talks = new ArrayList<>();

        String query = "SELECT * FROM Talk WHERE (sourceUserId=? OR targetUserId=?)  ORDER BY creationTime DESC";
        String errorMessage = "Can't find conversations";
        try {
            Pair<ResultSet, ResultSetMetaData> answer = DatabaseUtils.executeQuery(query, errorMessage, Long.toString(userId), Long.toString(userId));
            while (answer.getKey().next()) {
                talks.add(toTalk(answer.getValue(), answer.getKey()));
            }
        } catch (SQLException e) {
            throw new RepositoryException(errorMessage, e);
        }
        return talks;
    }

    private Date findCreationTime(long messageId) {
        String query = "SELECT creationTime FROM Talk WHERE id=?";
        String errorMessage = "Can't find record with messageId";
        return DatabaseUtils.findCreationTime(messageId, query, errorMessage);
    }

    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                talk.setId(resultSet.getLong(i));
            } else if ("sourceUserId".equalsIgnoreCase(columnName)) {
                talk.setSourceUserId(resultSet.getLong(i));
            } else if ("targetUserId".equalsIgnoreCase(columnName)) {
                talk.setTargetUserId(resultSet.getLong(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                talk.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                talk.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return talk;
    }


}
