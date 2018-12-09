package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface TalkRepository {
    void saveMessage(Talk message) ;
    List<Talk> findAllForUser(long userId);
}
