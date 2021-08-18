package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.Notification;
import models.responses.BooleanResponse;
import models.responses.NotificationResponse;
import models.responses.Response;
import models.responses.TweetResponse;
import models.trimmed.TrimmedTweet;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("notification")
public class NotificationRequest implements Request , Controllers {

    public enum TYPE {SYSTEM , REQUESTS , REQ_TO_ME}

    private String token;
    private long userId;
    private TYPE type;

    public NotificationRequest() {
    }

    public NotificationRequest(String token, long userId, TYPE type) {
        this.token = token;
        this.userId = userId;
        this.type = type;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            List<Notification> notifications = new ArrayList<>();
            switch (type){
                case SYSTEM -> {
                    return new NotificationResponse(NOTIFICATION_CONTROLLER.getSystemNotification(userId));
                }
                case REQUESTS -> {
                    return new NotificationResponse(NOTIFICATION_CONTROLLER.getYourFollowingRequestNotification(userId));
                }
                case REQ_TO_ME -> {
                    return new NotificationResponse(NOTIFICATION_CONTROLLER.getFollowingRequestsNotifications(userId));
                }
                default -> {
                    return new BooleanResponse(true);
                }
            }
        }
        else
            return new BooleanResponse(false);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
}
