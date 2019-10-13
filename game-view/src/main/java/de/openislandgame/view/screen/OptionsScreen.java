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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.basegame.replay.ReplayMode;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import de.openislandgame.view.buttons.MenuButton;

public class OptionsScreen implements IScreen {
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;

    // asset manager
    private AssetManager assetManager;
    private Texture bgImage;
    private Music music;

    // file paths
    private static final String UI_ATLAS_PATH = "./data/test/ui/uiskin.atlas";
    private static final String UI_SKIN_PATH = "./data/test/ui/uiskin.json";
    private static final String BGIMAGE_PATH = "./data/test/bg/flat-field-bg2.jpg";
    private static final String MUSIC_PATH = "./data/test/music/SnowyForest.mp3";

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        assetManager = new AssetManager();

        // init batch, camera, viewport and background image
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        bgImage = new Texture(BGIMAGE_PATH);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
        stage = new Stage(viewport, batch);

        // init button skin
        atlas = new TextureAtlas(UI_ATLAS_PATH);
        skin = new Skin(Gdx.files.internal(UI_SKIN_PATH), atlas);


        // load assets
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.load(BGIMAGE_PATH, Texture.class);
        assetManager.load(UI_ATLAS_PATH, TextureAtlas.class);
        assetManager.load(UI_SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(UI_ATLAS_PATH));

        // wait
        assetManager.finishLoadingAsset(MUSIC_PATH);
        assetManager.finishLoadingAsset(BGIMAGE_PATH);
        assetManager.finishLoadingAsset(UI_ATLAS_PATH);
        assetManager.finishLoadingAsset(UI_SKIN_PATH);

        // get music
        music = assetManager.get(MUSIC_PATH, Music.class);
        music.play();

        // get and set bg image
        bgImage = assetManager.get(BGIMAGE_PATH);

        // get and set atlas and skin
        atlas = assetManager.get(UI_ATLAS_PATH);
        skin = assetManager.get(UI_SKIN_PATH);

        stage.setDebugAll(true);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.left().top();

        Table menuTable = new Table();
        menuTable.left().top().pad(50);

        Label volumeTitleLabel = new Label("Volume Settings", skin);
        volumeTitleLabel.setFontScale(1.3f);

        menuTable.add(volumeTitleLabel).colspan(2).left().expandX();
        menuTable.row().height(50);

        Label musicVolumeLabel = new Label("Music Volume: ", skin);
        Slider musicVolumeSlider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
        TextField textField = new TextField(Integer.toString((int) musicVolumeSlider.getValue()*100), skin);
        textField.setSize(10, 20);
        textField.setAlignment(Align.center);
        textField.setDisabled(true);
        textField.setMaxLength(3);
        textField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) { String currentText = textField.getText();
                float sliderValue;
                if (currentText.isEmpty()){
                    sliderValue = 0f;
                }
                else{
                    sliderValue = Float.parseFloat(currentText)/100f;
                }

                musicVolumeSlider.setValue(sliderValue);
            }
        });
        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                int textFieldValue = (int) (musicVolumeSlider.getValue()*100);
                String text = Integer.toString(textFieldValue);

                textField.setText(text);
            }
        });

        menuTable.add(musicVolumeLabel).expandX().pad(10);
        menuTable.add(musicVolumeSlider).pad(10);
        menuTable.add(textField).size(100, 30).pad(10);

        menuTable.row();


        rootTable.add(menuTable);
        stage.addActor(rootTable);

        InputManager.getInstance().addFirst(stage);
    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
// stop and dispose music and stage
        music.stop();
        music.dispose();

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

        // dispose asset manager
        assetManager.dispose();
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
//        batch.begin();
//        batch.draw(bgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.end();

        stage.act(delta);
        stage.draw();
    }
}
