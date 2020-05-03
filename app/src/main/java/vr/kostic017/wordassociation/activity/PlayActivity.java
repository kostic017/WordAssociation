package vr.kostic017.wordassociation.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import vr.kostic017.wordassociation.AssociationApplication;
import vr.kostic017.wordassociation.R;
import vr.kostic017.wordassociation.consts.Cell;
import vr.kostic017.wordassociation.consts.PlayResultCode;
import vr.kostic017.wordassociation.data.Difficulty;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.databinding.ActivityPlayBinding;
import vr.kostic017.wordassociation.di.ApplicationComponent;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModel;
import vr.kostic017.wordassociation.viewmodel.AssociationViewModelFactory;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = PlayActivity.class.getSimpleName();

    public static final String EXTRA_SCORE = AssociationApplication.PACKAGE + ".SCORE";
    public static final String EXTRA_LANGUAGE = AssociationApplication.PACKAGE + ".LANGUAGE";
    public static final String EXTRA_DIFFICULTY = AssociationApplication.PACKAGE + ".DIFFICULTY";
    public static final String EXTRA_RESULT_CODE = AssociationApplication.PACKAGE + ".RESULT_CODE";

    private AssociationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationComponent applicationComponent =
                ((AssociationApplication) getApplicationContext()).getApplicationComponent();

        super.onCreate(savedInstanceState);

        Language language = Language.fromCode(getIntent().getStringExtra(EXTRA_LANGUAGE));
        Difficulty difficulty = Difficulty.valueOf(getIntent().getStringExtra(EXTRA_DIFFICULTY));

        AssociationViewModelFactory associationViewModelFactory =
                applicationComponent.associationViewModelFactory().create(language, difficulty);
        viewModel = new ViewModelProvider(this, associationViewModelFactory)
                .get(AssociationViewModel.class);

        ActivityPlayBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_play);
        binding.setActivity(this);
        binding.setLifecycleOwner(this);

        viewModel.getAssociations().observe(this, associations -> {
            if (associations != null && !associations.isEmpty()) {
                viewModel.doneLoading();
            } else {
                Intent result = new Intent();
                result.putExtra(PlayActivity.EXTRA_RESULT_CODE, PlayResultCode.ERROR_FETCH_ASSOCIATIONS);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        viewModel.getTimeLeft().observe(this, timeLeft -> {
            if (timeLeft.equals(0)) {
                Intent result = new Intent();
                result.putExtra(PlayActivity.EXTRA_RESULT_CODE, PlayResultCode.OUT_OF_TIME.name());
                result.putExtra(PlayActivity.EXTRA_SCORE, viewModel.getScore());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }

    public void tryAnswer(Cell solutionCell) {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.your_answer)
                .setView(input)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (!viewModel.tryAnswer(solutionCell, input.getText().toString())) {
                        Toast.makeText(PlayActivity.this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                } else {
                    Log.w(TAG, "Could not get window reference.");
                }
            }
        });

        input.requestFocus();
        alertDialog.show();
    }

    public void nextAssociation() {
        if (!PlayActivity.this.viewModel.nextAssociation()) {
            Intent result = new Intent();
            result.putExtra(PlayActivity.EXTRA_RESULT_CODE, PlayResultCode.OUT_OF_ASSOCIATIONS.name());
            result.putExtra(PlayActivity.EXTRA_SCORE, viewModel.getScore());
            setResult(Activity.RESULT_OK, result);
            finish();
        }
    }

    public AssociationViewModel getViewModel() {
        return viewModel;
    }
}
