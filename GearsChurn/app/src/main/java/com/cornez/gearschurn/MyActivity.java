package com.cornez.gearschurn;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class MyActivity extends Activity {

    private ImageView gear1Img;
    private ImageView gear2Img;

    private Gear gear1;
    private Gear gear2;

    private float currentDegree;
    private float degree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        gear1Img = (ImageView) findViewById(R.id.imageView1);
        gear2Img = (ImageView) findViewById(R.id.imageView2);

        initializeGears();
    }

    private void initializeGears(){
        //GEAR 1 WILL MOVE IN A CLOCKWISE DIRECTION
        gear1 = new Gear();
        gear1.setStartDegree(0);
        gear1.setEndDegree(360);

        //GEAR 1 WILL MOVE IN A COUNTER-CLOCKWISE DIRECTION
        gear2 = new Gear();
        gear2.setStartDegree(0);
        gear2.setEndDegree(-360);
    }

    public void animateGears(View view){
        final int DELAY = 1000;

        RotateAnimation ra1 = new RotateAnimation(gear1.getStartDegree(), gear1.getEndDegree(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra1.setDuration(DELAY);
        ra1.setFillAfter(true);
        ra1.setInterpolator(new AccelerateInterpolator());
        gear1Img.startAnimation(ra1);

        RotateAnimation ra2 = new RotateAnimation(gear2.getStartDegree(), gear2.getEndDegree(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra2.setDuration(DELAY);
        ra2.setFillAfter(true);
        gear2Img.startAnimation(ra2);
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
