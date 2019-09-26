package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;


public class MainMenuScreen implements IScreen {
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
        // start init stuff
        atlas = new TextureAtlas("./data/test/ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("./data/test/ui/uiskin.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        // end init stuff

        // do scene2d stuff
        Gdx.input.setInputProcessor(stage);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.center();

        Table menuTable = new Table();
        rootTable.add(menuTable);

        // continue button setup
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ClickListener(){

        });
        menuTable.add(continueButton).size(buttonWidth, buttonHeight);
        menuTable.row();

        // new game button setup
        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bgColor = Color.BLACK;
            }
        });
        menuTable.add(newGameButton).size(buttonWidth, buttonHeight);
        menuTable.row();

        // load game button setup
        TextButton loadGameButton = new TextButton("Load Game", skin);
        loadGameButton.addListener(new ClickListener(){

        });
        menuTable.add(loadGameButton).size(buttonWidth, buttonHeight);
        menuTable.row();

        // campaign button setup
        TextButton campaignButton = new TextButton("Campaign", skin);
        campaignButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bgColor = Color.RED;
            }
        });
        menuTable.add(campaignButton).size(buttonWidth, buttonHeight);
        menuTable.row();

        // settings button setup
        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bgColor = Color.RED;
            }
        });
        menuTable.add(settingsButton).size(buttonWidth, buttonHeight);
        menuTable.row();

        // credits button setup
        TextButton creditsButton = new TextButton("Credits", skin);
        creditsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bgColor = Color.RED;
            }
        });
        menuTable.add(creditsButton).size(buttonWidth, buttonHeight);
        menuTable.row();

        // exit button setup
        TextButton exitButton = new TextButton("Exit Game", skin);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bgColor = Color.RED;
            }
        });
        menuTable.add(exitButton).size(buttonWidth, buttonHeight);

        stage.addActor(rootTable);
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
