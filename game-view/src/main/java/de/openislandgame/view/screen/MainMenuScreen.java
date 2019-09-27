package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;


public class MainMenuScreen implements IScreen {
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;
    private final int buttonWidth = 200;
    private final int buttonHeight = 50;
    private final int buttonPad = 10;
    private final int menuRightPad = 50;
    private Color bgColor = Color.BLUE;
    private Texture bgImage;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        // start init stuff
        atlas = new TextureAtlas("./data/test/ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("./data/test/ui/uiskin.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        bgImage = new Texture("./data/test/bg/shipwallpaper.jpg");
        viewport.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        //stage = new Stage(new ScreenViewport());
        // end init stuff

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.right().padRight(menuRightPad);

        Table menuTable = new Table();

        // continue button setup
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.BLACK;
            }
        });
        menuTable.add(continueButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // new game button setup
        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.CYAN;
            }
        });
        menuTable.add(newGameButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // load game button setup
        TextButton loadGameButton = new TextButton("Load Game", skin);
        loadGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.RED;
            }
        });
        menuTable.add(loadGameButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // campaign button setup
        TextButton campaignButton = new TextButton("Campaign", skin);
        campaignButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.BLUE;
            }
        });
        menuTable.add(campaignButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // settings button setup
        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.GREEN;
            }
        });
        menuTable.add(settingsButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // credits button setup
        TextButton creditsButton = new TextButton("Credits", skin);
        creditsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.YELLOW;
            }
        });
        menuTable.add(creditsButton).size(buttonWidth, buttonHeight).pad(buttonPad);
        menuTable.row();

        // exit button setup
        TextButton exitButton = new TextButton("Exit Game", skin);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgColor = Color.BROWN;
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
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();


        stage.act(delta);
        stage.draw();
    }
}
