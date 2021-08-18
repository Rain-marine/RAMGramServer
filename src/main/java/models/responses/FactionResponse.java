package models.responses;

import models.Group;
import models.trimmed.TrimmedFaction;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("faction")
public class FactionResponse implements Response{

    private List<TrimmedFaction> trimmedFactions;

    public FactionResponse(List<Group> factions , long ownerId) {
        trimmedFactions = new ArrayList<>();
        for (Group faction : factions) {
            trimmedFactions.add(new TrimmedFaction(faction.getId() , ownerId));
        }
    }

    public FactionResponse(int factionId , long ownerId) {
        trimmedFactions = new ArrayList<>();
        trimmedFactions.add(new TrimmedFaction(factionId , ownerId));
    }


    @Override
    public void unleash() {

    }

    public FactionResponse() {
    }

    public List<TrimmedFaction> getTrimmedFactions() {
        return trimmedFactions;
    }

    public void setTrimmedFactions(List<TrimmedFaction> trimmedFactions) {
        this.trimmedFactions = trimmedFactions;
    }
}
