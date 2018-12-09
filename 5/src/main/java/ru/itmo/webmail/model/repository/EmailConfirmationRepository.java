package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

public interface EmailConfirmationRepository {

    void saveConfirmation(User user);

    long findUserIdBySecret(String secret);
}
