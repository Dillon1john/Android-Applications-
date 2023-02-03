package com.cornez.animateddial;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class MyActivity extends Activity {

    private Thread animationThread;
    private DialView dialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //INSTANTIATE A DIAL VIEW OBJECT
        dialView = new DialView(this);

        //SET THE CONTENT OF THIS ACTIVITY TO THE DIAL VIEW
        setContentView(dialView);

        //CONSTRUCT A THREAD TO ANIMATE THE DIAL
        animationThread = new Thread(runningAnimation);
        animationThread.start();
    }

    private Runnable runningAnimation = new Runnable() {
        private static final int DELAY = 200;

        public void run() {
            try {
                while (true) {
                    Thread.sleep(DELAY);
                    threadHandler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            dialView.update();
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        threadHandler.removeCallbacks(runningAnimation);
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
