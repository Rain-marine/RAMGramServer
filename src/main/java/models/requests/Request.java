package models.requests;


import controllers.ClientHandler;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "model")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
        @JsonSubTypes.Type(value =  UpdateLoggedUserRequest.class , name = "updateLoggedUser"),
        @JsonSubTypes.Type(value =  IsPublicRequest.class , name = "isPublic"),
        @JsonSubTypes.Type(value =  CheckPasswordRequest.class , name = "checkPass"),
        @JsonSubTypes.Type(value = ChangeAccountVisibilityRequest.class, name = "visibility"),
        @JsonSubTypes.Type(value = DeActiveRequest.class, name = "deActive"),
        @JsonSubTypes.Type(value = ChangeBirthdayRequest.class, name = "changeBirthday"),
        @JsonSubTypes.Type(value = DeleteAccountRequest.class, name = "deleteAccount"),
        @JsonSubTypes.Type(value = ChangePasswordRequest.class, name = "changePass"),
        @JsonSubTypes.Type(value = ChangeLastSeenRequest.class, name = "changeLastSeen"),
        @JsonSubTypes.Type(value = TweetRequest.class, name = "tweet"),
        @JsonSubTypes.Type(value = ListRequest.class, name = "list"),
        @JsonSubTypes.Type(value = TweetActionRequest.class, name = "tweetAction"),
        @JsonSubTypes.Type(value = UserActionRequest.class, name = "userAction"),
        @JsonSubTypes.Type(value = AddCommentRequest.class, name = "addComment"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),


})

public interface Request {

    Response execute(ClientHandler clientHandler);

}
