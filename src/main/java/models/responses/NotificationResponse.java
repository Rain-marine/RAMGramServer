package models.responses;

import models.Notification;
import models.trimmed.TrimmedNotification;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("notification")
public class NotificationResponse implements Response {

    private List<TrimmedNotification> trimmedNotifications = new ArrayList<>();



    public NotificationResponse(List<Notification> notifications) {
        for (Notification notification : notifications) {
            trimmedNotifications.add(new TrimmedNotification(notification.getId()));
        }
    }

    @Override
    public void unleash() {

    }

    public NotificationResponse() {
    }

    public List<TrimmedNotification> getTrimmedNotifications() {
        return trimmedNotifications;
    }

    public void setTrimmedNotifications(List<TrimmedNotification> trimmedNotifications) {
        this.trimmedNotifications = trimmedNotifications;
    }
}
