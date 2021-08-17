package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("leaveGroup")
public class LeaveGroupRequest implements Request, Controllers {

    private String token;
    private long userId;
    private long groupId;

    public LeaveGroupRequest(String token, long userId, long groupId) {
        this.token = token;
        this.userId = userId;
        this.groupId = groupId;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if (clientHandler.getToken().equals(token)) {
            CHAT_CONTROLLER.leaveGroup(groupId, userId);
            return new BooleanResponse(true);
        } else
            return new BooleanResponse(false);
    }

    public LeaveGroupRequest() {
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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }


}
