package vr.kostic017.wordassociation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_DIFFICULTY = "vr.kostic017.wordassociation.DIFFICULTY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toast.makeText(this, getIntent().getStringExtra(EXTRA_DIFFICULTY), Toast.LENGTH_LONG).show();
    }
}
