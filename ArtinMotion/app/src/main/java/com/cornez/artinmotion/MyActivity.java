package com.cornez.artinmotion;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class MyActivity extends Activity {

    //ANIMATION IS SPLIT INTO TWO THREADS:
    //          CALCULATING MOVEMENT
    //          DRAWING MOVEMENT
    private Thread calculateMovementThread;
    private Thread drawMovementThread;

    //HOW MANY MOVING OBJECTS IN THE ARTWORK
    private final int OBJECTS_N = 19;

    //ARTWORK OBJECTS MOVE FROM A CURRENT POSITION TO
    //A FINAL DESTINATION FORMING THE FINAL ARTWORK.
    private int[][] currentPosition = new int[19][2];
    private int[][] finalDestination = new int[19][2];

    private ArtWorkView artworkView;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TASK 1: SET THE LAYOUT
        setContentView(R.layout.activity_my);

        //TASK 2: INITIALIZE THE STARTING
        //        AND ENDING ART LOCATIONS
        initialize();

        //TASK 3: CREATE REFERENCES TO THE RELATIVELAYOUT CONTAINER
        //        AND ADD THE ARTWORK VIEW TO THE LAYOUT
        mainLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
        artworkView = new ArtWorkView(this);
        mainLayout.addView(artworkView, 0);

        //TASK 4: CONSTRUCT THE THREADS TO CALCULATE MOVEMENT
        //        AND ANIMATE (DRAW) THE MOVEMENT
        calculateMovementThread = new Thread(calculateMovement);
        drawMovementThread = new Thread(drawMovement);

        //TASK 5: START THE THREADS
        calculateMovementThread.start();
        drawMovementThread.start();
    }

    //****** CURRENT AND TARGET ARTWORK LOCATIONS ******
    private void initialize() {
        for (int i = 0; i < OBJECTS_N; i++){
            finalDestination[i][0] = 200;  //X POSITION
            finalDestination[i][1] = 200;  //Y POSITION

            currentPosition[i][0] = 20;
            currentPosition[i][1] = 20;
        }
    }

    //****** BUTTONS EVENT HANDLERS ******
    public void createA (View view){
        for (int i = 0; i < OBJECTS_N; i++){
            finalDestination[i][0] = ArtDesign.artA[i][0];
            finalDestination[i][1] = ArtDesign.artA[i][1];
        }
    }

    public void createB (View view){
        for (int i = 0; i < OBJECTS_N; i++){
            finalDestination[i][0] = ArtDesign.artB[i][0];
            finalDestination[i][1] = ArtDesign.artB[i][1];
        }
    }

    public void createC (View view){
        for (int i = 0; i < OBJECTS_N; i++){
            finalDestination[i][0] = ArtDesign.artC[i][0];
            finalDestination[i][1] = ArtDesign.artC[i][1];
        }
    }

    //***** RUNNABLES *****
    private Runnable calculateMovement = new Runnable() {
        private static final int DELAY = 200;
        public void run() {
            try {
                while (true){
                    for (int i = 0; i < OBJECTS_N; i++){
                        currentPosition[i][0] += (finalDestination[i][0] - currentPosition[i][0])/ 5;
                        currentPosition[i][1] += (finalDestination[i][1] - currentPosition[i][1])/ 5;
                    }
                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException e) {}
        }
    };

    private Runnable drawMovement = new Runnable() {
        private static final int DELAY = 200;
        public void run() {
            try {
                while (true){
                    Thread.sleep(DELAY);
                    threadHandler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {}
        }
    };

    //****** HANDLER FOR UPDATING ARTWORK BETWEEN DELAYS ******
    public Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg){
            artworkView.update(currentPosition);
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
