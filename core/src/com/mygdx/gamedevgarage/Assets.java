package com.mygdx.gamedevgarage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.gamedevgarage.utils.DataManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;


public class Assets {

    private final AssetManager assetManager;
    private final String characters = "0123456789!@#$%^&*()_+-=?/.><,`~'\";:|\\abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private Skin skin;

    public TextureAtlas designColorsAtlas;
    public TextureAtlas designObjectsAtlas;
    public TextureAtlas techAtlas;
    public TextureAtlas mechAtlas;
    public TextureAtlas platformAtlas;
    public TextureAtlas randomEventsAtlas;
    public TextureRegionDrawable transparent;
    public TextureRegionDrawable frame;
    public Model model;
    public I18NBundle myBundle;
    private String language;

    private static Assets instance;

    private Assets() {
        assetManager = new AssetManager();
        loadAllAssets();
    }

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    private void loadAllAssets() {
        AssetManager assetManager = new AssetManager();

        assetManager.load("design_items/colors/primary_color-atlas.atlas", TextureAtlas.class);
        assetManager.load("design_items/objects/object-atlas.atlas", TextureAtlas.class);
        assetManager.load("design_items/platforms/platform-atlas.atlas", TextureAtlas.class);
        assetManager.load("design_items/techs/tech-atlas.atlas", TextureAtlas.class);
        assetManager.load("models/mobile_game_background.g3db", Model.class);
        assetManager.load("design_items/random_events/random-events-atlas.atlas", TextureAtlas.class);
        assetManager.load("design_items/mechs/mech-atlas.atlas", TextureAtlas.class);


        while (!assetManager.update()) {
            // Render loading screen or wait for some time
        }


        designColorsAtlas = assetManager.get("design_items/colors/primary_color-atlas.atlas", TextureAtlas.class);
        designObjectsAtlas = assetManager.get("design_items/objects/object-atlas.atlas", TextureAtlas.class);
        platformAtlas = assetManager.get("design_items/platforms/platform-atlas.atlas", TextureAtlas.class);
        techAtlas = assetManager.get("design_items/techs/tech-atlas.atlas", TextureAtlas.class);
        mechAtlas = assetManager.get("design_items/mechs/mech-atlas.atlas", TextureAtlas.class);
        randomEventsAtlas = assetManager.get("design_items/random_events/random-events-atlas.atlas", TextureAtlas.class);
        model = assetManager.get("models/mobile_game_background.g3db", Model.class);

        skin = new Skin(Gdx.files.internal("skin/skin.json")) {
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
                        parameter.size = Gdx.graphics.getHeight() / (900 / parameter.size);
                        parameter.characters = characters;
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

        transparent = (TextureRegionDrawable) skin.getDrawable("transparent");
        frame = (TextureRegionDrawable) skin.getDrawable("frame");

        language = DataManager.getInstance().getLanguage();
        setBundle();
    }

    public void setLanguage(String language) {
        this.language = language;
        setBundle();
    }

    public String getLanguage() {
        return language;
    }

    private void setBundle(){
        Locale locale = Locale.ENGLISH;

        if(language.equals("Русский")){
            locale = new Locale("ru");
        }

        myBundle = I18NBundle.createBundle(Gdx.files.internal("localization/indie"), locale);
    }

    /*
    // 0123456789!@#$%^&*()_+-=?/.><,`~'\";:|\\abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ

//    public static void main(String[] args) {
//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 4096;
//        settings.maxHeight = 4096;
//        settings.filterMin = TextureFilter.Linear;
//        settings.filterMag = TextureFilter.Linear;
//
//        TexturePacker packer = new TexturePacker(settings);
//        try {
//
////            String[] fileNames = new String[]{
////                    "group_marathon"
////            };
//            for (String fileName : DataArrayFactory.mechanicNames) {
//                String regionName = fileName.toLowerCase().replace(" ", "_").replace("/", "_");
//                String filePath1 = "C:\\Users\\Артём\\Downloads\\assets\\items\\mech_items\\" + regionName + "_1.png";
//                String filePath2 = "C:\\Users\\Артём\\Downloads\\assets\\items\\mech_items\\" + regionName + "_2.png";
//
//                File file = new File(filePath1);
//                if (!file.exists()) {
//                    System.out.println(fileName);
//                    break;
//                }
//                BufferedImage img = ImageIO.read(file);
//
//                packer.addImage(img, fileName + "_1");
//
//                file = new File(filePath2);
//                if (!file.exists()) {
//                    System.out.println(fileName);
//                    break;
//                }
//                img = ImageIO.read(file);
//
//                packer.addImage(img, fileName + "_2");
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        File outputdir = new File("C:\\ProjectsJava\\GameDevGarage\\assets\\design_items\\mechs\\");
//
//        packer.pack(outputdir, "mech-atlas.atlas");
//    }

     */

    public void loadMusic(){
        loadVolume();
        setMusic();
        setSound();
    }

    private float volume = 1;

    private void loadVolume() {
        volume = DataManager.getInstance().getVolume();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        music.setVolume(volume);
    }

    public float getVolume() {
        return volume;
    }

    private int song = new Random().nextInt(8) + 1;
    private Music music;

    private void setMusic(){
        music = Gdx.audio.newMusic(Gdx.files.internal("music/" + song + ".mp3"));
        music.setVolume(volume);
        music.play();

        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                onMusicCompletion();
            }
        });
    }

    private void onMusicCompletion(){
        int r = song;

        while(r == song){
            r = new Random().nextInt(8) + 1;
        }

        song = r;

        setMusic();
    }

    private final HashMap<String, Sound> sounds = new HashMap<>();

    private void setSound(){
        String[] soundNames = new String[]{
                "button_click", "buying_cover", "buying_mech",
                "buying_tech", "end_screen", "screen_end",
                "buying_platform", "platform_unavailable",
                "game_sold", "lvl_up", "item_checked",
                "item_unchecked"
        };

        for (String soundName : soundNames) {
            sounds.put(soundName, Gdx.audio.newSound(Gdx.files.internal("sounds/" + soundName + ".mp3")));
        }
    }

    public void setSound(String name){
        Sound sound = sounds.get(name);
        sound.play(volume);
    }

    public void dispose() {
        skin.dispose();
        music.dispose();
        assetManager.dispose();
    }

    public Skin getSkin() {
        return skin;
    }
}
