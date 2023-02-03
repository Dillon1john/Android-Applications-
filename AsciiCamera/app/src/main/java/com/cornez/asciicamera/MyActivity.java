package com.cornez.asciicamera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyActivity extends Activity {

    //CREATE A REQUEST CODE TO REQUEST A PHOTOGRAPH
    private static final int
            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    //OBJECTS USED BY THE APP
    private Bitmap photoCaptured;
    private TextView asciiImage;
    private Button asciiButton;
    private Button cameraButton;
    private ProgressBar asciiProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //REFERENCE THE TEXTVIEW THAT WILL HOLD THE ASCII IMAGE
        asciiImage = (TextView) findViewById(R.id.textView1);

        //REFERENCE THE APPLICATION BUTTONS AND THE PROGRESS BAR
        cameraButton = (Button) findViewById(R.id.button1);
        asciiButton = (Button) findViewById(R.id.button2);
        asciiProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //DISABLE THE ASCII BUTTON PRIOR TO CAPTURING A PHOTO
        asciiButton.setEnabled(false);

        //HIDE THE PROGRESS BAR
        asciiProgressBar.setAlpha(0);
    }

    //ON CLICK EVENT HANDLER TO LAUNCH THE CAMERA
    public void toCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    // CALLED ONCE THE CAMERA HAS LAUNCHED AND
    // A PHOTOGRAPH HAS BEEN TAKEN
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //REFERENCE THE IMAGEVIEW - LOCATED ON THE LAYOUT
        ImageView photoPreview = (ImageView)
                findViewById(R.id.imageView1);


        //VERIFY A PHOTO WAS TAKEN
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //COLLECT THE PHOTOGRAPH TAKEN BY THE CAMERA
                // AND PLACE IT IN THE IMAGE VIEW
                Bundle extras = data.getExtras();
                photoCaptured = (Bitmap) extras.get("data");
                photoPreview.setImageBitmap(photoCaptured);
                asciiButton.setEnabled(true);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "result canceled",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "image capture failed", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    //CONVERT THE IMAGE TO ASCII TEXT
    public void toAsciiConversion(View view) {
        //PROCESS THE PHOTOGRAPH IN AN ASYNCTASK
        new PerformAsyncTask().execute();
    }

    private class PerformAsyncTask extends AsyncTask<Void, Void, Void> {

        //DECLARE A STRING TO STORE THE COMPLETE ASCII TEXT
        String asciiArtWork;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //DISABLE THE APPLICATION BUTTONS
            cameraButton.setEnabled(false);
            asciiButton.setEnabled(false);

            //DISPLAY THE PROGRESS BAR
            asciiProgressBar.setAlpha(1);

            //CLEAR THE TEXT STRING FOR THE FINAL ARTWORK
            asciiArtWork = "\n";
        }

        @Override
        protected Void doInBackground(Void... params) {
            //TASK 1: ACCESS THE PHOTOGRAPH CAPTURED BY THE CAMERA
            Bitmap photoThumbnail = photoCaptured;

            //GET THE WIDTH AND HEIGHT OF THE PREVIEW IMAGE
            int thumbnailWidth = photoThumbnail.getWidth();
            int thumbnailHeight = photoThumbnail.getHeight();

            int scaleWidth = 2; // shrink bitmap height
            int scaleHeight = 2; // shrink bitmap height

            //GENERATE ASCII ARTWORK PIXEL BY PIXEL
            for (int y = 0; y < thumbnailHeight / scaleHeight; y++) {
                for (int x = 0; x < thumbnailWidth / scaleWidth; x++) {
                    // COLLECT PIXEL INFORMATION AT A SPECIFIC LOCATION
                    int pixel = photoThumbnail.getPixel(x * scaleWidth, y * scaleHeight);

                    // COLLECT THE RED, GREEN, AND BLUE VALUES
                    int redVal = Color.red(pixel);
                    int greenVal = Color.green(pixel);
                    int blueVal = Color.blue(pixel);

                    // COMPUTE THE GRAYSCALE VALUE
                    int grayVal = (redVal + greenVal + blueVal) / 3;

                    // MATCH GRAY VALUE SPECTRUMS WITH DIFFERENT ASCII CHARACTERS
                    if (grayVal < 35)
                        asciiArtWork += "@@";
                    else if (grayVal <= 52)
                        asciiArtWork += "$$";
                    else if (grayVal <= 69)
                        asciiArtWork += "##";
                    else if (grayVal <= 86)
                        asciiArtWork += "%%";
                    else if (grayVal <= 103)
                        asciiArtWork += "**";
                    else if (grayVal <= 120)
                        asciiArtWork += "++";
                    else if (grayVal <= 137)
                        asciiArtWork += "vV";
                    else if (grayVal <= 154)
                        asciiArtWork += "- ";
                    else if (grayVal <= 171)
                        asciiArtWork += "  ";
                    else if (grayVal <= 188)
                        asciiArtWork += ";;";
                    else if (grayVal <= 205)
                        asciiArtWork += "::";
                    else if (grayVal <= 222)
                        asciiArtWork += "..";
                    else
                        asciiArtWork += "  ";
                }
                // ADD A LINE BREAK AT THE END OF EACH LINE
                asciiArtWork += "\n";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //ENABLE THE APPLICATION BUTTONS
            cameraButton.setEnabled(true);
            asciiButton.setEnabled(true);

            //TURN THE PROGRESS BAR OFF - MAKE IT INVISIBLE
            asciiProgressBar.setAlpha(0);

            //DISPLAY THE ASCII ARTWORK
            asciiImage.setText(asciiArtWork);
        }
    }

    public void takeScreenSnapShot() {
        //GET A BITMAP FROM THE TEXTVIEW
        Bitmap bitmap = loadBitmapFromView(this, asciiImage);
        String mPath = Environment.getExternalStorageDirectory() + File.separator + "screen_" + System.currentTimeMillis() + ".jpeg";
        File imageFile = new File(mPath);
        OutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmapFromView(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        view.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        view.draw(c);
        return returnedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // INFLATE THE MENU
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
