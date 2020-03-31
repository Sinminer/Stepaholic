package com.example.stepaholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    int steps;
    boolean userRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        steps = intent.getIntExtra("Steps",0);
        userRunning = intent.getBooleanExtra("Running",true);

        Button resetButton = findViewById(R.id.resetButton);
        Button pauseButton = findViewById(R.id.pauseButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSteps();
                Intent resetIntent = new Intent();
                resetIntent.putExtra("reset",steps);
                setResult(RESULT_OK,resetIntent);

            }

        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
                Intent pauseIntent = new Intent();
                pauseIntent.putExtra("reset",userRunning);
                setResult(RESULT_OK,pauseIntent);
            }
        });
    }
    public void resetSteps(){
        steps = 0;
        Toast.makeText(this,"Set steps to 0",Toast.LENGTH_SHORT).show();
    }
    public void pause(){
        if (userRunning){
            Toast.makeText(this, "Pausing", Toast.LENGTH_SHORT).show();
            userRunning = false;
        }else Toast.makeText(this, "Unpausing", Toast.LENGTH_SHORT).show();
    }
}
