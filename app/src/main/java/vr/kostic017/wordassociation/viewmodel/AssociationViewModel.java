package vr.kostic017.wordassociation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import vr.kostic017.wordassociation.consts.Cell;
import vr.kostic017.wordassociation.data.Association;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.repository.AssociationRepository;

public class AssociationViewModel extends ViewModel {
    private static final String TAG = AssociationViewModel.class.getSimpleName();

    private int currentAssociationIndex;
    private Association currentAssociation;

    private MutableLiveData<Boolean> loaded;
    private MutableLiveData<List<Association>> associations;
    private Map<Cell, MutableLiveData<Boolean>> closedCells;

    public AssociationViewModel(Language language, Difficulty difficulty,
                                AssociationRepository associationRepository) {

        currentAssociationIndex = -1;
        loaded = new MutableLiveData<>(false);
        associations = associationRepository.get(language, difficulty);

        closedCells = new HashMap<>();
        for (Cell cell : Cell.values()) {
            closedCells.put(cell, new MutableLiveData<>(true));
        }

    }

    public void doneLoading() {
        if (loaded.getValue() != null && !loaded.getValue()) {
            loaded.setValue(true);
            nextAssociation();
        }
    }

    public boolean nextAssociation() {
        if (associations.getValue() == null) {
            Log.e(TAG, "Associations aren't loaded");
            throw new NoSuchElementException("Associations aren't loaded");
        }

        if (currentAssociationIndex >= associations.getValue().size() - 1) {
            return false;
        }

        for (Cell cell : Cell.values()) {
            setCellClosed(cell, true);
        }

        ++currentAssociationIndex;
        currentAssociation = associations.getValue().get(currentAssociationIndex);

        return true;
    }

    public void openCell(Cell cell) {
        setCellClosed(cell, false);
    }

    public boolean tryAnswer(Cell solutionCell, String answer) {

        if (getCorrectAnswers(solutionCell).stream().noneMatch(answer::equalsIgnoreCase)) {
            return false;
        }

        if (solutionCell == Cell.F) {
            openRow(Cell.A);
            openRow(Cell.B);
            openRow(Cell.C);
            openRow(Cell.D);
            openCell(Cell.F);
        } else {
            openRow(solutionCell);
        }

        return true;

    }

    private List<String> getCorrectAnswers(Cell solutionCell) {
        switch (solutionCell) {
            case A: return currentAssociation.getSolutionsA();
            case B: return currentAssociation.getSolutionsB();
            case C: return currentAssociation.getSolutionsC();
            case D: return currentAssociation.getSolutionsD();
            case F: return currentAssociation.getSolutions();
            default:
                Log.e(TAG, "This method should be called only for cells that represent solutions");
                throw new IllegalArgumentException("This method should be called only for cells that represent solutions");
        }
    }

    private void setCellClosed(Cell cell, Boolean state) {
        MutableLiveData<Boolean> cellState = closedCells.get(cell);
        if (cellState != null && cellState.getValue() != null) {
            if (cellState.getValue() != state) {
                cellState.setValue(state);
            }
        } else {
            Log.e(TAG, "State of cell " + cell.name() + " is unknown");
            throw new IllegalStateException("State of cell " + cell.name() + " is unknown");
        }
    }

    private void openRow(Cell solutionCell) {
        openCell(solutionCell);
        openCell(Cell.valueOf(solutionCell.name() + "1"));
        openCell(Cell.valueOf(solutionCell.name() + "2"));
        openCell(Cell.valueOf(solutionCell.name() + "3"));
        openCell(Cell.valueOf(solutionCell.name() + "4"));
    }

    public Association getCurrentAssociation() {
        return currentAssociation;
    }

    public LiveData<Boolean> getLoaded() {
        return loaded;
    }

    public LiveData<List<Association>> getAssociations() {
        return associations;
    }

    public Map<Cell, MutableLiveData<Boolean>> getClosedCells() {
        return closedCells;
    }
}
