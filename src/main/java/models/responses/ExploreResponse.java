package models.responses;


import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("explore")
public class ExploreResponse implements Response {

    private long foundUserId;

    public ExploreResponse(long foundUserId) {
        this.foundUserId = foundUserId;
    }

    public long getFoundUserId() {
        return foundUserId;
    }

    public void setFoundUserId(long foundUserId) {
        this.foundUserId = foundUserId;
    }

    @Override
    public void unleash() {

    }

    public ExploreResponse() {
    }
}
