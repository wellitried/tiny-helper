package dao.repositories;

import dao.SessionService;
import dao.mappers.EventMapper;
import event.Event;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
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


    public List<Event> getActualEvents() {

        List<Event> events = new ArrayList<>();

        Date from = getCurrentDateMinusInterval();
        Date to = new Date();

        try (SqlSession session = sessionService.getSession()) {
            EventMapper mapper = session.getMapper(EventMapper.class);

            events = mapper.selectInInterval(from, to);
        }

        return events;
    }

    private Date getCurrentDateMinusInterval() {
        return new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(Event.INTERVAL_LENGTH));
    }
}
