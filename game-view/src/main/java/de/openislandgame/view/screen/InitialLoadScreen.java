package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import com.jukusoft.engine2d.basegame.loading.LoadingProcessor;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.view.assets.ZipAssetManagerFactory;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import de.openislandgame.view.loading.LoadingProcessorFactory;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class InitialLoadScreen implements IScreen {

    private static final String ZIP_PATH = "data/maindata/base.zip";
    private static final String LOG_TAG = "InitialLoadScreen";

    private static final String ANIMATION_PACK_PATH = "loadscreen/AnimationLoadingScreen.pack";
    private static final String BGIMAGE_PATH = "bg/waterfall_background.jpg";
    private static final String LOGO_PATH = "logo/logo.png";

    private float elapsedTime;
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

    // logo
    private final float logoWidth = 150;
    private final float logoHeight = 150;

    //local asset manager - only for use in this screen
    private AssetManager assetManager;

    // textures
    private Texture bgImage;
    private Texture logo;

    private LoadingProcessor loadingProcessor;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        loadingProcessor = LoadingProcessorFactory.create();
    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {
        loadingProcessor = null;
    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply(true);

        batch = new SpriteBatch();

        try (ZipFile zipFile = new ZipFile(new File(ZIP_PATH))) {
            //create asset manager for loading assets from only one zip file
            assetManager = ZipAssetManagerFactory.create(zipFile);

            assetManager.load(ANIMATION_PACK_PATH, TextureAtlas.class);
            assetManager.finishLoadingAsset(ANIMATION_PACK_PATH);
            atlas = assetManager.get(ANIMATION_PACK_PATH, TextureAtlas.class);

            // get and set bg image
            assetManager.load(BGIMAGE_PATH, Texture.class);
            assetManager.finishLoadingAsset(BGIMAGE_PATH);
            bgImage = assetManager.get(BGIMAGE_PATH);

            assetManager.load(LOGO_PATH, Texture.class);
            assetManager.finishLoadingAsset(LOGO_PATH);
            logo = assetManager.get(LOGO_PATH);
        } catch (ZipException e) {
            Log.e(LoadScreen.class.getSimpleName(), "Cannot open zip file: " + new File(ZIP_PATH).getAbsolutePath(), e);
            ErrorHandler.shutdownWithException(e);
        } catch (IOException e) {
            Log.e(LoadScreen.class.getSimpleName(), "IOException while opening zip file: " + new File(ZIP_PATH).getAbsolutePath(), e);
            ErrorHandler.shutdownWithException(e);
        }

        loadingAnimation = new Animation<>(1 / 30f, atlas.getRegions());

        //TODO: add code here
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        batch.dispose();
        atlas.dispose();

        bgImage.dispose();
        logo.dispose();

        assetManager.dispose();
        assetManager = null;
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        viewport.update(newWidth, newHeight, true);
    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float delta) {
        if (!loadingProcessor.hasFinished()) {
            try {
                loadingProcessor.process();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception while loading process: ", e);
                ErrorHandler.shutdownWithException(e);
            }
        }

        if (elapsedTime > 5 && loadingProcessor.hasFinished()) {
            screenManager.leaveAllAndEnter(Screens.MAIN_MENU_SCREEN);
        }
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
        batch.draw(bgImage, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
//        batch.draw(logo,
//                viewportWidth/2f - logoWidth/2f, viewportHeight/2f - logoHeight/2f,
//                logoWidth, logoHeight);
//

        elapsedTime += delta;
        TextureRegion frame = loadingAnimation.getKeyFrame(elapsedTime, true);
        batch.draw(
                frame,
                viewportWidth - loadAnimationWidthAndHeight - animationPad,
                animationPad, loadAnimationWidthAndHeight, loadAnimationWidthAndHeight);

        batch.end();
    }
}
