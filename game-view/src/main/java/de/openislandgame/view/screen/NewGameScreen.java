package de.openislandgame.view.screen;

import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import com.jukusoft.i18n.I;
import de.openislandgame.view.backgrounds.ParallaxBackground;
import de.openislandgame.view.buttons.MenuButton;
import de.openislandgame.view.selectboxes.LanguageSelectBox;
import de.openislandgame.view.selectboxes.ResolutionSelectBox;
import de.openislandgame.view.settings.SettingsKeys;
import de.openislandgame.view.slider.SettingsSlider;
import de.openislandgame.view.textfields.SliderDisplayTextField;

public class NewGameScreen implements IScreen {
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
    private static final int CELL_PADDING = 10;
    private static final int TABLE_PADDING = 50;
    private static final int TOP_SECTION_LABEL_PADDING = 10;

    private static final int EXIT_BUTTON_WIDTH = 150;
    private static final int EXIT_BUTTON_HEIGHT = 50;
    private static final int EXIT_BUTTON_PAD = 10;

    // window pad
    private static final int WINDOW_PADDING = 50;


    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        Log.i("NewGameScreen", "onResume()");

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

        Label settingsTitleLabel = new Label(I.tr("new_game", "settings"), skin);
        settingsTitleLabel.setFontScale(1.3f);

        menuTable.add(settingsTitleLabel).colspan(3).left().expandX();
        menuTable.row().padTop(TOP_SECTION_LABEL_PADDING);

        // Graphics Menu

        // difficulty setting
        Label difficultyLabel = new Label(I.tr("new_game", "difficulty_level"), skin);
        SelectBox<String> difficultySelectBox = new SelectBox<>(skin);
        difficultySelectBox.setItems(
                I.tr("new_game", "easy"),
                I.tr("new_game", "middle"),
                I.tr("new_game", "hard")
                );

        menuTable.add(difficultyLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(difficultySelectBox).left().pad(CELL_PADDING);
        menuTable.row();

        // start conditions setting
        Label startConditionLabel = new Label(I.tr("new_game", "start_condition"), skin);
        SelectBox<String> startSelectBox = new SelectBox<>(skin);
        startSelectBox.setItems(
                I.tr("new_game", "start_on_island"),
                I.tr("new_game", "start_with_ship")
        );

        menuTable.add(startConditionLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(startSelectBox).left().pad(CELL_PADDING);
        menuTable.row();

        // map option
        Label mapLabel = new Label(I.tr("new_game", "map"), skin);
        SelectBox<String> mapSelectBox = new SelectBox<>(skin);
        // TODO: show maps dynamically from assets
        mapSelectBox.setItems(
                I.tr("new_game", "map_1"),
                I.tr("new_game", "map_2")
        );
        menuTable.add(mapLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(mapSelectBox).left().pad(CELL_PADDING);
        menuTable.row();

        // start resources option
        Label resourceLabel = new Label(I.tr("new_game", "resources"), skin);
        SelectBox<String> resourceSelectBox = new SelectBox<>(skin);
        resourceSelectBox.setItems(
                I.tr("new_game", "start_with_full_comptoir_and_ship"),
                I.tr("new_game", "start_with_full_comptoir"),
                I.tr("new_game", "start_with_few_resources")
        );
        menuTable.add(resourceLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(resourceSelectBox).left().pad(CELL_PADDING);
        menuTable.row();

        // start money option
        Label moneyLabel = new Label(I.tr("new_game", "money"), skin);
        SelectBox<String> moneySelectBox = new SelectBox<>(skin);
        moneySelectBox.setItems(
                "100000", "75000", "50000"
        );
        menuTable.add(moneyLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(moneySelectBox).left().pad(CELL_PADDING);
        menuTable.row();

        // table for exit button
        Table exitTable = new Table();
        MenuButton exitButton = new MenuButton(I.tr("new_game", "back_to_menu"), skin, hoverSound);
        exitButton.setOnClickNewScreen(screenManager, Screens.MAIN_MENU_SCREEN);
        exitTable.add(exitButton).width(EXIT_BUTTON_WIDTH).height(EXIT_BUTTON_HEIGHT).pad(EXIT_BUTTON_PAD);

        menuTable.add(exitTable).expandY().left().bottom();

        // table for start button
        Table startTable = new Table();
        MenuButton startButton = new MenuButton(I.tr("new_game", "start_game"), skin, hoverSound);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Log.i("NewGameScreen", "Clicked start game, but next screen not set!");
            }
        });
        startTable.add(startButton).width(EXIT_BUTTON_WIDTH).height(EXIT_BUTTON_HEIGHT).pad(EXIT_BUTTON_PAD);

        menuTable.add(startTable).expandY().right().bottom();

        rootTable.add(menuTable).grow().pad(WINDOW_PADDING);

        stage.addActor(rootTable);

        InputManager.getInstance().addFirst(stage);
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        Log.i("OptionsScreen", "onPause()");
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
