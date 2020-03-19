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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    boolean userRunning = false;
    SensorManager sensorManager = null;
    float steps;
    float miles = steps / 2000;
    float kilometres = steps / 1500;
    ImageButton settingsButton;
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
    }

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

    @Override
    protected void onPause() {
        super.onPause();
        userRunning = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    if (userRunning){
        steps = event.values[0];
        TextView stepsTextview = findViewById(R.id.stepsDisplay);
        stepsTextview.setText(String.format("%s", steps));
        TextView milesTextview = findViewById(R.id.mileDisplay);
        milesTextview.setText(String.format("%s", miles));
        TextView kilometreTextview = findViewById(R.id.kiloDisplay);
        kilometreTextview.setText(String.format("%s", kilometres));
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void settingsOpen(){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
}
