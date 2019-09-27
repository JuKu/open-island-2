package de.openislandgame.view.buttons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import de.openislandgame.view.screen.Screens;

import java.util.function.Consumer;

public class MenuButton extends TextButton {

    public MenuButton(String text, Skin skin, Sound hoverSound){
        super(text, skin);
        setHoverSound(hoverSound);
    }

    public MenuButton(String text, Skin skin) {
        super(text, skin);
    }

    public MenuButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public MenuButton(String text, TextButtonStyle style) {
        super(text, style);
    }

    public void setHoverSound(Sound hoverSound){
        ClickListener hoverListener = new ClickListener(){
            boolean playing = false;

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing){
                    hoverSound.play(1F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        };

        addListener(hoverListener);
    }

    public void setOnClickNewScreen(ScreenManager<IScreen> screenManager, String newScreen){
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                screenManager.leaveAllAndEnter(newScreen);
            }
        });
    }
}
