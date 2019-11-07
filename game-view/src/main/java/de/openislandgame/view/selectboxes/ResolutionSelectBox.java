package de.openislandgame.view.selectboxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jukusoft.engine2d.core.config.Config;
import de.openislandgame.view.resolutions.Resolution;
import de.openislandgame.view.settings.SettingsKeys;

public class ResolutionSelectBox extends SelectBox<String> {
    private static final String SETTINGS_STRING = "Settings";

    public ResolutionSelectBox(Skin skin) {
        super(skin);
        setItems(Resolution.getAll());
        String resolution = Config.get(SETTINGS_STRING, "resolution");
        setSelected(resolution);

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selectedRes = getSelected();

                Config.set(SETTINGS_STRING, "resolution", selectedRes);
            }
        });
    }
}