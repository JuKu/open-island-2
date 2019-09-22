package de.openislandgame.view.subsystem;

import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import com.jukusoft.engine2d.view.subsystem.ScreenSubSystem;

public class ScreenSubSystemFactory {

    private ScreenSubSystemFactory() {
        //
    }

    public static ScreenSubSystem create() {
        ScreenSubSystem screenSubSystem = new ScreenSubSystem();
        ScreenManager<IScreen> screenManager = screenSubSystem.getScreenManager();

        //TODO: add screens

        return screenSubSystem;
    }

}
