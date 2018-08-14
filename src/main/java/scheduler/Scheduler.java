package scheduler;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    public static final Integer PERIOD_IN_MINUTES = 1;

    private final SendTask task;

    @Inject
    Scheduler(SendTask task) {
        this.task = task;
    }

    public void schedule() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Integer initialDelayInMinutes = 0;
        scheduler.scheduleAtFixedRate(task, initialDelayInMinutes, PERIOD_IN_MINUTES, TimeUnit.MINUTES);
    }
}
