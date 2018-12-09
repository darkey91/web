package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User find(long userId);

    User findByLogin(String login) ;

    User findByEmail(String email) ;

    User findByLoginOrEmailAndPasswordSha(String login, String passwordSha) ;

    List<User> findAll() ;

    void save(User user, String passwordSha);

    void confirmEmail(User user);

}
