package de.openislandgame.view.languages;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.basegame.i18n.I18NHelper;

import java.util.List;
import java.util.stream.Collectors;

public class Languages {

    public static Array<String> getAll() {
        Array allLanguages = new Array();
        I18NHelper.listAvailableLanguagePacks()
                .stream()
                .map(langPack -> langPack.getTitle())
                .forEach(langTitle -> allLanguages.add(langTitle));

        return allLanguages;
    }

}
