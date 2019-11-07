package de.openislandgame.view.loading;

import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.basegame.loading.LoadingTask;
import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.task.TaskPriority;
import com.jukusoft.engine2d.core.utils.FilePath;
import com.jukusoft.engine2d.core.utils.Utils;

import java.io.File;

//load mods first
@TaskPriority(1)
public class ModLoaderTask implements LoadingTask {

    private static final String LOG_TAG = "ModLoader";

    @Override
    public void load() throws Exception {
        Utils.printSection("Mod Loader");

        //load mods

        String modDirs = Config.get("Mods", "modDirs");
        String[] dirArray = modDirs.split(",");

        for (String dir : dirArray) {
            File modDir = new File(FilePath.parse(dir));
            Log.i(LOG_TAG, "load mods from directory: " + modDir.getAbsolutePath());
            ModManager.getInstance().loadFromDir(modDir);
        }
    }

}
