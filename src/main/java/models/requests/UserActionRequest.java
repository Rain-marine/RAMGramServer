package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("userAction")
public class UserActionRequest implements Request, Controllers {

    public enum USER_ACTION {MUTE , BLOCK , REPORT , UNBLOCK , FOLLOW , UNFOLLOW , QUIET_UNFOLLOW , REQUEST , ACCEPT , REJECT , DELETE_REQUEST , QUIET_REJECT, DELETE_NOTIF}

    private String token;
    private long userId;
    private long otherId;
    private USER_ACTION action;

    public UserActionRequest(String token, long userId, long otherUserId, USER_ACTION action) {
        this.token = token;
        this.userId = userId;
        this.otherId = otherUserId;
        this.action = action;
    }

    public UserActionRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            switch (action){
                case MUTE -> USER_CONTROLLER.muteUser(otherId, userId);
                case BLOCK -> USER_CONTROLLER.blockUser(otherId, userId);
                case REPORT -> USER_CONTROLLER.reportUser(otherId);
                case UNBLOCK -> USER_CONTROLLER.unblockUser(otherId, userId);
                case FOLLOW -> NOTIFICATION_CONTROLLER.followUser(otherId, userId);
                case REQUEST -> NOTIFICATION_CONTROLLER.sendFollowRequestToUser(otherId,userId);
                case ACCEPT -> NOTIFICATION_CONTROLLER.acceptFollowRequest(otherId , userId);
                case REJECT -> NOTIFICATION_CONTROLLER.rejectFollowRequestWithNotification(otherId , userId);
                case UNFOLLOW -> NOTIFICATION_CONTROLLER.unfollowUserWithNotification(otherId, userId);
                case QUIET_REJECT -> NOTIFICATION_CONTROLLER.rejectFollowRequestWithoutNotification(otherId , userId);
                case QUIET_UNFOLLOW -> NOTIFICATION_CONTROLLER.unfollowUserWithoutNotification(otherId , userId);
                case DELETE_REQUEST -> NOTIFICATION_CONTROLLER.deleteRequest(otherId , userId);
                case DELETE_NOTIF -> NOTIFICATION_CONTROLLER.deleteNotification(otherId, userId);
            }
            return new BooleanResponse(true);
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

    public long getOtherId() {
        return otherId;
    }

    public void setOtherId(long otherId) {
        this.otherId = otherId;
    }

    public USER_ACTION getAction() {
        return action;
    }

    public void setAction(USER_ACTION action) {
        this.action = action;
    }
}
