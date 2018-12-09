package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.*;

public class EventRepositoryImpl implements EventRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void saveEvent(long userId, String event) {
        String query = "INSERT INTO Event (userId, `type`, creationTime) VALUES (?, ?, NOW())";
        String errorMessage = "Can't save event";
        DatabaseUtils.executeUpdate(query, errorMessage, userId, event);
    }

}
