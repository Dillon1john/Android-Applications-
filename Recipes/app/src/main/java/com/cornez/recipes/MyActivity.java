package com.cornez.recipes;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity{

    private Fragment mFragmentRecipeDirections;
    private Fragment mFragmentRecipePhoto;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mFragmentRecipePhoto = new FragmentRecipePhoto();
        mFragmentRecipeDirections = new FragmentRecipeDirections();

        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fragmentContainer, mFragmentRecipePhoto);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();

    }

    public void flipOver(View view) {
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out);


        mFragmentTransaction.setCustomAnimations(R.anim.rotate_in, R.anim.rotate_out, R.anim.rotate_in, R.anim.rotate_out);


        if(mFragmentRecipePhoto.isVisible()){
            mFragmentTransaction.replace(R.id.fragmentContainer, mFragmentRecipeDirections);
        }else{
            mFragmentTransaction.replace(R.id.fragmentContainer, mFragmentRecipePhoto);
        }
        mFragmentTransaction.commit();
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
