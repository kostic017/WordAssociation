package vr.kostic017.wordassociation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import vr.kostic017.wordassociation.AssociationApplication;
import vr.kostic017.wordassociation.R;
import vr.kostic017.wordassociation.consts.PlayResultCode;
import vr.kostic017.wordassociation.data.Language;
import vr.kostic017.wordassociation.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    public static final int PLAY_REQUEST_CODE = 1;

    public static final String HIGH_SCORE = AssociationApplication.PACKAGE + ".HIGH_SCORE";

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
        activityMenuBinding.textViewScore.setText(String.valueOf(readHighScore()));
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
            PlayResultCode playResultCode = PlayResultCode.valueOf(data.getStringExtra(PlayActivity.EXTRA_RESULT_CODE));
            switch (playResultCode) {
                case ERROR_FETCH_ASSOCIATIONS:
                    Toast.makeText(MenuActivity.this, R.string.error_fetch_associations, Toast.LENGTH_SHORT).show();
                    break;
                case OUT_OF_ASSOCIATIONS:
                    Toast.makeText(MenuActivity.this, R.string.out_of_associations, Toast.LENGTH_SHORT).show();
                    saveScore(data);
                    break;
                case OUT_OF_TIME:
                    Toast.makeText(MenuActivity.this, R.string.out_of_time, Toast.LENGTH_SHORT).show();
                    saveScore(data);
                    break;
            }
        }
    }

    private int readHighScore() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getInt(HIGH_SCORE, 0);
    }

    private void saveScore(Intent data) {
        int score = data.getIntExtra(PlayActivity.EXTRA_SCORE, 0);
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt(HIGH_SCORE, 0);
        if (score > highScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(HIGH_SCORE, score);
            editor.apply();
            Toast.makeText(this, R.string.new_high_score, Toast.LENGTH_SHORT).show();
        }
    }
}
