package vr.kostic017.wordassociation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vr.kostic017.wordassociation.config.App;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_LANGUAGE = App.PACKAGE + ".LANGUAGE";
    public static final String EXTRA_DIFFICULTY = App.PACKAGE + ".DIFFICULTY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toast.makeText(this, getIntent().getStringExtra(EXTRA_DIFFICULTY) + " " + getIntent().getStringExtra(EXTRA_LANGUAGE), Toast.LENGTH_SHORT).show();
    }
}
