package com.pager.doom.smyshliaiev.anton.doompager;

/**
 * Created by Toxa on 06.05.2015.
 */
public class CoordinatesSouth extends Coordinates {
    public void goForward(){
        if(canDec(y))
        y--;
    }
    public void goBackward(){
        if(canInc(y))
        y++;
    }
}
