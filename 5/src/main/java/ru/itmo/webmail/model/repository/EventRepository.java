package ru.itmo.webmail.model.repository;

import java.sql.SQLException;

public interface EventRepository {
    public void saveEvent(long id, String event) ;
}
