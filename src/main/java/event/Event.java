package event;

import com.google.common.base.Objects;
import message.Message;

import java.util.Date;

public class Event {

    private Long id;
    private Date date;
    private boolean emailNotification;
    private boolean pushNotification;
    private Message message;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", emailNotification=" + emailNotification +
                ", pushNotification=" + pushNotification +
                ", message=" + message +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equal(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
