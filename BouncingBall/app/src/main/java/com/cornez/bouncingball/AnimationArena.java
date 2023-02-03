package com.cornez.bouncingball;

import android.graphics.Canvas;

public class AnimationArena {
    private Ball mBall;

    public AnimationArena () {
        //INSTANTIATE THE BALL
        mBall = new Ball();
    }

    public void update (int width, int height) {
        mBall.move(0, 0, width, height);
    }

    public void draw (Canvas canvas) {
        //WIPE THE CANVAS CLEAN
        canvas.drawRGB(156, 174, 216);

        //DRAW THE BALL
        mBall.draw(canvas);
    }
}
