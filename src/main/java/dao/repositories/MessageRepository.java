package dao.repositories;

import com.google.common.base.Preconditions;
import dao.SessionService;
import dao.mappers.MessageMapper;
import message.Message;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageRepository {

    @Inject
    public MessageRepository() {

    }

    public void markMessageAsSent(Message message) {
        Preconditions.checkNotNull(message);

        message.setSent(true);

        SqlSession session = SessionService.getSession();
        MessageMapper mapper = session.getMapper(MessageMapper.class);

        mapper.update(message);

        session.commit();
        session.close();
    }

}
