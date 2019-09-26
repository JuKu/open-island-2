package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;

public class TestScreen implements IScreen {
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;
    private final int buttonWidth = 150;
    private final int buttonHeight = 50;
    private Color bgColor = Color.BLUE;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        atlas = new TextureAtlas("./data/test/ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("./data/test/ui/uiskin.json"), atlas);

        stage = new Stage(new ScreenViewport());
        // end init stuff

        // do scene2d stuff
        Gdx.input.setInputProcessor(stage);

        // new game button setup
        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.setBounds(newGameButton.getX(), newGameButton.getY(), buttonWidth, buttonHeight);
        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.BLACK;

                System.err.println("test");
            }
        });

        stage.addActor(newGameButton);

        InputManager.getInstance().addFirst(stage);
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
    public void onResize(int i, int i1) {

    }

    @Override
    public void update(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
