package de.openislandgame.view.loading;

import com.jukusoft.engine2d.basegame.loading.LoadingTask;
import com.jukusoft.engine2d.core.task.TaskPriority;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;

@TaskPriority(99)
public class InitAssetManagerTask implements LoadingTask {

    @Override
    public void load() throws Exception {
        GameAssetManager.getInstance();
    }

}
