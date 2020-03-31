package com.example.stepaholic;

import androidx.annotation.Nullable;
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

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView stepsTextView;
    private TextView milesTextView;
    private TextView kilometreTextView;
    boolean userRunning = false;
    SensorManager sensorManager = null;
    float steps;
    float miles = steps / 2000;
    float kilometres = steps / 1500;
    ImageButton settingsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        steps = 5;
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

    @Override
    protected void onResume() {
        super.onResume();
        userRunning = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepSensor == null){
            Toast.makeText(this,"Step sensor not detected",Toast.LENGTH_SHORT).show();
            steps = 5;
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
        steps = 5;
        stepsTextView.setText(String.format("%s", steps));
        milesTextView.setText(String.format("%s", miles));
        kilometreTextView.setText(String.format("%s", kilometres));
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void settingsOpen(){
        Intent intent = new Intent(this,SettingsActivity.class);
        intent.putExtra("",steps);
        intent.putExtra("",userRunning);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                steps = data.getIntExtra("result",0);
                userRunning = data.getBooleanExtra("result",false);
            }
        }
    }
}
