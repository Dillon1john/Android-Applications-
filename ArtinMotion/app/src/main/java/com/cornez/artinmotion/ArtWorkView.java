package com.cornez.artinmotion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Arrays;

public class ArtWorkView extends View {

    private Paint paint;
    private int[][] artPositions;

    public ArtWorkView(Context context) {
        super(context);

        //HOLDS THE POSITIONS FOR EACH ELEMENT IN THE ARTWORK
        artPositions = new int[19][2];

        //SPECIFY THE PAINT COLOR AND STYLE
        int aquaBlue = Color.argb(255, 148, 205, 204);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15.0f);
        paint.setColor(aquaBlue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //DRAW EACH OF THE ELEMENTS ON THE CANVAS
        for (int i = 0; i < 19; i++) {
            canvas.drawCircle(artPositions[i][0] + 300,
                    artPositions[i][1] + 400, 80, paint);
        }
    }

    public void update(int[][] values) {
        artPositions = Arrays.copyOf(values, values.length);
        invalidate();
    }

}
