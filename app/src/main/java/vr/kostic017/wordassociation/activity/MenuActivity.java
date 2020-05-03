package vr.kostic017.wordassociation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import vr.kostic017.wordassociation.R;
import vr.kostic017.wordassociation.consts.PlayActivityResult;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    public static final int PLAY_REQUEST_CODE = 1;

    private boolean isDifficultySelected;
    private ActivityMenuBinding activityMenuBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMenuBinding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(activityMenuBinding.getRoot());

        ArrayAdapter<Language> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Language.values());
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        activityMenuBinding.spinnerLanguage.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isDifficultySelected = false;
        Language language = Language.fromCode(Locale.getDefault().getLanguage());
        if (language != null) {
            activityMenuBinding.spinnerLanguage.setSelection(language.ordinal());
        }
    }

    public void startGame(View view) {
        if (!isDifficultySelected) {
            isDifficultySelected = true;
            Language language = (Language) activityMenuBinding.spinnerLanguage.getSelectedItem();
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra(PlayActivity.EXTRA_LANGUAGE, language.getCode());
            intent.putExtra(PlayActivity.EXTRA_DIFFICULTY, (String) view.getTag());
            startActivityForResult(intent, PLAY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PLAY_REQUEST_CODE && data != null) {
            PlayActivityResult playActivityResult = PlayActivityResult.valueOf(data.getStringExtra(PlayActivity.EXTRA_RESULT));
            switch (playActivityResult) {
                case ERROR_FETCH_ASSOCIATIONS:
                    Toast.makeText(MenuActivity.this, R.string.error_fetch_associations, Toast.LENGTH_SHORT).show();
                    break;
                case NO_MORE_ASSOCIATIONS:
                    Toast.makeText(MenuActivity.this, R.string.no_more_associations, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
