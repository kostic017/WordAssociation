package vr.kostic017.wordassociation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

import java.util.List;

import vr.kostic017.wordassociation.data.Association;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.repository.AssociationRepository;

public class AssociationViewModel extends ViewModel {

    private LiveData<List<Association>> associations;

    @AssistedInject
    public AssociationViewModel(@Assisted Language language,
                                @Assisted Difficulty difficulty,
                                AssociationRepository associationRepository) {
        associations = associationRepository.get(language, difficulty);
    }

    @AssistedInject.Factory
    public interface Factory {
        AssociationViewModel create(Language language, Difficulty difficulty);
    }

    public LiveData<List<Association>> getAssociations() {
        return associations;
    }
}
