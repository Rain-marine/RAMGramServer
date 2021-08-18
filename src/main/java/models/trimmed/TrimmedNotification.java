package models.trimmed;

import models.types.NotificationType;
import repository.Repositories;

public class TrimmedNotification implements Repositories {

    private long id;
    private String sender;
    private String receiver;
    private String message;
    private NotificationType type;

    public TrimmedNotification() {
    }

    public TrimmedNotification(long id) {
        this.id = id;
        this.sender = NOTIFICATION_REPOSITORY.getById(id).getSender().getUsername();
        this.receiver = NOTIFICATION_REPOSITORY.getById(id).getReceiver().getUsername();
        this.message = NOTIFICATION_REPOSITORY.getById(id).getMessage();
        this.type = NOTIFICATION_REPOSITORY.getById(id).getType();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
