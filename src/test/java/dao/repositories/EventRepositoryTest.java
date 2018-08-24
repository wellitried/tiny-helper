package dao.repositories;

import dao.SessionService;
import dao.mappers.EventMapper;
import event.Event;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.TimeUtils;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventRepositoryTest {

    private SqlSession session;

    @Before
    public void setUp() {
        session = SessionService.getSession();
    }

    @After
    public void tearDown() {
        session.close();
    }

    @Test
    public void getEventsToNotify() {
        TestEventsBuilder eventsBuilder = new TestEventsBuilder(new TimeUtils());

        Event actualEvent = eventsBuilder.getActualEvent();
        Event actualEventWithSentMessage = eventsBuilder.getActualEventWithSentMessage();
        Event oldEvent = eventsBuilder.getOldEvent();
        Event futureEvent = eventsBuilder.getFutureEvent();

        insertEvents(actualEvent, actualEventWithSentMessage, oldEvent, futureEvent);

        EventRepository eventRepository = new EventRepository(new TimeUtils());
        List<Event> events = eventRepository.getEventsToNotify();

        assertTrue(events.contains(actualEvent));
        assertFalse(events.contains(actualEventWithSentMessage));
        assertFalse(events.contains(oldEvent));
        assertFalse(events.contains(futureEvent));

        deleteEvents(actualEvent, actualEventWithSentMessage, oldEvent, futureEvent);
    }

    private void insertEvents(Event... events) {
        EventMapper eventMapper = session.getMapper(EventMapper.class);

        eventMapper.createWithMessage(events);
        session.commit();
    }

    private void deleteEvents(Event... events) {
        EventMapper eventMapper = session.getMapper(EventMapper.class);

        eventMapper.deleteWithMessage(events);
        session.commit();
    }

}