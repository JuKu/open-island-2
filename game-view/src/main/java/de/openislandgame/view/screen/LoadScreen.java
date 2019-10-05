package de.openislandgame.view.screen;

import com.badlogic.gdx.assets.AssetManager;
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

    //local asset manager - only for use in this screen
    private AssetManager assetManager;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        File baseGamePackPath = new File(Config.get("Gamepack", "base"));
        ZipFile zipFile = null;

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
        assetManager.dispose();
        assetManager = null;
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {

    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float delta) {
        //

    }

    @Override
    public void draw(float delta) {
        //
    }

}
