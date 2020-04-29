package vr.kostic017.wordassociation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import vr.kostic017.wordassociation.consts.App;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_LANGUAGE = App.PACKAGE + ".LANGUAGE";
    public static final String EXTRA_DIFFICULTY = App.PACKAGE + ".DIFFICULTY";

    private AssociationViewModel associationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String languageExtra = getIntent().getStringExtra(EXTRA_LANGUAGE);
        String difficultyExtra = getIntent().getStringExtra(EXTRA_DIFFICULTY);

        Language language = Language.fromCode(languageExtra);
        Difficulty difficulty = Difficulty.valueOf(difficultyExtra);
        associationViewModel = new ViewModelProvider(this).get(AssociationViewModel.class);
    }
}
