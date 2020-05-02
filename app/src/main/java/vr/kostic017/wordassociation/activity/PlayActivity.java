package vr.kostic017.wordassociation.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModelProvider;

import vr.kostic017.wordassociation.AssociationApplication;
import vr.kostic017.wordassociation.R;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.databinding.ActivityPlayBinding;
import vr.kostic017.wordassociation.di.ApplicationComponent;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModelFactory;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_LANGUAGE = AssociationApplication.PACKAGE + ".LANGUAGE";
    public static final String EXTRA_DIFFICULTY = AssociationApplication.PACKAGE + ".DIFFICULTY";

    private ObservableBoolean loaded;
    private AssociationViewModel associationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationComponent applicationComponent =
                ((AssociationApplication) getApplicationContext()).getApplicationComponent();

        super.onCreate(savedInstanceState);

        Language language = Language.fromCode(getIntent().getStringExtra(EXTRA_LANGUAGE));
        Difficulty difficulty = Difficulty.valueOf(getIntent().getStringExtra(EXTRA_DIFFICULTY));

        AssociationViewModelFactory associationViewModelFactory =
                applicationComponent.associationViewModelFactory().create(language, difficulty);
        associationViewModel = new ViewModelProvider(this, associationViewModelFactory)
                .get(AssociationViewModel.class);

        loaded = new ObservableBoolean(false);

        ActivityPlayBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_play);
        binding.setLoaded(loaded);
        binding.setAssociationViewModel(associationViewModel);
        binding.setLifecycleOwner(this);

        associationViewModel.getAssociations().observe(this, associations -> {
            loaded.set(true);
            associationViewModel.nextAssociation();
        });

        associationViewModel.getCurrentAssociationIndex().observe(this, index -> {

        });
    }
}
