package models.responses;


import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "model")
@JsonSubTypes({
//        @JsonSubTypes.Type(value = Login_SignUp_Request.class, name = "login"),
})

public interface Response {

    void unleash();
}
