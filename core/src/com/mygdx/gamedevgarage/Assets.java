package com.mygdx.gamedevgarage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;


public class Assets {

    private final AssetManager assetManager;
    private Skin skin;

    public TextureAtlas designColorsAtlas;
    public TextureAtlas designObjectsAtlas;
    public TextureAtlas platformAtlas;
    public TextureRegionDrawable transparent;
    public TextureRegionDrawable frame;
    public Model model;


    public Assets() {
        assetManager = new AssetManager();
        loadAllAssets();
    }

    private void loadAllAssets() {
        AssetManager assetManager = new AssetManager();

        assetManager.load("design_items/colors/primary_color-atlas.atlas", TextureAtlas.class);
        assetManager.load("design_items/objects/object-atlas.atlas", TextureAtlas.class);
        assetManager.load("design_items/platforms/platform-atlas.atlas", TextureAtlas.class);
        assetManager.load("models/mobile_game_background.g3db", Model.class);


        while (!assetManager.update()) {
            // Render loading screen or wait for some time
        }

        designColorsAtlas = assetManager.get("design_items/colors/primary_color-atlas.atlas", TextureAtlas.class);
        designObjectsAtlas = assetManager.get("design_items/objects/object-atlas.atlas", TextureAtlas.class);
        platformAtlas = assetManager.get("design_items/platforms/platform-atlas.atlas", TextureAtlas.class);
        model = assetManager.get("models/mobile_game_background.g3db", Model.class);

        transparent = new TextureRegionDrawable(new Texture("transparent.png"));
        frame = new TextureRegionDrawable(new Texture("frame.png"));

        skin = new Skin(Gdx.files.internal("skin/skin.json")) {
            //Override json loader to process FreeType fonts from skin JSON

            @Override
            protected Json getJsonLoader(final FileHandle skinFile) {
                Json json = super.getJsonLoader(skinFile);
                final Skin skin = this;

                json.setSerializer(FreeTypeFontGenerator.class, new Json.ReadOnlySerializer<FreeTypeFontGenerator>() {
                    @Override
                    public FreeTypeFontGenerator read(Json json,
                                                      JsonValue jsonData, Class type) {
                        String path = json.readValue("font", String.class, jsonData);
                        jsonData.remove("font");

                        Hinting hinting = Hinting.valueOf(json.readValue("hinting",
                                String.class, "AutoMedium", jsonData));
                        jsonData.remove("hinting");

                        TextureFilter minFilter = TextureFilter.valueOf(
                                json.readValue("minFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("minFilter");

                        TextureFilter magFilter = TextureFilter.valueOf(
                                json.readValue("magFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("magFilter");

                        FreeTypeFontParameter parameter = json.readValue(FreeTypeFontParameter.class, jsonData);
                        parameter.hinting = hinting;
                        parameter.minFilter = minFilter;
                        parameter.magFilter = magFilter;
                        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(skinFile.parent().child(path));
                        BitmapFont font = generator.generateFont(parameter);
                        skin.add(jsonData.name, font);
                        if (parameter.incremental) {
                            generator.dispose();
                            return null;
                        } else {
                            return generator;
                        }
                    }
                });

                return json;
            }
        };
    }

    // 0123456789!@#$%^&*()_+-=?/.><,`~'\";:|\\abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ


//    public static void main(String[] args) {
//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 8192;
//        settings.maxHeight = 8192;
//        settings.filterMin = Texture.TextureFilter.Linear;
//        settings.filterMag = Texture.TextureFilter.Linear;
//
//        TexturePacker packer = new TexturePacker(settings);
//        try {
//
////            String[] fileNames = new String[]{
////                    "light_blue", "dark_blue", "blue", "red", "light_red", "dark_red", "yellow", "dark_yellow", "sandy", "orange",
////                    "light_purple", "dark_purple", "purple", "light_pink", "pink", "light_brown", "dark_brown", "brown",
////                    "black", "white", "light_grey", "dark_grey", "grey", "dark_green", "light_green", "green"
////            };
////296
//            for (int i = 225; i < 296; i++) {
//                String nulls = "0";
//                for(int j = 0; j < 3 - String.valueOf(i).length(); j++){
//                    nulls += "0";
//                }
//                String fileName = nulls + i;
//                String filePath = "C:\\Users\\Артём\\Desktop\\гамеэ\\blends\\anim\\" + fileName + ".png";
//
//                File file = new File(filePath);
//                if(!file.exists()){
//                    System.out.println(fileName);
//                    break;
//                }
//                BufferedImage img = ImageIO.read(file);
//
//                packer.addImage(img, fileName);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        File outputdir = new File("C:\\ProjectsJava\\GameDevGarage\\assets\\animation\\");
//
//        packer.pack(outputdir, "animation-atlas.atlas");
//    }

    public void dispose() {
        skin.dispose();
        assetManager.dispose();
    }

    public Skin getSkin() {
        return skin;
    }
}
