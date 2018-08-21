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

    @Inject
    public EventRepository() {
    }

    public List<Event> getEventsToNotify() {
        List<Event> events = getActualEvents();

        removeEventsWithSentMessages(events);

        return events;
    }

    private List<Event> getActualEvents() {
        SqlSession session = SessionService.getSession();
        EventMapper mapper = session.getMapper(EventMapper.class);

        Date from = getCurrentDateMinusSchedulerPeriod();
        Date to = new Date();

        List<Event> events = mapper.selectInInterval(from, to);

        session.close();

        return events;
    }

    private void removeEventsWithSentMessages(List<Event> events) {
        events.removeIf(e -> e.getMessage() != null && e.getMessage().getSent());
    }

    private Date getCurrentDateMinusSchedulerPeriod() {
        return new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(Scheduler.PERIOD_IN_MINUTES));
    }
}
