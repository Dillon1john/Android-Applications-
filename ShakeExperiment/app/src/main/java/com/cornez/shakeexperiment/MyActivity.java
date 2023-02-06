package com.cornez.shakeexperiment;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends Activity  {
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private ShakeDetector mShakeDetector;

    MagicAnswer mMagicAnswer;
    private TextView mAnswerTV;
    private EditText mQuestionET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // TASK 1: SET THE REFERENCES TO THE LAYOUT ELEMENTS
        mQuestionET = (EditText) findViewById(R.id.editText);
        mAnswerTV = (TextView) findViewById(R.id.textView);
        mMagicAnswer = new MagicAnswer (MyActivity.this);

        // TASK 2: REGISTER THE SENSOR MANAGER AND SETUP THE SHAKE DETECTION

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                displayMagicAnswer();
            }
        });
    }

    private void displayMagicAnswer(){
        String magicAnswer = mMagicAnswer.getRandomAnswer();
        mAnswerTV.setText(magicAnswer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mSensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector, mSensorAccelerometer);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
