package de.openislandgame.view.selectboxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jukusoft.engine2d.core.config.Config;
import de.openislandgame.view.resolutions.Resolution;

public class ResolutionSelectBox extends SelectBox<String> {
    private static final String SETTINGS_STRING = "Settings";

    public ResolutionSelectBox(Skin skin) {
        super(skin);
        setItems(Resolution.getAll());
        String resolution = Config.get(SETTINGS_STRING, "resolution");
        setSelected(resolution);

        getColor().a = 1.0f;

    }
}
