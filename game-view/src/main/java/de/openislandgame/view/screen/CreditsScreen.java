package de.openislandgame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.core.logger.Log;
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
    private OrthographicCamera camera;
    private Viewport viewport;

    private final int startX = 50;
    private int startY;
    private final float textSpeed = 100;
    private final float lineGap = 10;

    // credit lines
    private String[] creditLines;

    // asset paths
    private static final String CREDITS_FONT_PATH = "./data/test/fonts/almfixed.fnt";

    // asset manager
    private AssetManager assetManager;

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        try {
            generateCreditLines();
        } catch (IOException e){
            Log.e(CreditsScreen.class.getSimpleName(), "IOException while generating credit lines: ", e);
        }
    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        batch = new SpriteBatch();

        layout = new GlyphLayout();

        startY = 0;

        assetManager = new AssetManager();

        assetManager.load(CREDITS_FONT_PATH, BitmapFont.class);
        assetManager.finishLoadingAsset(CREDITS_FONT_PATH);

        textFont = assetManager.get(CREDITS_FONT_PATH);
        textFont.setColor(textColor);
        sectionFont = assetManager.get(CREDITS_FONT_PATH);
        sectionFont.setColor(textColor);
        sectionFont.getData().setScale(1.25f);
        titleFont = assetManager.get(CREDITS_FONT_PATH);
        titleFont.setColor(textColor);
        titleFont.getData().setScale(1.5f);

    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        // dispose batch
        batch.dispose();
        // dispose fonts
        textFont.dispose();
        sectionFont.dispose();
        titleFont.dispose();

        // dispose asset manager
        assetManager.dispose();
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        viewport.update(newWidth, newHeight, true);
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
        startY += textSpeed*delta;
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
