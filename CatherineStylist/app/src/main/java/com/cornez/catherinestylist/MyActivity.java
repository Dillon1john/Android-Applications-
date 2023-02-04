package com.cornez.catherinestylist;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MyActivity extends Activity {

    // MANNEQUIN CONTAINERS
    private LinearLayout headContainer, bodyContainer, feetContainer;

    // HAIR IMAGES AND CONTAINERS
    private ImageView hair1, hair2;
    private ImageView dress1, dress2;
    private ImageView shoes1, shoes2;

    // CONTAINERS
    private LinearLayout hairContainer1, hairContainer2;
    private LinearLayout dressContainer1, dressContainer2;
    private LinearLayout shoeContainer1, shoeContainer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //REFERENCE THE STYLE ELEMENTS AND THE CONTAINERS
        getMannequin();
        getHairStyles();
        getDressStyles();
        getShoeSyles();
    }


    void getMannequin() {
        // IDENTIFY THE MANNEQUIN ELEMENTS
        headContainer = (LinearLayout) findViewById(R.id.headcontainer);
        bodyContainer = (LinearLayout) findViewById(R.id.bodycontainer);
        feetContainer = (LinearLayout) findViewById(R.id.feetcontainer);

        // REGISTER MANNEQUIN LISTENER EVENTS
        headContainer.setOnDragListener(new ChoiceDragListener());
        bodyContainer.setOnDragListener(new ChoiceDragListener());
        feetContainer.setOnDragListener(new ChoiceDragListener());
    }

    void getHairStyles() {
        // IDENTIFY THE HAIR ELEMENTS
        hair1 = (ImageView) findViewById(R.id.h1);
        hair2 = (ImageView) findViewById(R.id.h2);
        hairContainer1 = (LinearLayout) findViewById(R.id.hair1container);
        hairContainer2 = (LinearLayout) findViewById(R.id.hair2container);

        // REGISTER HAIR LISTENER EVENTS
        hair1.setOnTouchListener(new ChoiceTouchListener());
        hair2.setOnTouchListener(new ChoiceTouchListener());
        hairContainer1.setOnDragListener(new ChoiceDragListener());
        hairContainer2.setOnDragListener(new ChoiceDragListener());
    }

    void getDressStyles() {
        // IDENTIFY THE DRESS ELEMENTS
        dress1 = (ImageView) findViewById(R.id.dress1);
        dress2 = (ImageView) findViewById(R.id.dress2);
        dressContainer1 = (LinearLayout) findViewById(R.id.dress1container);
        dressContainer2 = (LinearLayout) findViewById(R.id.dress2container);

        // REGISTER DRESS LISTENER EVENTS
        dress1.setOnTouchListener(new ChoiceTouchListener());
        dress2.setOnTouchListener(new ChoiceTouchListener());
        dressContainer1.setOnDragListener(new ChoiceDragListener());
        dressContainer2.setOnDragListener(new ChoiceDragListener());
    }

    void getShoeSyles() {
        // IDENTIFY THE SHOE ELEMENTS
        shoes1 = (ImageView) findViewById(R.id.shoes1);
        shoes2 = (ImageView) findViewById(R.id.shoes2);
        shoeContainer1 = (LinearLayout) findViewById(R.id.shoes1container);
        shoeContainer2 = (LinearLayout) findViewById(R.id.shoes2container);

        // REGISTER SHOE LISTENER EVENTS
        shoes1.setOnTouchListener(new ChoiceTouchListener());
        shoes2.setOnTouchListener(new ChoiceTouchListener());
        shoeContainer1.setOnDragListener(new ChoiceDragListener());
        shoeContainer2.setOnDragListener(new ChoiceDragListener());
    }

    void makeAToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionevent) {
            if (motionevent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);

                // MAKE THE CLOSET ITEM INVISIBLE DURING THE DRAG
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements View.OnDragListener {

        public boolean onDrag(View destinationV, DragEvent e) {
            // IDENTIFY ELEMENTS BEING DRAGGED AND DROPPED
            View draggedV = (View) e.getLocalState();
            ViewGroup parentSourceV = (ViewGroup) draggedV.getParent();
            LinearLayout container = (LinearLayout) destinationV;


            switch (e.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    draggedV.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    if (container.getChildCount() < 1) {
                        // ITEMS MOVED TO THE MANNEQUIN
                        if (container.equals(feetContainer)) {
                            if (draggedV.equals(shoes1) || draggedV.equals(shoes2))
                                swapItems(parentSourceV, draggedV, container, 2.0f);
                            else {
                                draggedV.setVisibility(View.VISIBLE);
                            }
                        } else if (container.equals(bodyContainer)) {
                            if (draggedV.equals(dress1) || draggedV.equals(dress2))
                                swapItems(parentSourceV, draggedV, container, 2.0f);
                            else {
                                draggedV.setVisibility(View.VISIBLE);
                            }
                        } else if (container.equals(headContainer)) {
                            if (draggedV.equals(hair1) || draggedV.equals(hair2))
                                swapItems(parentSourceV, draggedV, container, 2.0f);
                            else {
                                makeAToast("You can only place hair on Catherine\'s head.");
                                draggedV.setVisibility(View.VISIBLE);
                            }
                        }
                        // ITEMS RETURNED TO THEIR STORAGE LOCATION
                        else if (container.equals(shoeContainer1)
                                || container.equals(shoeContainer2)) {
                            if (draggedV.equals(shoes1) || draggedV.equals(shoes2))
                                swapItems(parentSourceV, draggedV, container, 1.0f);
                            else
                                draggedV.setVisibility(View.VISIBLE);
                        } else if (container.equals(hairContainer1)
                                || container.equals(hairContainer2)) {
                            if (draggedV.equals(hair1) || draggedV.equals(hair2))
                                swapItems(parentSourceV, draggedV, container, 1.0f);
                            else
                                draggedV.setVisibility(View.VISIBLE);
                        } else if (container.equals(dressContainer1)
                                || container.equals(dressContainer2)) {
                            if (draggedV.equals(dress1) || draggedV.equals(dress2))
                                swapItems(parentSourceV, draggedV, container, 1.0f);
                            else
                                draggedV.setVisibility(View.VISIBLE);
                        }

                    }
                    break;

                default:
                    break;
            }
            return true;
        }
    }

    void swapItems(ViewGroup parentSourceV, View draggedV,
                   LinearLayout container, float scale) {
        parentSourceV.removeView(draggedV);
        container.addView(draggedV);
        draggedV.setPivotX(0);
        draggedV.setPivotY(0);
        draggedV.setScaleX(scale);
        draggedV.setScaleY(scale);

        draggedV.setVisibility(View.VISIBLE);
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
