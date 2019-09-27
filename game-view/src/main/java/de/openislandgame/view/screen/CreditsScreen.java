package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;

public class CreditsScreen implements IScreen {
    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResize(int i, int i1, int i2, int i3) {

    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float v) {

    }

    @Override
    public void draw(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
