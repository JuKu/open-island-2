package de.openislandgame.view.resolutions;

import com.badlogic.gdx.utils.Array;

public class Resolution {
    private static final String RES_1024_768 = "1024x768";
    private static final String RES_1280_720 = "1280x720";

    public static Array<String> getAll(){
        Array<String> allResolutions = new Array<>();
        allResolutions.add(RES_1024_768);
        allResolutions.add(RES_1280_720);
        return allResolutions;
    }
}
