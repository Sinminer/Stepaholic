package com.example.stepaholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//SettingsActivity where variables are assigned.
public class SettingsActivity extends AppCompatActivity {
    //creating final static strings for intent use
    final static String STEPS = "Steps";
    final static String RUNNING = "Running";
//Assigning steps and userRunning to be used for the buttons and settings.
    int steps;
    boolean userRunning;
//The constructor that assigns variables to the buttons and textViews and assigning onClicks to the
    //Buttons.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        steps = intent.getIntExtra(STEPS,0);
        userRunning = intent.getBooleanExtra(RUNNING,true);

        Button resetButton = findViewById(R.id.resetButton);
        Button pauseButton = findViewById(R.id.pauseButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSteps();
                Intent resetIntent = new Intent();
                resetIntent.putExtra(MainActivity.RESET,steps);
                setResult(RESULT_OK,resetIntent);

            }

        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
                Intent pauseIntent = new Intent();
                pauseIntent.putExtra(MainActivity.PAUSE,userRunning);
                setResult(RESULT_OK,pauseIntent);
            }
        });
    }
    //ResetSteps methods sets the steps to 0 and posts a toast to the screen.
    public void resetSteps(){
        steps = 0;
        Toast.makeText(this,"Set steps to 0",Toast.LENGTH_SHORT).show();
    }
    //pauseMethod pauses the step tracker and posts a message informing the user if the tracker is
    //paused or not.
    public void pause(){
        if (userRunning){
            Toast.makeText(this, "Pausing", Toast.LENGTH_SHORT).show();
            userRunning = false;
        }else Toast.makeText(this, "Unpausing", Toast.LENGTH_SHORT).show();
    }

}
