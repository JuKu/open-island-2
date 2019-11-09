package de.openislandgame.view.languages;

import com.badlogic.gdx.utils.Array;

public class Languages {
    private static final String LANG_DE = "Deutsch";
    private static final String LANG_EN = "English";

    public static Array<String> getAll(){
        Array<String> allResolutions = new Array<>();
        allResolutions.add(LANG_DE);
        allResolutions.add(LANG_EN);
        return allResolutions;
    }
}
