package de.openislandgame.view.textfields;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.jukusoft.engine2d.core.config.Config;

public class SliderDisplayTextField extends TextField {
    private static final String SETTINGS_STRING = "Settings";
    private String settingsKey;


    public SliderDisplayTextField(String settingsKey, Skin skin) {
        super("", skin);
        this.settingsKey = settingsKey;
        float settingsValue = Config.getFloat(SETTINGS_STRING, settingsKey);
        String settingsString = Integer.toString((int) (settingsValue*100));
        setText(settingsString);
        setDisabled(true);
        setAlignment(Align.center);
    }

    public SliderDisplayTextField(String settingsKey, Skin skin, Slider slider){
        this(settingsKey, skin);
        setObservedSlider(slider);
    }

    public void setObservedSlider(Slider slider){
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                int sliderValue = (int) (slider.getValue()*100);
                String text = Integer.toString(sliderValue);

                Config.set(SETTINGS_STRING, settingsKey, String.valueOf(slider.getValue()));

                setText(text);
            }
        });
    }
}
