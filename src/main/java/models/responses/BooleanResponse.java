package models.responses;


import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("boolean")
public class BooleanResponse implements Response{

    private boolean result;

    public BooleanResponse() {
    }

    public BooleanResponse(boolean result) {
        this.result = result;
    }

    @Override
    public void unleash() {

    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
