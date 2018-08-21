package dao.mappers;

import dao.SessionService;
import event.Event;
import message.Message;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class EventMapperTest {

    private SqlSession session;
    private EventMapper eventMapper;
    private Event event;

    @Before
    public void setUp() {
        session = SessionService.getSession();
        eventMapper = session.getMapper(EventMapper.class);

        insertNewEvent();
    }

    @After
    public void tearDown() {
        deleteEvent();
        session.close();
    }

    @Test
    public void createWithMessage() {
        assertNotNull(event.getId());
    }

    @Test
    public void createWithoutMessage() {
        deleteEvent();
        event.setId(null);
        event.setMessage(null);

        eventMapper.create(event);

        assertNotNull(event.getId());
    }

    @Test
    public void selectInInterval() {
        Date from = getCurrentTimeMinusOneMinute();
        Date to = new Date();

        List<Event> events = eventMapper.selectInInterval(from, to);
        Date createdEventDate = getOurEventDate(events);

        assertTrue(from.before(createdEventDate));
        assertTrue(to.after(createdEventDate));
    }

    @Test
    public void selectById() {
        Event createdEvent = eventMapper.selectById(event.getId());

        assertNotNull(createdEvent);
    }

    @Test
    public void update() {
        boolean newValue = false;
        event.setEmailNotification(newValue);

        eventMapper.update(event);
        session.commit();

        Event createdEvent = eventMapper.selectById(event.getId());

        assertEquals(newValue, createdEvent.isEmailNotification());
    }

    @Test
    public void delete() {
        deleteEvent();

        Event createdEvent = eventMapper.selectById(event.getId());

        assertNull(createdEvent);
    }


    private void deleteEvent() {
        eventMapper.deleteWithMessage(event);
        session.commit();
    }

    private void insertNewEvent() {
        event = new Event();
        event.setDate(new Date());
        event.setEmailNotification(true);
        event.setPushNotification(true);
        event.setMessage(getMessage());

        assertNull(event.getId());

        eventMapper.createWithMessage(event);
        session.commit();

        assertNotNull(event);
    }

    private Message getMessage() {
        Message message = new Message();
        message.setText("event test");

        return message;
    }

    private Date getOurEventDate(List<Event> events) {
        Event createdEvent = events.stream()
                .filter(e -> Objects.equals(e.getId(), event.getId()))
                .findFirst()
                .get();

        return createdEvent.getDate();
    }

    private Date getCurrentTimeMinusOneMinute() {
        long intervalInMinutes = 1;
        Date date = new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(intervalInMinutes));

        return date;
    }
}