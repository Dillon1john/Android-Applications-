package com.cornez.virtualfish;

public class Fish {
    // FISH ARTWORK OBJECT HAS A CURRENT
    // POSITION TO THAT CAN BE ACCESSED PUBLICLY
    public int x;
    public int y;


    public static final int IsHungry = 1;
    public static final int IsSwimming = 2;
    public static final int IsEating = 3;

    private int mCondition;
    private int mVelocity;
    private int mStomachCapacity;
    private int mFoodInStomach;
    private int mTankWidth;
    private int mTankHeight;
    private int mDirection;

    private int playX, playY;
    private int foodX, foodY;

    public Fish(int xPos, int yPos, int condition, int tankWidth, int tankHeight) {
        mCondition = condition;
        mVelocity = 3;
        mStomachCapacity = 80;
        mFoodInStomach = 80;
        mTankWidth = tankWidth;
        mTankHeight = tankHeight;
        x = xPos;
        y = yPos;
        mDirection = 1;

        // FOOD AND EXPLORE LOCATIONS ARE FIXED
        // IN THE TOP AND BOTTOM OF THE TANK
        foodY = (int) tankHeight / 2 - 100;
        foodX = (int) (Math.ceil(Math.random() * mTankWidth) - mTankWidth / 2);
        playY = (int) -(Math.random() * mTankHeight / 2) + 100;
        playX = (int) (Math.ceil(Math.random() * mTankWidth) - mTankWidth / 2);
    }

    public void move() {
        //EXAMINE POSSIBLE CONDITIONS
        switch (mCondition) {
            case IsSwimming:
                swim();
                break;
            case IsHungry:
                findFood();
                break;
            case IsEating:
                eatFood();
        }
    }

    private void swim() {
        //TASK 1: BURN A CALORIE OF FOOD;
        mFoodInStomach--;

        //TASK 2: SWIM TOWARD A POINT OF INTEREST: playX, playY
        int xDistance = playX - x;
        int yDistance = playY - y;
        x += xDistance / mVelocity;
        y += yDistance / mVelocity;
        if (playX < x) {
            mDirection = -1;
        } else {
            mDirection = 1;
        }

        //TASK 3: FIND ANOTHER PLACE TO EXPLORE
        //        IN THE TOP HALF OF THE TANK
        if (Math.abs(xDistance) < 5 && Math.abs(yDistance) < 5) {
            playX = (int) (Math.ceil(Math.random() * mTankWidth) - mTankWidth / 2);
            playY = (int) -(Math.random() * mTankHeight / 2) + 100;
        }

        //TASK 4: DETERMINE IF STOMACH IS EMPTY
        if (mFoodInStomach <= 0) {
            mCondition = IsHungry;
            //FIND A PLACE TO EAT IN THE BOTTOM OF THE TANK
            foodX = (int) (Math.ceil(Math.random() * mTankWidth) - mTankWidth / 2) - 100;
        }
    }

    private void findFood() {

        //TASK : SWIM TOWARD FOOD: foodX, foodY
        int xDistance = foodX - x;
        int yDistance = foodY - y;

        x += xDistance / mVelocity;
        y += yDistance / mVelocity;

        //TURN FISH IN DIRECTION OF FOOD
        if (foodX < x) {
            mDirection = -1;
        } else {
            mDirection = 1;
        }

        //TASK 3: DETERMINE IF FOOD IS FOUND
        if (Math.abs((x - foodX)) <= 10 && Math.abs(y - foodY) <= 10) {
            mCondition = IsEating;
        }
    }

    private void eatFood() {

        //TASK 1: ADD A CALORIE OF FOOD TO THE STOMACH;
        mFoodInStomach += 4;

        //TASK 3: DETERMINE IF STOMACH IS FULL
        if (mFoodInStomach >= mStomachCapacity) {
            mCondition = IsSwimming;

            //FIND A NEW PLACE TO PLAY
            playX = (int) (Math.ceil(Math.random() * mTankWidth) - mTankWidth / 2);
            playY = (int) -(Math.random() * mTankHeight / 2) + 100;
        }
    }

    public int getFacingDirection() {
        return mDirection;
    }
}
