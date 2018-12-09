package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private EventRepository eventRepository = new EventRepositoryImpl();

    public void saveEvent(long id, String event) {
        eventRepository.saveEvent(id, event);
    }
}
