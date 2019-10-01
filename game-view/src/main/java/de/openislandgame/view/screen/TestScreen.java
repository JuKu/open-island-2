package de.openislandgame.view.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;

public class TestScreen implements IScreen {
    private Stage stage;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {
        stage.dispose();
    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        InputManager.getInstance().addFirst(stage);
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        InputManager.getInstance().remove(stage);
    }

    @Override
    public void onResize(int i, int i1, int i2, int i3) {

    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float v) {

    }

    @Override
    public void draw(float v) {
        stage.act();
        stage.draw();
    }
}
