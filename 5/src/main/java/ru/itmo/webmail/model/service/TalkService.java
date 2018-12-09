package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.repository.TalkRepository;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private TalkRepository talkRepository = new TalkRepositoryImpl();

    public List<Talk> findAllForUser(long userId) {
        return talkRepository.findAllForUser(userId);
    }

    public void saveMessage(Talk talk) {
        talkRepository.saveMessage(talk);
    }
}
