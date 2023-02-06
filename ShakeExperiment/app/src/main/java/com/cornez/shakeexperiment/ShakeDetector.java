package com.cornez.shakeexperiment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeDetector implements SensorEventListener {


    private long mTimeOfLastShake;

    private static final float SHAKE_THRESHOLD = 25f;
    private static final int SHAKE_TIME_LAPSE = 200;   //IN MILLISECONDS 500


    // OnShakeListener THAT WILL BE NOTIFIED WHEN A SHAKE IS DETECTED
    private OnShakeListener mShakeListener;

    // CONSTRUCTOR SETS THE SHAKE LISTENER
    public ShakeDetector(OnShakeListener shakeListener) {
        mShakeListener = shakeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //TASK 1: COLLECT SENSOR VALUES ON ALL THREE AXIS
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //TASK 2: CONVERT EACH ACCELEROMETER MEASUREMENT INTO
            //  A G-FORCE MEASUREMENT BY NEUTRALIZING GRAVITY.
            float gForceX = x - SensorManager.GRAVITY_EARTH;
            float gForceY = y - SensorManager.GRAVITY_EARTH;
            float gForceZ = z - SensorManager.GRAVITY_EARTH;

            //TASK 3: COMPUTE  G-FORCE AS A DIRECTIONLESS MEASUREMENT
            // NOTE: G-FORCE WILL BE APPROXIMATELY 1 WHEN
            //       THERE IS NO SHAKING MOVEMENT.
            double vector = Math.pow(gForceX, 2.0) + Math.pow(gForceY, 2.0) + Math.pow(gForceZ, 2.0);
            float gForce = (float) Math.sqrt(vector);

            //TASK 4: DETERMINE IF THE G-FORCE IS ENOUGH TO REGISTER AS A SHAKE

            if (gForce > SHAKE_THRESHOLD) {
                //IGNORE CONTINUOUS SHAKES - CHECK THAT 500 MILLISECONDS HAVE LAPSED
                final long now = System.currentTimeMillis();
                if (mTimeOfLastShake + SHAKE_TIME_LAPSE > now) {
                    return;
                }
                mTimeOfLastShake = now;

                //THE LISTENER REGISTERED A SHAKE
                mShakeListener.onShake();
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public interface OnShakeListener {
        public void onShake();
    }
}
