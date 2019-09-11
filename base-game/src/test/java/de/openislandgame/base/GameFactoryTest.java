package de.openislandgame.base;

import com.jukusoft.engine2d.applayer.BaseGame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameFactoryTest {

    //this test only checks, that the default constructor is available
    @Test
    public void testConstructor () {
        new GameFactory();
    }

    @Test
    public void testCreateGame () {
        BaseGame game = new GameFactory().createGame();
        assertNotNull(game);
    }

}
