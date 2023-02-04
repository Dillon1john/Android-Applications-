package com.cornez.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class MyActivity extends Activity implements SensorEventListener{

    // define the display assembly compass picture
    private ImageView compassImage;

    // record the compass picture angle turned
    private float currentDegree = 0.0f;

    // device sensor manager
    private SensorManager mSensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagnetometer;

    private float[] accelerometer;
    private float[] geomagnetic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // REFERENCE THE COMPASS IMAGE ON THE LAYOUT
        compassImage = (ImageView) findViewById(R.id.imageView);

        // INITIALIZE THE SENSOR CAPABILITIES
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnetometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //SENSOR_DELAY_GAME IS THE ONLY ONE THAT WORKS
        mSensorManager.registerListener(this, sensorAccelerometer,
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, sensorMagnetometer,
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // UNREGISTER THE LISTENER - save battery
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        final int DELAY = 1000;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelerometer = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values;

        if (accelerometer != null && geomagnetic != null) {

            float r[] = new float[9];
            float i[] = new float[9];
            boolean foundRotation = SensorManager.getRotationMatrix(r, i, accelerometer,
                    geomagnetic);
            if (foundRotation) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(r, orientation);

                // COMPUTE THE X-AXIS ROTATION ANGLE
                float degree = (float) Math.toDegrees(orientation[0]);


                // CREATE A ROTATION ANIMATION
                // ROTATION WILL OCCUR IN REVERSE FROM CURRENT DEGREE TO - DEGREE
                RotateAnimation animation = new RotateAnimation(currentDegree,
                        -degree, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);

                // how long the animation will take place
                animation.setDuration(DELAY);

                // set the animation after the end of the reservation status
                animation.setFillAfter(true);

                // Start the animation
                compassImage.startAnimation(animation);
                currentDegree = -degree;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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
