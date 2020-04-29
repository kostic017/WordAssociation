package vr.kostic017.wordassociation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import vr.kostic017.wordassociation.AssociationApplication;
import vr.kostic017.wordassociation.R;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_LANGUAGE = AssociationApplication.PACKAGE + ".LANGUAGE";
    public static final String EXTRA_DIFFICULTY = AssociationApplication.PACKAGE + ".DIFFICULTY";

    @Inject
    AssociationViewModel associationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AssociationApplication) getApplicationContext()).getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String languageExtra = getIntent().getStringExtra(EXTRA_LANGUAGE);
        String difficultyExtra = getIntent().getStringExtra(EXTRA_DIFFICULTY);

        Language language = Language.fromCode(languageExtra);
        Difficulty difficulty = Difficulty.valueOf(difficultyExtra);
    }
}
