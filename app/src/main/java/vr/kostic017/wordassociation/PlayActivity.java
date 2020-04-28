package vr.kostic017.wordassociation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import vr.kostic017.wordassociation.config.App;
import vr.kostic017.wordassociation.model.Difficulty;
import vr.kostic017.wordassociation.model.Language;
import vr.kostic017.wordassociation.provider.RetrofitProvider;
import vr.kostic017.wordassociation.repository.AssociationRepository;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModelFactory;
import vr.kostic017.wordassociation.webservice.AssociationWebservice;

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

        AssociationWebservice associationWebservice = RetrofitProvider.getRetrofit().create(AssociationWebservice.class);
        AssociationRepository associationRepository = new AssociationRepository(associationWebservice);
        ViewModelProvider.Factory associationViewModelFactory = new AssociationViewModelFactory(associationRepository);
        associationViewModel = new ViewModelProvider(this, associationViewModelFactory).get(AssociationViewModel.class);
    }
}
