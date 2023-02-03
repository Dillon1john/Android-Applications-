package com.cornez.bullseye;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class BullsEyeView  extends View {

    private Paint paint;

    //VALUES FOR THE RED, GREEN, AND BLUE VALUES
    private int redVal;
    private int greenVal;
    private int blueVal;

    public BullsEyeView (Context context) {
        super(context);

        //INITIAL VALUES FOR RED, GREEN, AND BLUE
        redVal = 248;
        greenVal = 232;
        blueVal = 198;

        paint = new Paint();
    }

    public void onDraw (Canvas canvas) {
        //INITIALIZE THE CENTER OF THE CANVAS
        float centerX = canvas.getWidth() / 2;
        float centerY = canvas.getHeight() / 2;

        //INITIALIZE THE RADIUS FOR THE FIRST RING
        float radius = canvas.getHeight() / 2;

        //TASK 1: FILL THE ENTIRE CANVAS WITH A BEIGE COLOR
        canvas.drawRGB(194,  183,  158);

        //TASK 2: DRAW A SET OF FIVE RINGS
        int ringRed = redVal;
        int ringGreen = greenVal;
        int ringBlue = blueVal;

        for (int i = 1; i <= 5; i++) {
            //DRAW A SINGLE RING
            paint.setStyle(Style.FILL);
            paint.setColor(Color.rgb(ringRed, ringGreen, ringBlue));
            canvas.drawCircle(centerX, centerY, radius,  paint);

            //RESET THE COLOR AND SIZE FOR THE NEXT RING
            ringRed -= 13;
            ringGreen -= 13;
            ringBlue -= 13;
            radius -= 120;
        }
    }
}
