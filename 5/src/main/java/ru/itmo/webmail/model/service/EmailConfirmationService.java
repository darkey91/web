package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.EmailConfirmationRepository;
import ru.itmo.webmail.model.repository.impl.EmailConfirmationRepositoryImpl;

public class EmailConfirmationService {
    private EmailConfirmationRepository emailConfirmationRepository = new EmailConfirmationRepositoryImpl();

    public void saveConfirmation(User user) {
        emailConfirmationRepository.saveConfirmation(user);
    }

    public long findUserIdBySecret(String secret) {
        return emailConfirmationRepository.findUserIdBySecret(secret);
    }


}
