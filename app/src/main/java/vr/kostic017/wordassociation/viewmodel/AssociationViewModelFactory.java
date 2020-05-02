package vr.kostic017.wordassociation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.inject.assisted.Assisted;
import com.squareup.inject.assisted.AssistedInject;

import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.repository.AssociationRepository;

public class AssociationViewModelFactory implements ViewModelProvider.Factory {

    private Language language;
    private Difficulty difficulty;
    private AssociationRepository associationRepository;

    @AssistedInject
    public AssociationViewModelFactory(@Assisted Language language,
                                       @Assisted Difficulty difficulty,
                                       AssociationRepository associationRepository) {
        this.language = language;
        this.difficulty = difficulty;
        this.associationRepository = associationRepository;
    }

    @AssistedInject.Factory
    public interface Factory {
        AssociationViewModelFactory create(Language language, Difficulty difficulty);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AssociationViewModel(language, difficulty, associationRepository);
    }
}
