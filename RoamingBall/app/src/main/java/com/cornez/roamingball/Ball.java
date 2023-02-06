package com.cornez.roamingball;


public class Ball {

    private float mX;
    private float mY;
    private int mWidth;
    private float mTop;
    private float mBottom;
    private float mLeft;
    private float mRight;
    private float mVelocityX;
    private float mVelocityY;

    public void setX(float x){
        mX = x;
    }
    public float getX(){
        return mX;
    }

    public void setY(float y){
        mY = y;
    }
    public float getY(){
        return mY;
    }


    public void setWidth(int width){
        mWidth = width;
    }
    public int getWidth(){
        return mWidth;
    }
    public void setVelocityX(float velocityX){
        mVelocityX = velocityX;
    }
    public float getVelocityX(){
        return mVelocityX;
    }
    public void setVelocityY(float velocityY) {
        mVelocityY = velocityY;
    }
    public float getVelocityY(){
        return mVelocityY;
    }
}
