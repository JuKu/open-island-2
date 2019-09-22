package de.openislandgame.view.subsystem;

import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import com.jukusoft.engine2d.view.subsystem.ScreenSubSystem;
import de.openislandgame.view.screen.LoadScreen;
import de.openislandgame.view.screen.Screens;

import java.util.Objects;

public class ScreenSubSystemFactory {

    private ScreenSubSystemFactory() {
        //
    }

    public static ScreenSubSystem create() {
        ScreenSubSystem screenSubSystem = new ScreenSubSystem();
        ScreenManager<IScreen> screenManager = screenSubSystem.getScreenManager();
        Objects.requireNonNull(screenManager, "screen manager cannot be null");

        screenManager.addScreen(Screens.LOADING_SCREEN, new LoadScreen());
        //TODO: add screens

        //set activiated screen
        screenManager.leaveAllAndEnter(Screens.LOADING_SCREEN);

        return screenSubSystem;
    }

}
