package com.cornez.basiccamera;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MyActivity extends Activity {

    //ACTIVITY REQUEST CODE
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1;

    //DIRECTORY NAME TO STORE CAPTURED PHOTOGRAPHS
    private static final String IMAGE_DIRECTORY_NAME = "mycamera";

    //FILE URL TO STORE THE PHOTOGRAPH FOR RESTORATION
    private Uri fileUri;

    private ImageView photoPreview;
    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //SET REFERENCES TO THE IMAGE VIEW FOR THE PHOTO PREVIEW
        photoPreview = (ImageView) findViewById(R.id.imageView);

    }

    public void takePhoto(View view) {
        Intent takePhotoIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;

        try {
            //CREATE THE IMAGE FILE FOR THE PHOTO INTENT
            f = setUpPhotoFile();
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

            //START THE PHOTO CAPTURE INTENT
            startActivityForResult(takePhotoIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();

        //STORE THE ABSOLUTE PATH OF THIS FILE
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile () throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File album = getAlbumDir();
        File imageFile = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, album);
        return imageFile;
    }

    private File getAlbumDir() {
        File albumDir =  new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (albumDir == null){
            Toast.makeText(this, "Failed to create a directory", Toast.LENGTH_LONG).show();
        }
        return albumDir;
    }


    public void addPhotoToGallery (View view){
        //ALTER PHOTO

        BitmapDrawable drawableBmp = (BitmapDrawable) photoPreview.getDrawable();


        Bitmap bmp = drawableBmp.getBitmap();


        int density = bmp.getRowBytes();
        Bitmap invertedBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++){
                int p = bmp.getPixel (x, y);

                int dens = bmp.getDensity();
                dens = (int) (dens * .5);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);

                int red = (int) (r);
                int blue = (int) (b);
               int  green = (int) (g);



                invertedBmp.setPixel (x, y, Color.argb(Color.alpha(p), red, green, blue));
            }
        }
        photoPreview.setImageBitmap(invertedBmp);


/*
        //ADD PHOTO TO GALLERY
        if (mCurrentPhotoPath != null) {
            Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_SHORT).show();
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            //CLEAR OUT THE PATH FOR THE NEXT PHOTO
            mCurrentPhotoPath = null;
        }
*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //CONSTRUCT A PREVIEW OF THE PHOTO
                // PLACE THE PHOTO IN THE IMAGE VIEW
                BitmapFactory.Options mOptions = new
                        BitmapFactory.Options();
                mOptions.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile
                        (mCurrentPhotoPath, mOptions);
                photoPreview.setImageBitmap(bitmap);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Image capture canceled",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Image capture failed",
                        Toast.LENGTH_LONG).show();
            }
        }

       }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //SAVE THE FILE URL IN A BUNDLE TO BE RESTORED
        outState.putParcelable("file_uri", fileUri);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // GRAB THE SAVED FILE FROM THE STORED BUNDLE AND RESTORE IT
        fileUri = savedInstanceState.getParcelable("file_uri");
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
