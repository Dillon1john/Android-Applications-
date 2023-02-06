package com.cornez.soundjukebox;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyActivity extends Activity {

    private ImageButton bellClangBtn;
    private ImageButton funkyGongBtn;
    private ImageButton spookyCryBtn;
    private ImageButton randomHaBtn;
    private ImageButton drumSoloBtn;

    private SoundPool soundPool;
    private SparseIntArray soundMap;

    private MediaPlayer mMediaPlayer;
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        configureSounds();
        initializeJukeBoxBtns();
    }

    private void configureSounds() {

        // CONFIGURE THE SOUNDS USE IN THE JUKEBOX
        // PRE-LOAD THE FIRST FOUR SOUNDS
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundMap = new SparseIntArray(4);
        soundMap.put(1, soundPool.load(this, R.raw.bell_clang, 1));
        soundMap.put(2, soundPool.load(this, R.raw.funky_gong, 1));
        soundMap.put(3, soundPool.load(this, R.raw.spooky_cry, 1));
        soundMap.put(4, soundPool.load(this, R.raw.random_ha, 1));

        // FIFTH SOUND WILL BE PLAYED IN MEDIA PLAYER
        mMediaPlayer = MediaPlayer.create(this, R.raw.drum);
        mMediaController = new MediaController(this);
        mMediaController.setEnabled(true);
    }

    private void initializeJukeBoxBtns() {
        // SET REFERENCES TO THE SOUND EFFECT BUTTONS ON THE LAYOUT

        bellClangBtn = (ImageButton) findViewById(R.id.imageButton1);
        funkyGongBtn = (ImageButton) findViewById(R.id.imageButton2);
        spookyCryBtn = (ImageButton) findViewById(R.id.imageButton3);
        randomHaBtn = (ImageButton) findViewById(R.id.imageButton4);
        drumSoloBtn = (ImageButton) findViewById(R.id.imageButton5);

        // REGISTER LISTENER EVENTS FOR THE BUTTONS ON THE LAYOUT
        bellClangBtn.setOnClickListener(playSoundEffect);
        funkyGongBtn.setOnClickListener(playSoundEffect);
        spookyCryBtn.setOnClickListener(playSoundEffect);
        randomHaBtn.setOnClickListener(playSoundEffect);
        drumSoloBtn.setOnClickListener(playSoundEffect);
    }

    private View.OnClickListener playSoundEffect = new View.OnClickListener() {
        public void onClick(View btn) {

            // IDENTIFY THE SOUND TO BE PLAYED
            String soundName = (String) btn.getContentDescription();

            // PLAY THE SOUND
            if (soundName.contentEquals("Bell Clang")) {
                soundPool.play(1, 1, 1, 1, 0, 1.0f);
            }
            else if (soundName.contentEquals("Funky Gong")) {
                soundPool.play(2, 1, 1, 1, 0, 1.0f);
            }
            else if (soundName.contentEquals("Random Ha"))
                soundPool.play(3, 1, 1, 1, 0, 1.0f);
            else if (soundName.contentEquals("Spooky Cry"))
                soundPool.play(4, 1, 1, 1, 0, 1.0f);
            else if (soundName.contentEquals("Drum Solo")) {

                mMediaPlayer.start();
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        mMediaController.show();
        return false;
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
