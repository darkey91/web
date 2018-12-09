package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.EmailConfirmationRepository;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.EmailConfirmationRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("This email is already used");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }

    }

    public void register(User user, String password) {
        String passwordSha = getPasswordSha(password);
        userRepository.save(user, passwordSha);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String login, String password) throws ValidationException {
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login is required");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }

        if (login.length() > 30) {
            throw new ValidationException("Login can't be longer than 8");
        }

        User user = userRepository.findByLoginOrEmailAndPasswordSha(login, getPasswordSha(password));

        if (user == null) {
            throw new ValidationException("Invalid login or password");

        }
        if (!user.isConfirmed()) {
            throw new ValidationException("Confirm your email!");
        }

    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
    }

    public User authorize(String loginOrEmail, String password) {
        return userRepository.findByLoginOrEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
    }

    public User find(long userId) {
        return userRepository.find(userId);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void confirmEmail(User user) {
        userRepository.confirmEmail(user);
    }
}

