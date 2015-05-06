package com.pager.doom.smyshliaiev.anton.doompager;

import java.util.HashSet;

/**
 * Created by Toxa on 06.05.2015.
 */
public class PrefferenceObject {
    public int x;
    public int y;
    public Direction direction;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + direction.ordinal();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        PrefferenceObject other = (PrefferenceObject) obj;

        return this.x == other.x && this.y == other.y && this.direction.ordinal() == other.direction.ordinal();
    }
}
