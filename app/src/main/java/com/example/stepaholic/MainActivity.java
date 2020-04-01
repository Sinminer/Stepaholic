package com.example.stepaholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
// The main activity that implements SensorEventListener to utilise the inbuilt motion sensor to
// track the movement of the user.
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // Creation of all the variables
    //Creating final static string for use in intent passing.
     final static String RESET = "reset";
     final static String PAUSE= "pause";
     //Creating the text views to display the steps, miles and kilometres
    private TextView stepsTextView;
    private TextView milesTextView;
    private TextView kilometreTextView;
    // A boolean to determine if the user is running or not and if they want to enable the sensor.
    boolean userRunning = false;
    //The sensor Manager is is used to check if the user has a motion sensor in their phone and
    // if its active it starts as a null to error check.
    SensorManager sensorManager = null;
    //Using floats for the variables so the kilometres and miles get displayed a decimal
    float steps;
    float miles = steps / 2000;
    float kilometres = steps / 1500;
//Using an image button to display the settings page
    ImageButton settingsButton;
//The app constructor assigning all the variables to the XML display and creating the sensor.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsOpen();
            }
        });
        stepsTextView = findViewById(R.id.stepsDisplay);
        milesTextView = findViewById(R.id.mileDisplay);
        kilometreTextView = findViewById(R.id.kiloDisplay);
    }
// On resume method that checks if the phone has sensor and if not displaying to the user that there
    // is no sensor available.
    @Override
    protected void onResume() {
        super.onResume();
        userRunning = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepSensor == null){
            Toast.makeText(this,"Step sensor not detected",Toast.LENGTH_SHORT).show();
        }else {
            sensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI);
        }
    }
// On pause method that sets the userRunning boolean to false to stop the sensor running.
    @Override
    protected void onPause() {
        super.onPause();
        userRunning = false;
        sensorManager.unregisterListener(this);
    }
// onSensorChanged method that checks if the user is running and if it is changes the steps to the
    // amount of steps that the user has stepped and then displaying it to the user.
    @Override
    public void onSensorChanged(SensorEvent event) {
    if (userRunning){
        steps = event.values[0];
        stepsTextView.setText(String.format("%s", steps));
        milesTextView.setText(String.format("%s", miles));
        kilometreTextView.setText(String.format("%s", kilometres));
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
//settingsOpen method uses intents to move the the user to the settings activity and passes the
    //steps and the userRunning variables to be used in the activity.
    public void settingsOpen(){
        Intent intent = new Intent(this,SettingsActivity.class);
        intent.putExtra(SettingsActivity.STEPS,steps);
        intent.putExtra(SettingsActivity.RUNNING,userRunning);
        startActivityForResult(intent,1);
    }
//onActivityResult returns the result that the settingsActivity sent and updates the Main activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                steps = data.getIntExtra(RESET,0);
                userRunning = data.getBooleanExtra(PAUSE,false);
            }
        }
    }
}
