package models.responses;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("denied")
public class AccessDeniedResponse implements Response{

    @Override
    public void unleash() {

    }

    public AccessDeniedResponse() {
    }
}
