package de.openislandgame.view.subsystem;

import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import com.jukusoft.engine2d.view.subsystem.ScreenSubSystem;
import de.openislandgame.view.screen.*;

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
        screenManager.addScreen(Screens.MAIN_MENU_SCREEN, new MainMenuScreen());
        screenManager.addScreen(Screens.CREDITS_SCREEN, new CreditsScreen());
        screenManager.addScreen(Screens.OPTIONS_SCREEN, new OptionsScreen());
        screenManager.addScreen(Screens.INITIAL_LOADING_SCREEN, new InitialLoadScreen());
        screenManager.addScreen(Screens.LOAD_GAME_SCREEN, new LoadGameScreen());

        //set activated screen
        screenManager.leaveAllAndEnter(Screens.INITIAL_LOADING_SCREEN);

        return screenSubSystem;
    }

}
