package vr.kostic017.wordassociation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import vr.kostic017.wordassociation.repository.AssociationRepository;

public class AssociationViewModelFactory implements ViewModelProvider.Factory {

    private AssociationRepository associationRepository;

    public AssociationViewModelFactory(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AssociationViewModel(associationRepository);
    }

}
