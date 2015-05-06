package com.pager.doom.smyshliaiev.anton.doompager;

import java.util.ArrayList;

/**
 * Created by Toxa on 06.05.2015.
 */
public class DirectionManager {
    private ArrayList<Coordinates> mCoordinates = new ArrayList<>();
    private ArrayList<Direction> mDirections = new ArrayList<>();
    private int mCurDir = 0;
    private Direction mCurDirection = Direction.NORTH;

    public DirectionManager() {
        mDirections.add(Direction.NORTH);
        mDirections.add(Direction.EAST);
        mDirections.add(Direction.SOUTH);
        mDirections.add(Direction.WEST);

        mCoordinates.add(new CoordinatesNorth());
        mCoordinates.add(new CoordinatesEast());
        mCoordinates.add(new CoordinatesSouth());
        mCoordinates.add(new CoordinatesWest());
    }

    public void turnLeft(){
        moveDirectionLeft();
        System.out.println("turn left, x: " + mCoordinates.get(mCurDir).getX() + ", y: " + mCoordinates.get(mCurDir).getY() + ", dir: " + mCurDirection);
    }

    public void turnRight(){
        moveDirectionRight();
        System.out.println("turn left, x: " + mCoordinates.get(mCurDir).getX() + ", y: " + mCoordinates.get(mCurDir).getY() + ", dir: " + mCurDirection);
    }

    public void moveFrw(){
        mCoordinates.get(mCurDir).goForward();
        System.out.println("turn left, x: " + mCoordinates.get(mCurDir).getX() + ", y: " +  mCoordinates.get(mCurDir).getY() +  ", dir: " + mCurDirection);
    }

    public void moveBack(){
        mCoordinates.get(mCurDir).goBackward();
        System.out.println("turn left, x: " + mCoordinates.get(mCurDir).getX() + ", y: " +  mCoordinates.get(mCurDir).getY() +  ", dir: " + mCurDirection);
    }

    public int getX(){
        return mCoordinates.get(mCurDir).getX();
    }

    public int getY(){
        return mCoordinates.get(mCurDir).getY();
    }

    public Direction getDir(){
        return mCurDirection;
    }


    private void moveDirectionRight(){
        mCurDir++;
        if(mCurDir>= mDirections.size()){
            mCurDir = 0;
        }
        mCurDirection = mDirections.get(mCurDir);
    }

    private void moveDirectionLeft(){
        mCurDir--;
        if(mCurDir < 0){
            mCurDir = mDirections.size()-1;
        }
        mCurDirection = mDirections.get(mCurDir);
    }


}
