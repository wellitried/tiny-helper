package dao.repositories;

import dao.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageRepository {

    private final SessionService sessionService;


    @Inject
    public MessageRepository(SessionService sessionService) {
        this.sessionService = sessionService;
    }

}
