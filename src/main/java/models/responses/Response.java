package models.responses;


import models.requests.ChatInfoRequest;
import models.requests.ListRequest;
import models.requests.LoginRequest;
import models.requests.TweetRequest;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "model")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
        @JsonSubTypes.Type(value = BooleanResponse.class, name = "boolean"),
        @JsonSubTypes.Type(value = LoggedUserResponse.class, name = "LoggedUser"),
        @JsonSubTypes.Type(value = TweetResponse.class, name = "tweet"),
        @JsonSubTypes.Type(value = ListResponse.class, name = "list"),
        @JsonSubTypes.Type(value = MessageResponse.class, name = "message"),
        @JsonSubTypes.Type(value = ChatInfoResponse.class, name = "chatInfo"),
        @JsonSubTypes.Type(value = ChatResponse.class, name = "chat"),
        @JsonSubTypes.Type(value = ExploreResponse.class, name = "explore"),
        @JsonSubTypes.Type(value = NotificationResponse.class, name = "notification"),
        @JsonSubTypes.Type(value = FactionResponse.class, name = "faction"),
        @JsonSubTypes.Type(value = UserResponse.class, name = "user"),
        @JsonSubTypes.Type(value = PermissionResponse.class, name = "permission"),
        @JsonSubTypes.Type(value = ConnectionErrorResponse.class, name = "error"),



})

public interface Response {

    void unleash();
}
