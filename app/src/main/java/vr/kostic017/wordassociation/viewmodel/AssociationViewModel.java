package vr.kostic017.wordassociation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.NoSuchElementException;

import vr.kostic017.wordassociation.data.Association;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.repository.AssociationRepository;

public class AssociationViewModel extends ViewModel {
    private static final String TAG = AssociationViewModel.class.getSimpleName();

    private LiveData<List<Association>> associations;
    private MutableLiveData<Integer> currentAssociationIndex;

    public AssociationViewModel(Language language, Difficulty difficulty,
                                AssociationRepository associationRepository) {
        associations = associationRepository.get(language, difficulty);
        currentAssociationIndex = new MutableLiveData<>(-1);
    }

    public Association currentAssociation() {
        if (associations.getValue() == null) {
            Log.e(TAG, "Associations aren't loaded");
            throw new NoSuchElementException("Associations aren't loaded");
        }
        if (currentAssociationIndex.getValue() == null) {
            Log.e(TAG, "Current association is not set");
            throw new NoSuchElementException("Current association is not set");
        }
        return associations.getValue().get(currentAssociationIndex.getValue());
    }

    public void nextAssociation() {
        if (currentAssociationIndex.getValue() != null) {
            currentAssociationIndex.setValue(currentAssociationIndex.getValue() + 1);
        }
    }

    public LiveData<List<Association>> getAssociations() {
        return associations;
    }

    public LiveData<Integer> getCurrentAssociationIndex() {
        return currentAssociationIndex;
    }
}
