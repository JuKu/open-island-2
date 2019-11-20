package de.openislandgame.view.selectboxes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jukusoft.engine2d.core.config.Config;
import de.openislandgame.view.languages.Languages;
import de.openislandgame.view.resolutions.Resolution;

import java.util.HashMap;
import java.util.Map;

public class LanguageSelectBox extends SelectBox<String> {
    private static final String SETTINGS_STRING = "Settings";
    private static final Map<String, String> LANGUAGE_TO_SHORT = new HashMap<>();
    private static final Map<String, String> SHORT_TO_LANGUAGE = new HashMap<>();
    static {
        LANGUAGE_TO_SHORT.put("Deutsch", "de");
        LANGUAGE_TO_SHORT.put("English", "en");
        SHORT_TO_LANGUAGE.put("de", "Deutsch");
        SHORT_TO_LANGUAGE.put("en", "English");
    }

    public LanguageSelectBox(Skin skin) {
        super(skin);
        setItems(Languages.getAll());
        String language = SHORT_TO_LANGUAGE.get(Config.get(SETTINGS_STRING, "lang"));
        setSelected(language);

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selectedLang = getSelected();

                Config.set(SETTINGS_STRING, "lang", LANGUAGE_TO_SHORT.get(selectedLang));
            }
        });
    }
}
