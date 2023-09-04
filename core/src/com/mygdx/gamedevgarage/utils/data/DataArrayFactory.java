package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckListItem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

public class DataArrayFactory {

    public static Array<CoverListItem> createCoverObjects(Game game){
        HashSet<String> objectSet = game.getCoverObjects();
        Array<CoverListItem> objects = initObjects(game);

        for (CoverListItem item : objects) {
            item.setPurchased(objectSet.contains(item.getText()));
        }

        return objects;
    }

    public static Array<CheckListItem> createTechnologies(Game game){
        Array<CheckListItem> technologies = initTechnologies(game);
        HashSet<String> technologySet = game.getTechnologies();

        for (CheckListItem item : technologies) {
            item.setPurchased(technologySet.contains(item.getText()));
        }

        return technologies;
    }

    public static Array<CheckListItem> createMechanics(Game game){
        Array<CheckListItem> mechanics = initMechanics(game);
        HashSet<String> mechanicsSet = game.getMechanics();

        for (CheckListItem mechanic : mechanics) {
            mechanic.setPurchased(mechanicsSet.contains(mechanic.getText()));
        }

        return mechanics;
    }

    public static Array<CoverListItem> createPlatform(Game game){
        Array<CoverListItem> platforms = initPlatforms(game);
        HashSet<String> platformSet = game.getPlatforms();

        for (CoverListItem platform : platforms) {
            platform.setPurchased(platformSet.contains(platform.getText()));
        }

        return platforms;
    }

    public static HashSet<String> createDataObjectsSet(Game game){
        HashSet<String> objects = new HashSet<>();
        String[] objectNames = game.reward.objectNames;

        for (int i = 0; i < 5; i++) {
            objects.add(objectNames[i] + "_1");
            objects.add(objectNames[i] + "_2");
        }

        return objects;
    }

    public static HashSet<String> createDataTechnologiesSet(Game game){
        String[] technologyNames = game.reward.techNames;

        return new HashSet<>(Arrays.asList(technologyNames).subList(0, 5));
    }

    public static HashSet<String> createDataMechanicsSet(Game game){
        String[] mechanicNames = game.reward.mechanicNames;

        return new HashSet<>(Arrays.asList(mechanicNames).subList(0, 5));
    }

    public static HashSet<String> createDataPlatformsSet(Game game){
        String[] platformNames = game.reward.platformNames;

        return new HashSet<>(Arrays.asList(platformNames).subList(0, 1));
    }

    private static Array<CoverListItem> initObjects(Game game){
        Assets assets = game.getAssets();
        TextureAtlas atlas = assets.designObjectsAtlas;
        Array<CoverListItem> objects = new Array<>();

        String[] objectNames = game.reward.objectNames;

        String[] objectRegions = {
                "aliens", "aviation", "business", "cinema", "city", "comedy", "construction",
                "cooking", "criminal", "cyberpunk", "dance", "detective", "fantasy", "farm",
                "fashion", "game_development", "government", "hacker", "horror", "hospital",
                "hunting", "life", "medieval", "music", "ninja", "pirates", "prison", "race",
                "romantic", "rhythm", "school", "space", "sport", "superheros", "time_travel",
                "transport", "vampires", "virtual_animal", "war", "westwood", "zombie"
        };

        for (int i = 0; i < objectNames.length; i++) {
            TextureRegionDrawable drawable1 = createDrawable(atlas, objectRegions[i], 1);
            TextureRegionDrawable drawable2 = createDrawable(atlas, objectRegions[i], 2);

            objects.add(new CoverListItem(objectNames[i] + "_1", drawable1));
            objects.add(new CoverListItem(objectNames[i] + "_2", drawable2));
        }

        return objects;
    }

    private static TextureRegionDrawable createDrawable(TextureAtlas atlas, String regionName, int index) {
        return new TextureRegionDrawable(atlas.findRegion(regionName, index));
    }

    private static Array<CheckListItem> initTechnologies(Game game){
        Assets assets = game.getAssets();
        Skin skin = assets.getSkin();

        Array<CheckListItem> objects = new Array<>();

        String[] objectNames = game.reward.techNames;

        Drawable bgUnselected = skin.getDrawable("programming_item");
        Drawable bgSelected = skin.getDrawable("programming_item_selected");
        Drawable imageFrameUnselected = skin.getDrawable("item_image_bg");
        Drawable imageFrameSelected = skin.getDrawable("item_image_bg_selected");

        TextureRegionDrawable frame1Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame1.png")));
        TextureRegionDrawable  frame2Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame2.png")));

        for (String objectName : objectNames) {
            objects.add(new CheckListItem(objectName, frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected, assets.getSkin()));
        }

        return objects;
    }

    private static Array<CheckListItem> initMechanics(Game game){
        Assets assets = game.getAssets();
        Skin skin = assets.getSkin();

        Array<CheckListItem> objects = new Array<>();

        String[] mechanicNames = game.reward.mechanicNames;

        Drawable bgUnselected = skin.getDrawable("game_design_item");
        Drawable bgSelected = skin.getDrawable("game_design_item_selected");
        Drawable imageFrameUnselected = skin.getDrawable("item_image_bg");
        Drawable imageFrameSelected = skin.getDrawable("item_image_bg_selected");

        TextureRegionDrawable frame1Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame1.png")));
        TextureRegionDrawable  frame2Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame2.png")));

        for (String mechanicName : mechanicNames) {
            objects.add(new CheckListItem(mechanicName, frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected, assets.getSkin()));
        }

        return objects;
    }

    public static Array<CoverListItem> initColors(Assets assets) {
        Skin skin = assets.getSkin();
        Array<CoverListItem> colors = new Array<>();

        String[] colorNames = {
                "Light Blue", "Dark Blue", "Blue", "Red", "Light Red", "Dark Red", "Yellow", "Dark Yellow", "Sandy", "Orange",
                "Light Purple", "Dark Purple", "Purple", "Light Pink", "Pink", "Light Brown", "Dark Brown", "Brown",
                "Black", "White", "Light Grey", "Dark Grey", "Grey", "Dark Green", "Light Green", "Green"
        };

        String[] colorRegions = {
                "light_blue", "dark_blue", "blue", "red", "light_red", "dark_red", "yellow", "dark_yellow", "sandy", "orange",
                "light_purple", "dark_purple", "purple", "light_pink", "pink", "light_brown", "dark_brown", "brown",
                "black", "white", "light_grey", "dark_grey", "grey", "dark_green", "light_green", "green"
        };

        Drawable background = skin.getDrawable("design_item");

        Drawable imageBackground = skin.getDrawable("item_image_bg");

        for (int i = 0; i < colorNames.length; i++) {
            TextureRegionDrawable item = new TextureRegionDrawable(assets.designColorsAtlas.findRegion(colorRegions[i]));
            colors.add(new CoverListItem(colorNames[i], item, background, imageBackground, assets.getSkin()));
        }

        return colors;
    }

    private static Array<CoverListItem> initPlatforms(Game game) {
        Assets assets = game.getAssets();
        Skin skin = assets.getSkin();
        Array<CoverListItem> platforms = new Array<>();

        String[] platformNames = game.reward.platformNames;

        String[] platformRegions = {
                "free_web", "steam", "ubisoft", "google_play"
        };

        Drawable background = skin.getDrawable("platform_item");

        Drawable imageBackground = skin.getDrawable("transparent");

        for (int i = 0; i < platformNames.length; i++) {
            TextureRegionDrawable item = new TextureRegionDrawable(assets.platformAtlas.findRegion(platformRegions[i]));
            platforms.add(new CoverListItem(platformNames[i], item, background, imageBackground, assets.getSkin()));
        }

        return platforms;
    }

    public static Drawable getColor(Assets assets, String name){
        name = name.toLowerCase(Locale.ROOT).replace(" ", "_");
        return new TextureRegionDrawable(assets.designColorsAtlas.findRegion(name));
    }

    public static Drawable getObject(Assets assets, String name){
        TextureAtlas atlas = assets.designObjectsAtlas;

        String drawableName = name.toLowerCase(Locale.ROOT).substring(0, name.length() - 2);
        int drawableIndex = name.charAt(name.length() - 1) == '2' ? 1 : 2;

        return new TextureRegionDrawable(atlas.findRegion(drawableName, drawableIndex));
    }
}
