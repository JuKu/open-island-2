package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.basegame.replay.ReplayMode;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import de.openislandgame.view.buttons.MenuButton;

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

    // asset manager for use in this screen
    private AssetManager assetManager;

    // menu padding
    private final int menuPad = 50;

    // background texture
    private Texture bgImage;

    // music
    private Music music;

    // clicking sound for button hover
    private Sound hoverSound;

    // click sound path
    private static final String BUTTON_ATLAS_PATH = "ui/uiskin.atlas";
    private static final String BUTTON_SKIN_PATH = "ui/uiskin.json";
    private static final String SELECT_SOUND_PATH = "sound/menu_selection_click/menu_selection_click_16bit.wav";
    private static final String BGIMAGE_PATH = "bg/flat-field-bg2.jpg";
    private static final String MUSIC_PATH = "music/SnowyForest.mp3";

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        Log.i("MainMenu", "onResume()");

        GameAssetManager assetManager = GameAssetManager.getInstance();

        // init batch, camera, viewport and background image
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
        stage = new Stage(viewport, batch);

        // load assets
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.load(BGIMAGE_PATH, Texture.class);
        assetManager.load(SELECT_SOUND_PATH, Sound.class);
        assetManager.load(BUTTON_ATLAS_PATH, TextureAtlas.class);
        assetManager.load(BUTTON_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(BUTTON_ATLAS_PATH));

        // wait
        assetManager.finishLoading(MUSIC_PATH);
        assetManager.finishLoading(BGIMAGE_PATH);
        assetManager.finishLoading(SELECT_SOUND_PATH);
        assetManager.finishLoading(BUTTON_ATLAS_PATH);
        assetManager.finishLoading(BUTTON_SKIN_PATH);

        // get music
        music = assetManager.get(MUSIC_PATH, Music.class);
        music.play();

        // get and set sound
        hoverSound = assetManager.get(SELECT_SOUND_PATH);

        // get and set bg image
        bgImage = assetManager.get(BGIMAGE_PATH);

        // get and set atlas and skin
        atlas = assetManager.get(BUTTON_ATLAS_PATH);
        skin = assetManager.get(BUTTON_SKIN_PATH);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.left().pad(menuPad);

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
        if (ReplayMode.isEnabled()){
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
    public void onPause(ScreenManager<IScreen> screenManager) {
        Log.i("MainMenu", "onPause()");

        // stop and dispose music and stage
        music.stop();
        music.dispose();

        // dispose hover sound
        hoverSound.dispose();

        // dispose background
        bgImage.dispose();

        // dispose button skins
        skin.dispose();
        atlas.dispose();

        // remove stage from input manager and dispose
        InputManager.getInstance().remove(stage);
        stage.dispose();

        // dispose batch
        batch.dispose();

        //TODO: unlad assets
        assetManager.unload(MUSIC_PATH);
        assetManager.unload(BGIMAGE_PATH);
        assetManager.unload(SELECT_SOUND_PATH);
        assetManager.unload(BUTTON_ATLAS_PATH);
        assetManager.unload(BUTTON_SKIN_PATH);
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
        batch.draw(bgImage, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }
    
}
