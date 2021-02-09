package de.openislandgame.view.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import com.jukusoft.i18n.I;
import de.openislandgame.view.buttons.MenuButton;
import de.openislandgame.view.selectboxes.LanguageSelectBox;
import de.openislandgame.view.selectboxes.ResolutionSelectBox;
import de.openislandgame.view.settings.SettingsKeys;
import de.openislandgame.view.slider.SettingsSlider;
import de.openislandgame.view.textfields.SliderDisplayTextField;

public class LoadGameScreen implements IScreen {
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;

    // asset manager
    private GameAssetManager assetManager;
    private Texture bgImage;
    private Music music;
    private Sound hoverSound;


    // file paths
    private static final String UI_ATLAS_PATH = "ui/uiskin.atlas";
    private static final String UI_SKIN_PATH = "ui/uiskin.json";
    private static final String BGIMAGE_PATH = "bg/flat-field-bg2.jpg";
    private static final String MUSIC_PATH = "music/options/SnowyForest.mp3";
    private static final String SELECT_SOUND_PATH = "sound/menu_selection_click/menu_selection_click_16bit.wav";

    // display text field props
    private static final int DISPLAY_TEXTFIELD_WIDTH = 100;
    private static final int DISPLAY_TEXTFIELD_HEIGHT = 30;
    private static final int CELL_PADDING = 10;
    private static final int TABLE_PADDING = 50;
    private static final int TOP_SECTION_LABEL_PADDING = 10;

    private static final int EXIT_BUTTON_WIDTH = 150;
    private static final int EXIT_BUTTON_HEIGHT = 50;
    private static final int EXIT_BUTTON_PAD = 10;

    // window pad
    private static final int WINDOW_PADDING = 50;

    private String selectedSaveGame;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        Log.i("LoadGameScreen", "onResume()");

        this.assetManager = GameAssetManager.getInstance();

        // init batch, camera, viewport and background image
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport, batch);


        // load assets
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.load(BGIMAGE_PATH, Texture.class);
        assetManager.load(UI_ATLAS_PATH, TextureAtlas.class);
        assetManager.load(UI_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(UI_ATLAS_PATH));
        assetManager.load(SELECT_SOUND_PATH, Sound.class);

        // wait
        assetManager.finishLoading(MUSIC_PATH);
        assetManager.finishLoading(BGIMAGE_PATH);
        assetManager.finishLoading(UI_ATLAS_PATH);
        assetManager.finishLoading(UI_SKIN_PATH);
        assetManager.finishLoading(SELECT_SOUND_PATH);

        // get music
        music = assetManager.get(MUSIC_PATH, Music.class);
        music.play();

        // get and set bg image
        bgImage = assetManager.get(BGIMAGE_PATH);

        // get and set atlas and skin
        atlas = assetManager.get(UI_ATLAS_PATH);
        skin = assetManager.get(UI_SKIN_PATH);

        // get hover sound
        hoverSound = assetManager.get(SELECT_SOUND_PATH);

        Table rootTable = new Table();
        rootTable.setBackground(new TextureRegionDrawable(bgImage));
        rootTable.setFillParent(true);
        rootTable.left().top();

        Table menuTable = new Table();
        // add background color
        menuTable.setBackground(skin.getDrawable("default-window"));
        menuTable.left().top().pad(TABLE_PADDING);

        Label titleLabel = new Label(I.tr("load_game", "saved_games"), skin);
        titleLabel.setFontScale(1.3f);

        menuTable.add(titleLabel).left().expandX();
        menuTable.row().padTop(TOP_SECTION_LABEL_PADDING);

        // TODO: Load game names from saved games
        List<String> savedGameNames = new List<String>(skin);
        int k = 100;
        String[] gameNames = new String[k];
        for (int i=0; i<k; i++){
            gameNames[i] = "Game " + i;
        }
        savedGameNames.setItems(gameNames);
        selectedSaveGame = savedGameNames.getSelected();
        ScrollPane gameScrollPane = new ScrollPane(savedGameNames, skin);
        gameScrollPane.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedSaveGame = savedGameNames.getSelected();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        menuTable.add(gameScrollPane).left().top().expandX();


        // table for exit button
        Table exitTable = new Table();
        MenuButton exitButton = new MenuButton(I.tr("load_game", "back_to_menu"), skin, hoverSound);
        exitButton.setOnClickNewScreen(screenManager, Screens.MAIN_MENU_SCREEN);
        exitTable.add(exitButton).width(EXIT_BUTTON_WIDTH).height(EXIT_BUTTON_HEIGHT).pad(EXIT_BUTTON_PAD);
        menuTable.add(exitTable).expandY().left().bottom();

        Table loadGameTable = new Table();
        MenuButton loadButton = new MenuButton(I.tr("load_game", "load_game"), skin, hoverSound);
        // TODO: Add next screen here
        loadGameTable.add(loadButton).width(EXIT_BUTTON_WIDTH).height(EXIT_BUTTON_HEIGHT).pad(EXIT_BUTTON_PAD);
        menuTable.add(loadGameTable).expandY().right().bottom();


        rootTable.add(menuTable).grow().pad(WINDOW_PADDING);

        stage.addActor(rootTable);

        InputManager.getInstance().addFirst(stage);
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        Log.i("LoadGameScreen", "onPause()");
        // stop and dispose music and stage
        music.stop();

        // remove stage from input manager and dispose
        InputManager.getInstance().remove(stage);
        stage.dispose();

        // dispose batch
        batch.dispose();

        assetManager.unload(MUSIC_PATH);
        assetManager.unload(BGIMAGE_PATH);
        assetManager.unload(UI_ATLAS_PATH);
        assetManager.unload(UI_SKIN_PATH);
        assetManager.unload(SELECT_SOUND_PATH);
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        stage.getViewport().update(newWidth, newHeight, true);
    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float v) {

    }

    @Override
    public void draw(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        stage.draw();
    }
}
