package de.openislandgame.view.slider;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.jukusoft.engine2d.core.config.Config;

public class SettingsSlider extends Slider {
    private static final String SETTINGS_STRING = "Settings";

    public SettingsSlider(String settingsKey, Skin skin) {
        super(0f, 1f, 0.01f, false, skin);
        float sliderValue = Config.getFloat(SETTINGS_STRING, settingsKey);
        setValue(sliderValue);
    }
}
