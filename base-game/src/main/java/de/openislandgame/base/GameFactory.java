package de.openislandgame.base;

import com.jukusoft.engine2d.applayer.BaseGame;
import com.jukusoft.engine2d.applayer.BaseGameFactory;
import com.jukusoft.engine2d.applayer.game.BasicGame;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.core.subsystem.EventProcessor;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.core.utils.Threads;
import com.jukusoft.engine2d.input.subsystem.InputSubSystem;
import com.jukusoft.engine2d.plugin.PluginApi;
import com.jukusoft.engine2d.view.subsystem.ScreenSubSystem;
import de.openislandgame.view.subsystem.ScreenSubSystemFactory;

import java.util.Objects;
import java.util.function.Consumer;

public class GameFactory implements BaseGameFactory {

    @Override
    public BaseGame createGame() {
        return new BaseGame(GameFactory.class) {
            @Override
            protected PluginApi createPluginApi() {
                return () -> null;
            }

            @Override
            protected Consumer<SubSystemManager> addSubSystems() {
                return manager -> {
                    Log.i(GameFactory.class.getSimpleName(), "create subsystems");
                    Objects.requireNonNull(manager);

                    //add input manager
                    manager.addSubSystem(new InputSubSystem(), Threads.UI_THREAD);

                    //add event processors for UI and logic events
                    manager.addSubSystem(new EventProcessor(Threads.UI_THREAD, 10), Threads.UI_THREAD);
                    manager.addSubSystem(new EventProcessor(Threads.LOGIC_THREAD, 10), Threads.LOGIC_THREAD);
                    manager.addSubSystem(new EventProcessor(Threads.NETWORK_THREAD, 10), Threads.NETWORK_THREAD);

                    //TODO: add network layer
                    //manager.addSubSystem(new NetworkView(), true);

                    //TODO: add game-logic-layer
                    //manager.addSubSystem(new GameLogicLayer(), true);

                    //add game-view-layer / human views
                    manager.addSubSystem(ScreenSubSystemFactory.create(), Threads.UI_THREAD);
                };
            }

            @Override
            protected Game createGame() {
                return null;//new BasicGame();
            }
        };
    }

}
