package com.cornez.countingthreadexample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MyActivity extends Activity {

    //DECLARE UI TEXTVIEW AND COUNT OBJECT
    private TextView countTextView;
    private Integer count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //REFERENCE THE TEXTVIEW UI ELEMENT ON THE LAYOUT
        countTextView = (TextView) findViewById(R.id.textView);

        //INITIALIZE THE COUNTER
        count = 0;

        //CREATE A THREAD AND START IT
        Thread thread = new Thread (countNumbers);
        thread.start();

    }

    //INITIALIZE THE COUNTER TO ZERO EACH TIME THE
    //APPLICATION LAUNCHES
    @Override
    protected void onStart() {
        super.onStart();
        count = 0;
    }

    //*************RUNNABLE **************/
    private Runnable countNumbers = new Runnable () {
        private static final int DELAY = 1000;
        public void run() {
            try {
                while (true) {
                    count ++;
                    Thread.sleep (DELAY);
                    threadHandler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };

    //**************HANDLER****************/
    public Handler threadHandler = new Handler() {
        public void handleMessage (android.os.Message message){
            countTextView.setText(count.toString());
        }

    };

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
