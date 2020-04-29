package vr.kostic017.wordassociation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import vr.kostic017.wordassociation.config.App;
import vr.kostic017.wordassociation.config.ConfigKey;
import vr.kostic017.wordassociation.model.Difficulty;
import vr.kostic017.wordassociation.model.Language;
import vr.kostic017.wordassociation.provider.ConfigProvider;
import vr.kostic017.wordassociation.provider.RetrofitProvider;
import vr.kostic017.wordassociation.repository.AssociationRepository;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModelFactory;
import vr.kostic017.wordassociation.webservice.AssociationWebservice;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = PlayActivity.class.getSimpleName();

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

        try {
            String baseUrl = new ConfigProvider(getAssets()).getProperty(ConfigKey.API_BASE_URL);
            AssociationWebservice associationWebservice = new RetrofitProvider(baseUrl).getRetrofit().create(AssociationWebservice.class);
            AssociationRepository associationRepository = new AssociationRepository(associationWebservice);
            ViewModelProvider.Factory associationViewModelFactory = new AssociationViewModelFactory(associationRepository);
            associationViewModel = new ViewModelProvider(this, associationViewModelFactory).get(AssociationViewModel.class);
        } catch (IOException e) {
            Log.e(TAG, "Could not load properties.", e);
            Toast.makeText(this, R.string.error_configuration_read, Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
