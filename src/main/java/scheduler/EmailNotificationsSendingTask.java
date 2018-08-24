package scheduler;

import dao.repositories.EventRepository;
import email.MailSender;
import event.Event;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class EmailNotificationsSendingTask implements Runnable {

    private final EventRepository eventRepository;
    private final MailSender mailSender;

    @Inject
    EmailNotificationsSendingTask(EventRepository eventRepository, MailSender mailSender) {
        this.eventRepository = eventRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void run() {
        List<Event> actualEvents = eventRepository.getEventsToNotify();

        actualEvents.stream()
                .filter(Event::isEmailNotification)
                .forEach(e -> mailSender.sendMessage(e.getMessage()));
    }
}
