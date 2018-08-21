package dao.mappers;

import dao.SessionService;
import message.Message;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageMapperTest {

    private SqlSession session;
    private MessageMapper messageMapper;
    private Message message;

    @Before
    public void setUp() {
        session = SessionService.getSession();
        messageMapper = session.getMapper(MessageMapper.class);

        insertNewMessage();
    }

    @After
    public void tearDown() {
        deleteMessage();
        session.close();
    }

    @Test
    public void create() {
        assertNotNull(message.getId());
    }

    @Test
    public void select() {
        Message createdMessage = messageMapper.selectById(message.getId());

        assertNotNull(createdMessage);
    }

    @Test
    public void update() {
        String newText = "new text";
        message.setText(newText);

        messageMapper.update(message);
        session.commit();

        Message dbMessage = messageMapper.selectById(message.getId());

        assertEquals(newText, dbMessage.getText());
    }

    @Test
    public void delete() {
        deleteMessage();

        Message dbMessage = messageMapper.selectById(message.getId());

        assertNull(dbMessage);
    }


    private void insertNewMessage() {
        message = new Message();
        message.setSubject("subject");
        message.setText("text");
        message.setRecipient("recipient@recipient.com");
        message.setSent(false);

        assertNull(message.getId());

        messageMapper.create(message);
        session.commit();

        assertNotNull(message.getId());
    }

    private void deleteMessage() {
        messageMapper.delete(message.getId());
        session.commit();
    }
}