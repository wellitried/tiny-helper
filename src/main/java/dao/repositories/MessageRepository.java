package dao.repositories;

import dao.SessionService;
import dao.mappers.MessageMapper;
import message.Message;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageRepository {

    private final SessionService sessionService;

    @Inject
    public MessageRepository(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void markMessageAsSent(Message message) {
        try (SqlSession session = sessionService.getSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);

            mapper.setSentAsTrue(message);
            session.commit();
        }
    }

}
