package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.view.assets.ZipAssetManagerFactory;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class LoadScreen implements IScreen {
    private Camera camera;
    private Viewport viewport;

    // animation
    private TextureAtlas atlas;
    private Animation<TextureRegion> loadingAnimation;

    // batch
    private SpriteBatch batch;

    // time
    private final float loadAnimationWidthAndHeight = 150;
    private final float animationPad = 20;
    private float elapsedTime = 0;

    //local asset manager - only for use in this screen
    private AssetManager assetManager;

    // bg texture
    private Texture bgImage;

    private static final String ANIMATION_PACK_PATH = "./data/test/loadscreen/AnimationLoadingScreen.pack";
    private static final String BGIMAGE_PATH = "./data/test/bg/field-bg2-blurred.jpg";

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        // initAssetManager();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply(true);

        batch = new SpriteBatch();

        // workaround
        assetManager = new AssetManager();

        assetManager.load(ANIMATION_PACK_PATH, TextureAtlas.class);
        assetManager.finishLoadingAsset(ANIMATION_PACK_PATH);
        atlas = assetManager.get(ANIMATION_PACK_PATH, TextureAtlas.class);

        // get and set bg image
        assetManager.load(BGIMAGE_PATH, Texture.class);
        assetManager.finishLoadingAsset(BGIMAGE_PATH);
        bgImage = assetManager.get(BGIMAGE_PATH);

        loadingAnimation = new Animation<>(1/30f, atlas.getRegions());

        //TODO: add code here
    }

    private void initAssetManager() {
        File baseGamePackPath = new File(Config.get("Gamepack", "base"));
        ZipFile zipFile;

        try {
            zipFile = new ZipFile(baseGamePackPath);
        } catch (IOException e) {
            Log.e(LoadScreen.class.getSimpleName(), "IOException while open gamepack: " + baseGamePackPath.getAbsolutePath(), e);
            ErrorHandler.shutdownWithException(e);

            return;
        }

        assetManager = ZipAssetManagerFactory.create(zipFile);
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        batch.dispose();
        atlas.dispose();

        assetManager.dispose();
        assetManager = null;
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
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgImage, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        elapsedTime += delta;

        TextureRegion frame = loadingAnimation.getKeyFrame(elapsedTime, true);
        float viewportWidth = viewport.getScreenWidth();
        batch.draw(
                frame,
                viewportWidth/2f - loadAnimationWidthAndHeight/2f,
                animationPad, loadAnimationWidthAndHeight, loadAnimationWidthAndHeight);

        batch.end();
    }

}
