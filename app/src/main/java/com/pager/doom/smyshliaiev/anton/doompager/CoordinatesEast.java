package com.pager.doom.smyshliaiev.anton.doompager;

/**
 * Created by Toxa on 06.05.2015.
 */
public class CoordinatesEast extends Coordinates {
    public void goForward(){
        if(canInc(x))
            x++;
    }
    public void goBackward(){
        if(canDec(x))
            x--;
    }

}