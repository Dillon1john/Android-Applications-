package com.cornez.roamingball;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MyActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private LayoutInflater layoutInflater;
    private RelativeLayout mainLayout;
    private ImageView ballImage;
    private Ball mBall;

    private Thread movementThread;

    static int TOP;
    static int BOTTOM;
    static int LEFT;
    static int RIGHT;

    private TextView x_axis;
    private TextView y_axis;
    private TextView z_axis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        // SET THE REFERENCES TO THE LAYOUTS
        mainLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        x_axis = (TextView) findViewById(R.id.textView2);
        y_axis = (TextView) findViewById(R.id.textView4);
        z_axis = (TextView) findViewById(R.id.textView6);

        // ADD THE BALL AND INITIALIZE MOVEMENT SETTINGS
        mBall = new Ball();
        initializeBall();
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ballImage = (ImageView) layoutInflater.inflate(R.layout.ball_item,
                null);
        ballImage.setX(50.0f);
        ballImage.setY(50.0f);
        mainLayout.addView(ballImage, 0);


        // REGISTER THE SENSOR MANAGER
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // IMPLEMENT THE MOVEMENT THREAD
        movementThread = new Thread(BallMovement);
    }

    private void initializeBall() {
        //COMPUTE THE WIDTH AND HEIGHT OF THE DEVICE
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        //CONFIGURE THE ROAMING BALL
        mBall.setX(50.0f);
        mBall.setY(50.0f);
        mBall.setWidth(225);

        mBall.setVelocityX(0.0f);
        mBall.setVelocityY(0.0f);

        TOP = 0;
        BOTTOM = screenHeight - mBall.getWidth();
        LEFT = 0;
        RIGHT = screenWidth - mBall.getWidth();
    }


    // Registers the listener
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        movementThread.start();
    }

    // Unregister the listener
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensorAccelerometer);
    }

    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onDestroy(){
        finish();
        super.onDestroy();
    }

    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mBall.setVelocityX(sensorEvent.values[0]);
            mBall.setVelocityY(sensorEvent.values[1]);

            x_axis.setText(" " + sensorEvent.values[0]);
            y_axis.setText(" " + sensorEvent.values[1]);
            z_axis.setText(" " + sensorEvent.values[2]);
        }
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }


    // UPDATES THE BALL POSITION CONTINUOUSLY
    private Runnable BallMovement = new Runnable() {
        private static final int DELAY = 20;

        public void run() {
            try {
                while (true) {
                    mBall.setX(mBall.getX() - mBall.getVelocityX());
                    mBall.setY(mBall.getY() + mBall.getVelocityY());

                    // CHECK FOR COLLISIONS
                    if (mBall.getY() < TOP)
                        mBall.setY(TOP);
                    else   if (mBall.getY() > BOTTOM)
                        mBall.setY(BOTTOM);

                    if (mBall.getX() < LEFT)
                        mBall.setX(LEFT);
                    else if (mBall.getX() > RIGHT)
                        mBall.setX(RIGHT);


                    //DELAY BETWEEN ANIMATIONS
                    Thread.sleep(DELAY);

                    //HANDLE THE RELOCATION OF THE VIEW (IMAGEVIEW)
                    threadHandler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
            }
        }
    };

    public Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //HANDLE THE RELOCATION OF THE IMAGEVIEW
            ballImage.setX(mBall.getX());
            ballImage.setY(mBall.getY());
        }
    };


    // @Override
    // Stops the user from changing the Orientation.
    // If the user rotates the device it will not
    // change to the landscape orientation.
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu.
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
