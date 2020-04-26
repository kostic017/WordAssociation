package vr.kostic017.wordassociation.webservice.response;

import java.util.List;
import java.util.Map;

import vr.kostic017.wordassociation.model.Association;
import vr.kostic017.wordassociation.model.Difficulty;

public class AssociationResponse {
    private Map<Difficulty, List<Association>> associations;

    public Map<Difficulty, List<Association>> getAssociations() {
        return associations;
    }

    public void setAssociations(Map<Difficulty, List<Association>> associations) {
        this.associations = associations;
    }
}
