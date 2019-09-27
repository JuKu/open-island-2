package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.basegame.replay.ReplayMode;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import de.openislandgame.view.buttons.MenuButton;

import java.awt.*;


public class MainMenuScreen implements IScreen {
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;
    // button size and padding
    private final int buttonWidth = 250;
    private final int buttonHeight = 50;
    private final int buttonPad = 10;

    // menu padding
    private final int menuRightPad = 50;
    private Texture bgImage;

    // click sound path
    private static final String BUTTON_ATLAS_PATH = "./data/test/ui/uiskin.atlas";
    private static final String BUTTON_SKIN_PATH = "./data/test/ui/uiskin.json";
    private static final String SELECT_SOUND_PATH = "./data/test/sound/menu_selection_click/menu_selection_click_16bit.wav";
    private static final String BGIMAGE_PATH = "./data/test/bg/shipwallpaper.jpg";
    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        // start init stuff
        atlas = new TextureAtlas(BUTTON_ATLAS_PATH);
        skin = new Skin(Gdx.files.internal(BUTTON_SKIN_PATH), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        bgImage = new Texture(BGIMAGE_PATH);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        //stage = new Stage(new ScreenViewport());
        // end init stuff
        Sound hoverSound = Gdx.audio.newSound(Gdx.files.internal(SELECT_SOUND_PATH));
        ClickListener hoverListener = new ClickListener(){
            boolean playing = false;

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing){
                    hoverSound.play(1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        };

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.right().padRight(menuRightPad);

        Table menuTable = new Table();

        // continue button setup
        MenuButton continueButton = new MenuButton("Continue", skin, hoverSound);
        menuTable.add(continueButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // new game button setup
        MenuButton newGameButton = new MenuButton("New Game", skin, hoverSound);
        menuTable.add(newGameButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // load game button setup
        MenuButton loadGameButton = new MenuButton("Load Game", skin, hoverSound);
        menuTable.add(loadGameButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // campaign button setup
        MenuButton campaignButton = new MenuButton("Campaign", skin, hoverSound);
        menuTable.add(campaignButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // settings button setup
        MenuButton settingsButton = new MenuButton("Settings", skin, hoverSound);
        menuTable.add(settingsButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // credits button setup
        MenuButton creditsButton = new MenuButton("Credits", skin, hoverSound);
        creditsButton.setOnClickNewScreen(screenManager, Screens.CREDITS_SCREEN);
        menuTable.add(creditsButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // replay button
        if (Config.getBool("Replay", "enabled")){
            MenuButton replayButton = new MenuButton("Replay", skin, hoverSound);
            menuTable.add(replayButton).size(buttonWidth, buttonHeight).pad(buttonPad);
            menuTable.row();
        }

        // exit button setup
        MenuButton exitButton = new MenuButton("Exit Game", skin, hoverSound);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });
        menuTable.add(exitButton).size(buttonWidth, buttonHeight).pad(buttonPad);

        rootTable.add(menuTable);
        stage.addActor(rootTable);

        InputManager.getInstance().addFirst(stage);
    }

    @Override
    public void onStop() {
        stage.dispose();
    }

    @Override
    public void onResume() {
        InputManager inputManager = InputManager.getInstance();

        if (inputManager.contains(stage)){
            inputManager.remove(stage);
        }
        inputManager.addFirst(stage);
    }

    @Override
    public void onPause() {
        InputManager.getInstance().remove(stage);
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        stage.getViewport().update(newWidth, newHeight, true);
    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float delta) {
        //
    }

    @Override
    public void draw(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }
}
