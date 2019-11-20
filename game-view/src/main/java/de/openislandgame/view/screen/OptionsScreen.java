package de.openislandgame.view.screen;

import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

public class OptionsScreen implements IScreen {
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
    private static final String MUSIC_PATH = "music/SnowyForest.mp3";
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

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        Log.i("OptionsScreen", "onResume()");

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

        Label volumeTitleLabel = new Label(I.tr("options", "volume_settings"), skin);
        volumeTitleLabel.setFontScale(1.3f);

        menuTable.add(volumeTitleLabel).colspan(3).left().expandX();
        menuTable.row().padTop(TOP_SECTION_LABEL_PADDING);

        // Music Volume Setting
        Label musicVolumeLabel = new Label(I.tr("options", "music_volume"), skin);
        SettingsSlider musicVolumeSlider = new SettingsSlider(SettingsKeys.MUSIC_VOLUME, skin);
        SliderDisplayTextField musicVolumeTextField = new SliderDisplayTextField(SettingsKeys.MUSIC_VOLUME, skin, musicVolumeSlider);

        menuTable.add(musicVolumeLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(musicVolumeSlider).pad(CELL_PADDING);
        menuTable.add(musicVolumeTextField).size(DISPLAY_TEXTFIELD_WIDTH, DISPLAY_TEXTFIELD_HEIGHT).pad(CELL_PADDING);
        menuTable.row();

        // Sound Volume Setting
        Label soundVolumeLabel = new Label(I.tr("options", "sound_volume"), skin);
        SettingsSlider soundVolumeSlider = new SettingsSlider(SettingsKeys.SOUND_VOLUME, skin);
        SliderDisplayTextField soundVolumeTextField = new SliderDisplayTextField(SettingsKeys.SOUND_VOLUME, skin, soundVolumeSlider);

        menuTable.add(soundVolumeLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(soundVolumeSlider).pad(CELL_PADDING);
        menuTable.add(soundVolumeTextField).size(DISPLAY_TEXTFIELD_WIDTH, DISPLAY_TEXTFIELD_HEIGHT).pad(CELL_PADDING);
        menuTable.row();

        // Speech Volume Setting
        Label speechVolumeLabel = new Label(I.tr("options", "speech_volume"), skin);
        SettingsSlider speechVolumeSlider = new SettingsSlider(SettingsKeys.SPEECH_VOLUME, skin);
        SliderDisplayTextField speechVolumeTextField = new SliderDisplayTextField(SettingsKeys.SPEECH_VOLUME, skin, speechVolumeSlider);

        menuTable.add(speechVolumeLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(speechVolumeSlider).pad(CELL_PADDING);
        menuTable.add(speechVolumeTextField).size(DISPLAY_TEXTFIELD_WIDTH, DISPLAY_TEXTFIELD_HEIGHT).pad(CELL_PADDING);
        menuTable.row();

        // Graphics Menu
        Label graphicsTitleLabel = new Label(I.tr("options", "graphics_settings"), skin);
        graphicsTitleLabel.setFontScale(1.3f);

        menuTable.add(graphicsTitleLabel).colspan(3).left().expandX();
        menuTable.row().padTop(TOP_SECTION_LABEL_PADDING);

        // resolution setting
        Label resolutionLabel = new Label(I.tr("options", "resolution"), skin);
        ResolutionSelectBox resolutionSelectBox = new ResolutionSelectBox(skin);

        menuTable.add(resolutionLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(resolutionSelectBox).pad(CELL_PADDING);
        menuTable.row();

        // full screen setting
        Label fullScreenLabel = new Label(I.tr("options", "full_screen"), skin);
        CheckBox fullScreenCheckBox = new CheckBox("", skin);
        boolean isFullScreen = Config.getBool("Settings", "fullscreen");
        fullScreenCheckBox.setChecked(isFullScreen);
        fullScreenCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                boolean isFullScreen = fullScreenCheckBox.isChecked();
                Config.set("Settings", "fullscreen", String.valueOf(isFullScreen));
            }
        });

        menuTable.add(fullScreenLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(fullScreenCheckBox).pad(CELL_PADDING);
        menuTable.row();

        // Language Menu
        Label languageTitleLabel = new Label(I.tr("options", "language_settings"), skin);
        languageTitleLabel.setFontScale(1.3f);
        menuTable.add(languageTitleLabel).colspan(3).left().expandX();
        menuTable.row().padTop(TOP_SECTION_LABEL_PADDING);

        // language option
        Label languageLabel = new Label(I.tr("options", "language"), skin);
        LanguageSelectBox languageSelectBox = new LanguageSelectBox(skin);
        menuTable.add(languageLabel).left().expandX().pad(CELL_PADDING);
        menuTable.add(languageSelectBox).pad(CELL_PADDING);
        menuTable.row();

        // table for exit button
        Table exitTable = new Table();
        MenuButton exitButton = new MenuButton(I.tr("options", "back_to_menu"), skin, hoverSound);
        exitButton.setOnClickNewScreen(screenManager, Screens.MAIN_MENU_SCREEN);
        exitTable.add(exitButton).width(EXIT_BUTTON_WIDTH).height(EXIT_BUTTON_HEIGHT).pad(EXIT_BUTTON_PAD);

        menuTable.add(exitTable).expandY().left().bottom();

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
    public void update(ScreenManager<IScreen> screenManager, float delta) {

    }

    @Override
    public void draw(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        stage.draw();
    }

}
