package scheduler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class Scheduler {

    public static final Integer PERIOD_IN_MINUTES = 1;

    private final EmailNotificationsSendingTask task;

    @Inject
    Scheduler(EmailNotificationsSendingTask task) {
        this.task = task;
    }

    public void schedule() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        int initialDelayInMinutes = 0;
        scheduler.scheduleAtFixedRate(task, initialDelayInMinutes, PERIOD_IN_MINUTES, TimeUnit.MINUTES);
    }
}
