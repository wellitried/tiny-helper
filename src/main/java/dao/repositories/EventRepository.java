package dao.repositories;

import dao.SessionService;
import dao.mappers.EventMapper;
import event.Event;
import org.apache.ibatis.session.SqlSession;
import scheduler.Scheduler;
import utils.ArithmeticOperation;
import utils.TimeUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;

@Singleton
public class EventRepository {

    private final TimeUtils timeUtils;

    @Inject
    public EventRepository(TimeUtils timeUtils) {
        this.timeUtils = timeUtils;
    }

    public List<Event> getEventsToNotify() {
        List<Event> events = getActualEvents();

        removeEventsWithSentMessages(events);

        return events;
    }

    private List<Event> getActualEvents() {
        SqlSession session = SessionService.getSession();
        EventMapper mapper = session.getMapper(EventMapper.class);

        Date from = timeUtils.getModifiedCurrentDate(ArithmeticOperation.SUBTRACT, Scheduler.PERIOD_IN_MINUTES);
        Date to = new Date();

        List<Event> events = mapper.selectInInterval(from, to);

        session.close();

        return events;
    }

    private void removeEventsWithSentMessages(List<Event> events) {
        events.removeIf(e -> e.getMessage() != null && e.getMessage().getSent());
    }
}
