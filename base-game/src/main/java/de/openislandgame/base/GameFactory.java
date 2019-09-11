package de.openislandgame.base;

import com.jukusoft.engine2d.applayer.BaseGame;
import com.jukusoft.engine2d.applayer.BaseGameFactory;
import com.jukusoft.engine2d.applayer.game.BasicGame;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.plugin.PluginApi;

public class GameFactory implements BaseGameFactory {

    @Override
    public BaseGame createGame() {
        return new BaseGame(GameFactory.class) {
            @Override
            protected PluginApi createPluginApi() {
                return () -> null;
            }

            @Override
            protected void addSubSystems(SubSystemManager manager) {
                //
            }

            @Override
            protected Game createGame() {
                return new BasicGame();
            }
        };
    }

}
