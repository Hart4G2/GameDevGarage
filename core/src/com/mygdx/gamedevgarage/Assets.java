package com.mygdx.gamedevgarage;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class Assets {

    public AssetManager assetManager;
    public TextureAtlas mainButtonAtlas;
    public TextureAtlas progressBarStyleAtlas;
    public BitmapFont black_16;
    public BitmapFont black_18;
    public BitmapFont black_20;
    public BitmapFont black_24;
    public BitmapFont black_32;
    public BitmapFont white_16;
    public BitmapFont white_18;
    public BitmapFont white_20;
    public BitmapFont white_24;
    public BitmapFont white_32;
    public Skin skin;
    private FreeTypeFontGenerator generator;

    public Assets() {
        assetManager = new AssetManager();
        generateFont();
        loadAllAssets();
    }

    private void loadAllAssets() {
        AssetManager assetManager = new AssetManager();

        assetManager.load("atlases/button-atlas.atlas", TextureAtlas.class);
        assetManager.load("atlases/progressbar-atlas.atlas", TextureAtlas.class);


        while (!assetManager.update()) {
            // Render loading screen or wait for some time
        }


        mainButtonAtlas = assetManager.get("atlases/button-atlas.atlas", TextureAtlas.class);
        progressBarStyleAtlas = assetManager.get("atlases/progressbar-atlas.atlas", TextureAtlas.class);

        skin = new Skin();

        skin.add("black_16", black_16, BitmapFont.class);
        skin.add("black_18", black_18, BitmapFont.class);
        skin.add("black_20", black_20, BitmapFont.class);
        skin.add("black_24", black_24, BitmapFont.class);
        skin.add("black_32", black_32, BitmapFont.class);
        skin.add("white_16", white_16, BitmapFont.class);
        skin.add("white_18", white_18, BitmapFont.class);
        skin.add("white_20", white_20, BitmapFont.class);
        skin.add("white_24", white_24, BitmapFont.class);
        skin.add("white_32", white_32, BitmapFont.class);

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("atlases/styles/selection.png")));
        skin.add("selection_drawable", drawable, Drawable.class);
        skin.add("list_background", drawable, Drawable.class);

        drawable = new TextureRegionDrawable(new TextureRegion(new Texture("atlases/styles/window.png")));
        skin.add("window_drawable", drawable, Drawable.class);

        drawable = new TextureRegionDrawable(new TextureRegion(new Texture("atlases/styles/text_field_background.png")));
        skin.add("text_field_background", drawable, Drawable.class);

        // Button style images
        drawable = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-up"));
        skin.add("button_up", drawable, Drawable.class);
        drawable = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-down"));
        skin.add("button_down", drawable, Drawable.class);
        drawable = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-hover"));
        skin.add("button_hover", drawable, Drawable.class);

        // Progress bar images
        drawable = new TextureRegionDrawable(progressBarStyleAtlas.findRegion("background"));
        skin.add("progressbar_background", drawable, Drawable.class);
        drawable = new TextureRegionDrawable(progressBarStyleAtlas.findRegion("knob-before"));
        skin.add("progressbar_knob_before", drawable, Drawable.class);
        drawable = new TextureRegionDrawable(progressBarStyleAtlas.findRegion("knob-after"));
        skin.add("progressbar_knob_after", drawable, Drawable.class);

        skin.load(Gdx.files.internal("skin.json"));
    }

    private void generateFont() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("atlases/fonts/AovelSansRounded-rdDL.TTF"));

        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.color = Color.BLACK;

        parameter.size = 16;
        black_16 = generator.generateFont(parameter);

        parameter.size = 18;
        black_18 = generator.generateFont(parameter);

        parameter.size = 20;
        black_20 = generator.generateFont(parameter);

        parameter.size = 24;
        black_24 = generator.generateFont(parameter);

        parameter.size = 32;
        black_32 = generator.generateFont(parameter);

        parameter.color = Color.WHITE;

        parameter.size = 16;
        white_16 = generator.generateFont(parameter);

        parameter.size = 18;
        white_18 = generator.generateFont(parameter);

        parameter.size = 20;
        white_20 = generator.generateFont(parameter);

        parameter.size = 24;
        white_24 = generator.generateFont(parameter);

        parameter.size = 32;
        white_32 = generator.generateFont(parameter);
    }

//    public static void main(String[] args) {
//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 2048;
//        settings.maxHeight = 2048;
//        settings.filterMin = Texture.TextureFilter.Linear;
//        settings.filterMag = Texture.TextureFilter.Linear;
//
//        TexturePacker packer = new TexturePacker(settings);
//        try {
//            File file = new File("C:\\ProjectsJava\\GameDevGarage\\assets\\atlases\\button\\button-down.png");
//            BufferedImage downImg = ImageIO.read(file);
//
//            file = new File("C:\\ProjectsJava\\GameDevGarage\\assets\\atlases\\button\\button-up.png");
//            BufferedImage upImg = ImageIO.read(file);
//
//            file = new File("C:\\ProjectsJava\\GameDevGarage\\assets\\atlases\\button\\button-hover.png");
//            BufferedImage hoverImg = ImageIO.read(file);
//
//            packer.addImage(downImg, "button-down");
//            packer.addImage(upImg, "button-up");
//            packer.addImage(hoverImg, "button-hover");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        File outputdir = new File("C:\\ProjectsJava\\GameDevGarage\\assets\\atlases");
//
//        packer.pack(outputdir, "button-atlas.atlas");
//    }

    public void dispose() {
        skin.dispose();
        black_16.dispose();
        black_18.dispose();
        black_20.dispose();
        black_24.dispose();
        white_16.dispose();
        white_18.dispose();
        white_20.dispose();
        white_24.dispose();
        mainButtonAtlas.dispose();
        progressBarStyleAtlas.dispose();
        assetManager.dispose();
        generator.dispose();
    }
}
