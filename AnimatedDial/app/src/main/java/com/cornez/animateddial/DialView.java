package com.cornez.animateddial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


public class DialView  extends View{

    private float angle;
    private Paint paint;

    public DialView (Context context) {
        super(context);

        //SET THE ANGLE
        angle = 0;

        //INITIALIZE THE PAINT
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(150);

    }
    @Override
    protected void onDraw(Canvas canvas) {

        //TASK 1: FILL THE BACKGROUND OF THE CANVAS
        canvas.drawRGB(248,  232,  198);

        //TASK 2: COMPUTE THE WIDTH AND HEIGHT OF THE CANVAS
        //        THIS WILL CHANGE DEPENDING ON ORIENTATION
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int radius;
        if (w > h) {
            radius = h/2;
        }else {
            radius = w/2;
        }

        //TASK 3: DRAW THE CIRCLES
        paint.setColor(Color.rgb(126, 79, 43));
        canvas.drawCircle(w/2, h/2, radius, paint);

        paint.setColor(Color.rgb(224, 153, 78));
        canvas.drawCircle(w/2, h/2, radius/2, paint);

        paint.setColor(Color.rgb(207, 69, 56));
        canvas.drawCircle(w/2, h/2, radius/4, paint);

        //TASK 4: COMPUTE THE NEW ANGLE
        angle += 1;
        if (angle > 360)
            angle = 0;


        //TASK 5: DRAW THE LINE FROM THE CENTER TO THE RIM
        float radians = (float) (angle * (180/Math.PI));
        float stopX = (float) (w/2 + radius * Math.sin(radians));
        float stopY = (float) (h/2 - radius * Math.cos(radians));
        paint.setColor(Color.rgb(132, 175, 166));
        canvas.drawLine(w/2, h/2, stopX, stopY, paint);
    }

    public void update () {
        invalidate();
    }





}
