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

    private MutableLiveData<Boolean> loaded;
    private MutableLiveData<List<Association>> associations;
    private MutableLiveData<Integer> currentAssociationIndex;

    public AssociationViewModel(Language language, Difficulty difficulty,
                                AssociationRepository associationRepository) {
        loaded = new MutableLiveData<>(false);
        currentAssociationIndex = new MutableLiveData<>();
        associations = associationRepository.get(language, difficulty);
    }

    public void doneLoading() {
        loaded.setValue(true);
        currentAssociationIndex.setValue(0);
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

    public LiveData<Boolean> getLoaded() {
        return loaded;
    }

    public LiveData<List<Association>> getAssociations() {
        return associations;
    }

    public LiveData<Integer> getCurrentAssociationIndex() {
        return currentAssociationIndex;
    }
}
