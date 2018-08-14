package dao.repositories;

import dao.SessionService;
import dao.mappers.EventMapper;
import event.Event;
import org.apache.ibatis.session.SqlSession;
import scheduler.Scheduler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
public class EventRepository {

    private final SessionService sessionService;

    @Inject
    public EventRepository(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public List<Event> getEventsToNotify() {
        List<Event> events = getActualEvents();

        removeEventsWithSentMessages(events);

        return events;
    }

    private List<Event> getActualEvents() {
        try (SqlSession session = sessionService.getSession()) {

            EventMapper mapper = session.getMapper(EventMapper.class);

            Date from = getCurrentDateMinusSchedulerPeriod();
            Date to = new Date();

            return mapper.selectInInterval(from, to);
        }
    }

    private void removeEventsWithSentMessages(List<Event> events) {
        events.removeIf(e -> e.getMessage() != null && e.getMessage().getSent());
    }

    private Date getCurrentDateMinusSchedulerPeriod() {
        return new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(Scheduler.PERIOD_IN_MINUTES));
    }
}
