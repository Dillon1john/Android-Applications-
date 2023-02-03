package com.cornez.asynctaskexplore;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyActivity extends Activity {
    private Button downloadBtn;
    private ProgressBar downloadProgressBar;
    private TextView downloadProgressTextView;
    private TextView callBackDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        downloadBtn = (Button) findViewById(R.id.button1);
        downloadProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        downloadProgressTextView = (TextView) findViewById(R.id.textView2);
        callBackDisplayTextView = (TextView) findViewById(R.id.textView3);
    }

    public void clearDisplay(View view) {
        callBackDisplayTextView.setText(" ");
    }

    public void startDownload(View view) {
        // DOWNLOAD IS PERFORMED.
        downloadBtn.setEnabled(false);
        new PerformAsyncTask().execute();
    }

    private class PerformAsyncTask extends AsyncTask<Void, Integer, Void> {
        int progress_status;

        @Override
        protected void onPreExecute() {
            // UPDATE THE UI IMMEDIATELY BEFORE BACKGROUND WORK IS PERFORMED
            super.onPreExecute();

            callBackDisplayTextView.setText(callBackDisplayTextView.getText()
                    + "\n\nLock the screen orientation()");
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            callBackDisplayTextView.setText(callBackDisplayTextView.getText()
                    + "\nInvoke onPreExecute()");
            progress_status = 0;
            downloadProgressTextView.setText("downloading 0%");

            callBackDisplayTextView.setText(callBackDisplayTextView.getText()
                    + "\nCompleted onPreExecute()");
            callBackDisplayTextView.setText(callBackDisplayTextView.getText()
                    + "\nInvoke doInBackground()");
            callBackDisplayTextView.setText(callBackDisplayTextView.getText()
                    + "\nPerforming background work...");
        }

        @Override
        protected Void doInBackground(Void... params) {

            while (progress_status < 100) {
                progress_status += 2;

                publishProgress(progress_status);
                SystemClock.sleep(300);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // UPDATE THE UI AS BACKGROUND TASKS ARE BEING PERFORMED
            downloadProgressBar.setProgress(values[0]);
            downloadProgressTextView.setText("downloading "
                    + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // UPDATE THE FINAL UI IMMEDIATELY AFTER THE TASK IS EXECUTED
            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nCompleted backgound work");
            callBackDisplayTextView.setText(callBackDisplayTextView.getText() + "\nInvoke onPostExecute()");

            downloadProgressTextView.setText("download complete");
            downloadBtn.setEnabled(true);
        }
    }

    ;

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

