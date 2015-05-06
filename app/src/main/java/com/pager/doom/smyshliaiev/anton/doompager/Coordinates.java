package com.pager.doom.smyshliaiev.anton.doompager;

import java.util.ArrayList;

/**
 * Created by Toxa on 06.05.2015.
 */
public abstract class Coordinates {
    protected static int x = 0;
    protected static int y = 0;

    public abstract void goForward();
    public abstract void goBackward();

    public boolean canInc(int num){
        if(num >= 5)return false;
        return true;
    }
    public boolean canDec(int num){
        if(num <= -5)return false;
        return true;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
