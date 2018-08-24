package dao.repositories;

import event.Event;
import message.Message;
import utils.ArithmeticOperation;
import utils.TimeUtils;

import java.util.Date;

class TestEventsBuilder {

    private TimeUtils timeUtils;

    TestEventsBuilder(TimeUtils timeUtils) {
        this.timeUtils = timeUtils;
    }

    Event getActualEvent() {
        Event actualEvent = createEvent();
        actualEvent.setDate(new Date());

        return actualEvent;
    }

    Event getActualEventWithSentMessage() {
        Event event = getActualEvent();
        event.getMessage().setSent(true);

        return event;
    }

    Event getOldEvent() {
        Event actualEvent = createEvent();
        actualEvent.setDate(timeUtils.getModifiedCurrentDate(ArithmeticOperation.SUBTRACT, 30));

        return actualEvent;
    }

    Event getFutureEvent() {
        Event actualEvent = createEvent();
        actualEvent.setDate(timeUtils.getModifiedCurrentDate(ArithmeticOperation.ADD, 30));

        return actualEvent;
    }

    private Event createEvent() {
        Event event = new Event();
        event.setEmailNotification(true);
        event.setPushNotification(false);
        event.setMessage(getMessage());

        return event;
    }

    private Message getMessage() {
        Message message = new Message();
        message.setSent(false);
        message.setText("test");
        message.setRecipient("test@test.com");

        return message;
    }
}
