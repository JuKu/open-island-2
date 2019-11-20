package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;

public class LoadScreen implements IScreen {

    private static final String ANIMATION_PACK_PATH = "loadscreen/AnimationLoadingScreen.pack";
    private static final String BGIMAGE_PATH = "bg/field-bg2-blurred.jpg";
    private static final String LOGO_PATH = "logo/logo.png";

    private Camera camera;
    private Viewport viewport;

    // animation
    private TextureAtlas atlas;
    private Animation<TextureRegion> loadingAnimation;

    private GameAssetManager assetManager;

    // batch
    private SpriteBatch batch;

    // time
    private final float loadAnimationWidthAndHeight = 150;
    private final float animationPad = 20;
    private float elapsedTime = 0;

    // logo
    private final float logoWidth = 150;
    private final float logoHeight = 150;

    // textures
    private Texture bgImage;
    private Texture logo;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        // initAssetManager();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply(true);

        batch = new SpriteBatch();

        assetManager = GameAssetManager.getInstance();

        // get and set bg image
        assetManager.load(BGIMAGE_PATH, Texture.class);
        assetManager.load(ANIMATION_PACK_PATH, TextureAtlas.class);
        assetManager.load(LOGO_PATH, Texture.class);

        assetManager.finishLoading(BGIMAGE_PATH);
        assetManager.finishLoading(ANIMATION_PACK_PATH);
        assetManager.finishLoading(LOGO_PATH);

        bgImage = assetManager.get(BGIMAGE_PATH, Texture.class);
        atlas = assetManager.get(ANIMATION_PACK_PATH, TextureAtlas.class);
        logo = assetManager.get(LOGO_PATH, Texture.class);

        loadingAnimation = new Animation<>(1 / 30f, atlas.getRegions());
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        batch.dispose();
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        viewport.update(newWidth, newHeight, true);
    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float delta) {
        //

    }

    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //
        camera.update();

        float viewportWidth = viewport.getScreenWidth();
        float viewportHeight = viewport.getScreenHeight();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // batch.draw(bgImage, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        batch.draw(logo,
                viewportWidth / 2f - logoWidth / 2f, viewportHeight / 2f - logoHeight / 2f,
                logoWidth, logoHeight);

        elapsedTime += delta;
        TextureRegion frame = loadingAnimation.getKeyFrame(elapsedTime, true);
        batch.draw(
                frame,
                viewportWidth - loadAnimationWidthAndHeight - animationPad,
                animationPad, loadAnimationWidthAndHeight, loadAnimationWidthAndHeight);

        batch.end();
    }

}
