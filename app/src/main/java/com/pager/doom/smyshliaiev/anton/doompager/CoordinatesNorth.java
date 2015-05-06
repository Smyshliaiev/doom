package com.pager.doom.smyshliaiev.anton.doompager;

/**
 * Created by Toxa on 06.05.2015.
 */
public class CoordinatesNorth extends Coordinates {
    public void goForward(){
        if(canInc(y))
            y++;
    }
    public void goBackward(){
        if(canDec(y))
            y--;
    }

}
