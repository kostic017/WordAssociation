package vr.kostic017.wordassociation.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vr.kostic017.wordassociation.data.Association;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.repository.AssociationRepository;

public class AssociationViewModel extends ViewModel {

    private AssociationRepository associationRepository;
    private Map<Difficulty, List<Association>> associations;

    @Inject
    public AssociationViewModel(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

}
