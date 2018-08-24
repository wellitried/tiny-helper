package utils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Singleton
public class TimeUtils {

    @Inject
    public TimeUtils() {
    }

    public Date getModifiedCurrentDate(ArithmeticOperation operation, int minutes) {

        long minutesInMillis = TimeUnit.MINUTES.toMillis(minutes);

        switch (operation) {
            case ADD:
                return new Date(System.currentTimeMillis() + minutesInMillis);
            case SUBTRACT:
                return new Date(System.currentTimeMillis() - minutesInMillis);
            default:
                throw new IllegalStateException();
        }
    }

}
