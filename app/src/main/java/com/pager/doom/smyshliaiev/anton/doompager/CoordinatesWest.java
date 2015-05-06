package com.pager.doom.smyshliaiev.anton.doompager;

/**
 * Created by Toxa on 06.05.2015.
 */
public class CoordinatesWest extends Coordinates {
    public void goForward(){
        if(canDec(x))
        x--;
    }
    public void goBackward(){
        if(canInc(x))
        x++;
    }

}