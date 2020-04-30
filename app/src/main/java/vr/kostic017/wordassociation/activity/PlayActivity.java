package vr.kostic017.wordassociation.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vr.kostic017.wordassociation.AssociationApplication;
import vr.kostic017.wordassociation.R;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.di.ApplicationComponent;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_LANGUAGE = AssociationApplication.PACKAGE + ".LANGUAGE";
    public static final String EXTRA_DIFFICULTY = AssociationApplication.PACKAGE + ".DIFFICULTY";

    private AssociationViewModel associationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationComponent applicationComponent =
                ((AssociationApplication) getApplicationContext()).getApplicationComponent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String languageExtra = getIntent().getStringExtra(EXTRA_LANGUAGE);
        String difficultyExtra = getIntent().getStringExtra(EXTRA_DIFFICULTY);

        Language language = Language.fromCode(languageExtra);
        Difficulty difficulty = Difficulty.valueOf(difficultyExtra);
        associationViewModel = applicationComponent.associationViewModelFactory().create(language, difficulty);

        associationViewModel.getAssociations().observe(this, associations -> {
            if (associations != null && !associations.isEmpty()) {
                Toast.makeText(PlayActivity.this, associations.get(0).getSolutions().get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
