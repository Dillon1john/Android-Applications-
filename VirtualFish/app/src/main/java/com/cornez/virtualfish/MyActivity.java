package com.cornez.virtualfish;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MyActivity extends Activity {

    //ANIMATION IS SPLIT INTO TWO THREADS:
    //          CALCULATING MOVEMENT
    //          FISH TANK UPDATES: UI THREAD
    private Thread calculateMovementThread;

    // FISH TANK ELEMENTS AND PROPERTIES
    private ImageView fishImageView;
    private Fish mFish;
    private int tankWidth;
    private int tankHeight;
    private FrameLayout fishTankLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TASK 1: SET THE LAYOUT
        setContentView(R.layout.activity_my);

        //TASK 2: CREATE REFERENCES TO THE FRAME LAYOUT CONTAINER
        fishTankLayout = (FrameLayout) findViewById(R.id.container);

        //TASK 4: GET THE DIMENSIONS OF THE SCREEN TO USE FOR TH TANK SIZE
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        tankWidth = size.x;
        tankHeight = size.y;

        //TASK 2: INSTANTIATE A FISH
        int initialXPosition = 0;
        int initialYPosition = 0;
        mFish = new Fish(initialXPosition, initialYPosition, Fish.IsSwimming, tankWidth, tankHeight);

        //TASK 3: BUILD THE TANK ELEMENTS
        buildTank();

        //TASK 4: CONSTRUCT THE THREAD TO CALCULATE MOVEMENT
        //        AND ANIMATE THE MOVEMENT
        calculateMovementThread = new Thread(calculateMovement);

        //TASK 5: START THE THREAD
        calculateMovementThread.start();
    }

    private void buildTank() {
        //TASK 1: CREATE A LAYOUT INFLATER TO ADD VISUAL VIEWS TO THE LAYOUT
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //TASK 2: ADD THE FOLIAGE
        ImageView foliageImageView = (ImageView) layoutInflater.inflate(R.layout.foliage_layout, null);
        foliageImageView.setX((float) 0);
        foliageImageView.setY((float) 0);
        foliageImageView.setAlpha((float) .97);
        fishTankLayout.addView(foliageImageView, 0);

        //TASK 3: ADD THE VIRTUAL FISH
        fishImageView = (ImageView) layoutInflater.inflate(R.layout.fish_image, null);
        fishImageView.setScaleX((float) .3);
        fishImageView.setScaleY((float) .3);
        fishImageView.setX(mFish.x);
        fishImageView.setY(mFish.y);
        fishTankLayout.addView(fishImageView, 0);
    }

    //******************* RUNNABLE **********************
    private Runnable calculateMovement = new Runnable() {
        private static final int DELAY = 200;

        public void run() {
            try {
                while (true) {
                    mFish.move();
                    Thread.sleep(DELAY);
                    updateTankHandler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    //******************* HANDLER FOR UPDATING THE FISH BETWEEN SLEEP DELAYS **********************
    public Handler updateTankHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //TASK 1: FACE THE FISH IN THE CORRECT DIRECTION
            fishImageView.setScaleX((float) (.3 * mFish.getFacingDirection()));

            //TASK 2: SET THE FISH AT THE CORRECT XY LOCATION
            fishImageView.setX((float) mFish.x);
            fishImageView.setY((float) mFish.y);
        }
    };

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
