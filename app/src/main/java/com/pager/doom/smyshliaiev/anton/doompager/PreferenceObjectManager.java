package com.pager.doom.smyshliaiev.anton.doompager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by Toxa on 06.05.2015.
 */
public class PreferenceObjectManager {
    private Map<Integer, String> mPrefferenceObjects = new HashMap<>();

    public PreferenceObjectManager() {
    }

    public Map<Integer, String> getPrefferenceObjects() {
        return mPrefferenceObjects;
    }

    public void setPrefferenceObjects(Map<Integer, String> mPrefferenceObjects) {
        this.mPrefferenceObjects = mPrefferenceObjects;
    }
}
