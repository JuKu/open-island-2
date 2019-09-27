package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.jukusoft.engine2d.core.utils.FileUtils;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CreditsScreen implements IScreen {
    // fonts for title, section and normal text
    private BitmapFont textFont;
    private BitmapFont sectionFont;
    private BitmapFont titleFont;
    private GlyphLayout layout;

    // text color
    private final Color textColor = Color.WHITE;

    private SpriteBatch batch;
    private final int startX = 50;
    private int startY;
    private final float textSpeed = 1;
    private final float lineGap = 10;

    // credit lines
    private String[] creditLines;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        batch = new SpriteBatch();
        layout = new GlyphLayout();

        startY = 0;

        textFont = new BitmapFont(Gdx.files.internal("./data/test/fonts/almfixed.fnt"));
        textFont.setColor(textColor);
        sectionFont = new BitmapFont(Gdx.files.internal("./data/test/fonts/almfixed.fnt"));
        sectionFont.setColor(textColor);
        sectionFont.getData().setScale(1.25f);
        titleFont = new BitmapFont(Gdx.files.internal("./data/test/fonts/almfixed.fnt"));
        titleFont.setColor(textColor);
        titleFont.getData().setScale(1.5f);

        try {
            generateCreditLines();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {
        startY = 0;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {

    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float v) {
        if (Gdx.input.isTouched()){
            screenManager.leaveAllAndEnter(Screens.MAIN_MENU_SCREEN);
        }
    }

    @Override
    public void draw(float delta) {

        batch.begin();

        float lastY = startY;

        for (String line : creditLines) {
            if (line.contains("##")) {
                layout.setText(sectionFont, line.replace("#", "").trim());
                sectionFont.draw(batch, layout, startX, lastY);
            } else if (line.contains("#")) {
                layout.setText(titleFont, line.replace("#", "").trim());
                titleFont.draw(batch, layout, startX, lastY);
            } else {
                layout.setText(textFont, line.trim());
                textFont.draw(batch, layout, startX, lastY);
            }

            // adjust according to height of text written before
            lastY -= layout.height + lineGap;
        }

        batch.end();

        // update start position
        startY += textSpeed;

    }

    private void generateCreditLines() throws IOException {
        List<String> lines = new ArrayList<>();

        List<String> linesFromFile = FileUtils.readLines("./Credits.md", StandardCharsets.UTF_8);

        String lastLine = "";

        for (String line: linesFromFile){
            line = line.replace("\\", "");

            if (lastLine.equals("") && line.equals("")) {
                continue;
            }

            lines.add(line);

            if (line.contains("#")){
                lines.add("");

                line = "";
            }

            lastLine = line;
        }

        creditLines = new String[lines.size()];
        creditLines = lines.toArray(creditLines);
    }
}
