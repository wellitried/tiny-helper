package dao.repositories;

import dao.SessionService;
import dao.mappers.MessageMapper;
import message.Message;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageRepositoryTest {

    private SqlSession session;
    private MessageMapper messageMapper;
    private Message message;

    @Before
    public void setUp() {
        session = SessionService.getSession();
        messageMapper = session.getMapper(MessageMapper.class);

        message = getMessage();
        messageMapper.create(message);
        session.commit();
    }

    @After
    public void tearDown() {
        messageMapper.delete(message.getId());
        session.commit();
        session.close();
    }

    @Test
    public void markMessageAsSent() {
        assertNotNull(message.getId());
        assertFalse(message.getSent());

        MessageRepository messageRepository = new MessageRepository();
        messageRepository.markMessageAsSent(message);

        Message actualMessage = messageMapper.selectById(message.getId());

        assertTrue(actualMessage.getSent());
    }

    private Message getMessage() {
        Message message = new Message();
        message.setSent(false);
        message.setRecipient("test@test.com");
        message.setText("test");

        return message;
    }
}