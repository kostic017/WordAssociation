package vr.kostic017.wordassociation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vr.kostic017.wordassociation.enumeration.Difficulty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(PlayActivity.EXTRA_DIFFICULTY, (String) view.getTag());
        startActivity(intent);
    }

}
