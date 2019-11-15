package de.openislandgame.view.loading;

import com.jukusoft.engine2d.basegame.loading.LoadingTask;
import com.jukusoft.engine2d.core.task.TaskPriority;

@TaskPriority(98)
public class PluginLoaderTask implements LoadingTask {

    @Override
    public void load() throws Exception {
        //DefaultPluginManager
    }

}
