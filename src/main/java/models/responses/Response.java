package models.responses;


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
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),
//        @JsonSubTypes.Type(value = LoginResponse.class, name = "login"),



})

public interface Response {

    void unleash();
}
