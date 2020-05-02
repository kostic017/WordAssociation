package vr.kostic017.wordassociation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import vr.kostic017.wordassociation.AssociationApplication;
import vr.kostic017.wordassociation.R;
import vr.kostic017.wordassociation.consts.PlayResult;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.databinding.ActivityPlayBinding;
import vr.kostic017.wordassociation.di.ApplicationComponent;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModelFactory;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_RESULT = AssociationApplication.PACKAGE + ".RESULT";
    public static final String EXTRA_LANGUAGE = AssociationApplication.PACKAGE + ".LANGUAGE";
    public static final String EXTRA_DIFFICULTY = AssociationApplication.PACKAGE + ".DIFFICULTY";

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

        ActivityPlayBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_play);
        binding.setAssociationViewModel(associationViewModel);
        binding.setLifecycleOwner(this);

        associationViewModel.getAssociations().observe(this, associations -> {
            if (associations != null) {
                associationViewModel.doneLoading();
            } else {
                finish(PlayResult.ERROR_FETCH_ASSOCIATIONS);
            }
        });

        associationViewModel.getCurrentAssociationIndex().observe(this, index -> {

        });
    }

    private void finish(PlayResult playResult) {
        Intent result = new Intent();
        result.putExtra(PlayActivity.EXTRA_RESULT, playResult.toString());
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
