package vr.kostic017.wordassociation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import vr.kostic017.wordassociation.databinding.ActivityMenuBinding;
import vr.kostic017.wordassociation.model.Language;

public class MenuActivity extends AppCompatActivity {

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
        Language language = Language.fromCode(Locale.getDefault().getLanguage());
        if (language != null) {
            activityMenuBinding.spinnerLanguage.setSelection(language.ordinal());
        }
    }

    public void startGame(View view) {
        Language language = (Language) activityMenuBinding.spinnerLanguage.getSelectedItem();
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(PlayActivity.EXTRA_LANGUAGE, language.getCode());
        intent.putExtra(PlayActivity.EXTRA_DIFFICULTY, (String) view.getTag());
        startActivity(intent);
    }

}
