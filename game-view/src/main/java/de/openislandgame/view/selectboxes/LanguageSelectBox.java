package de.openislandgame.view.selectboxes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.i18n.I;
import de.openislandgame.view.languages.Languages;

import java.util.HashMap;
import java.util.Map;

public class LanguageSelectBox extends SelectBox<String> {

    private static final String SETTINGS_STRING = "I18N";
    private static final String SETTINGS_LANG_TOKEN = "token";
    private static final Map<String, String> LANGUAGE_TO_SHORT = new HashMap<>();
    private static final Map<String, String> SHORT_TO_LANGUAGE = new HashMap<>();

    //TODO: remove this hardcoded constants and load them from a config file or the specific i18n directories
    static {
        LANGUAGE_TO_SHORT.put("Deutsch", "de");
        LANGUAGE_TO_SHORT.put("English", "en");
        SHORT_TO_LANGUAGE.put("de", "Deutsch");
        SHORT_TO_LANGUAGE.put("en", "English");
    }

    public LanguageSelectBox(Skin skin) {
        super(skin);
        setItems(Languages.getAll());
        String language = SHORT_TO_LANGUAGE.get(Config.get(SETTINGS_STRING, SETTINGS_LANG_TOKEN));
        setSelected(language);

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selectedLang = getSelected();

                String selectedLangToken = LANGUAGE_TO_SHORT.get(selectedLang);

                I.setLanguage(selectedLangToken);
                Config.set(SETTINGS_STRING, SETTINGS_LANG_TOKEN, selectedLangToken);
            }
        });
    }

}
