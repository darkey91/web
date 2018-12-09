package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface UserRepository {
    public long getId(String user);
    void save(User user);
    User findByLogin(String login);
    List<User> findAll();
    User findByEmail(String email);
    User findById(long id);
    int findCount();
}
