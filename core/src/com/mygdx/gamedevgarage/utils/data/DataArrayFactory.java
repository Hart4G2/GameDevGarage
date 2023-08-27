package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckListItem;

import java.util.HashSet;

public class DataArrayFactory {

    public static Array<CoverListItem> createCoverObjects(Game game){
        HashSet<String> objectSet = game.getCoverObjects();

        Array<CoverListItem> objects = initObjects(game);

        for(int i = 0; i < objects.size; i++){
            CoverListItem item = objects.get(i);

            item.setPurchased(objectSet.contains(item.getText()));
        }

        return objects;
    }

    public static Array<CheckListItem> createTechnologies(Game game){
        Array<CheckListItem> technologies = initTechnologies(game);
        HashSet<String> technologySet = game.getTechnologies();

        for(int i = 0; i < technologies.size; i++){
            CheckListItem item = technologies.get(i);

            item.setPurchased(technologySet.contains(item.getText()));
        }

        return technologies;
    }

    public static Array<CheckListItem> createMechanics(Game game){
        Array<CheckListItem> mechanics = initMechanics(game);
        HashSet<String> mechanicsSet = game.getMechanics();

        for(int i = 0; i < mechanics.size; i++){
            CheckListItem mechanic = mechanics.get(i);

            mechanic.setPurchased(mechanicsSet.contains(mechanic.getText()));
        }

        return mechanics;
    }

    public static Array<CoverListItem> createPlatform(Game game){
        Array<CoverListItem> platforms = initPlatforms(game);
        HashSet<String> platformSet = game.getPlatforms();

        for(int i = 0; i < platforms.size; i++){
            CoverListItem platform = platforms.get(i);

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
        HashSet<String> technologies = new HashSet<>();

        String[] technologyNames = game.reward.techNames;

        for (int i = 0; i < 5; i++) {
            technologies.add(technologyNames[i]);
        }

        return technologies;
    }

    public static HashSet<String> createDataMechanicsSet(Game game){
        HashSet<String> mechanics = new HashSet<>();

        String[] mechanicNames = game.reward.mechanicNames;


        for (int i = 0; i < 5; i++) {
            mechanics.add(mechanicNames[i]);
        }

        return mechanics;
    }

    public static HashSet<String> createDataPlatformsSet(Game game){
        HashSet<String> platforms = new HashSet<>();

        String[] platformNames = game.reward.platformNames;


        for (int i = 0; i < 1; i++) {
            platforms.add(platformNames[i]);
        }

        return platforms;
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
            TextureRegionDrawable drawable1 = new TextureRegionDrawable(atlas.findRegion(objectRegions[i], 1));
            TextureRegionDrawable drawable2 = new TextureRegionDrawable(atlas.findRegion(objectRegions[i], 2));
            objects.add(new CoverListItem(objectNames[i] + "_1", drawable1));
            objects.add(new CoverListItem(objectNames[i] + "_2", drawable2));
        }

        return objects;
    }

    private static Array<CheckListItem> initTechnologies(Game game){
        Assets assets = game.getAssets();

        Array<CheckListItem> objects = new Array<>();

        String[] objectNames = game.reward.techNames;

        TextureRegionDrawable bgUnselected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("programming_item.png")));

        TextureRegionDrawable bgSelected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("programming_item_selected.png")));

        TextureRegionDrawable imageFrameUnselected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg.png")));

        TextureRegionDrawable imageFrameSelected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg_selected.png")));

        TextureRegionDrawable frame1Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame1.png")));
        TextureRegionDrawable  frame2Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame2.png")));

        for (int i = 0; i < objectNames.length; i++) {
            objects.add(new CheckListItem(objectNames[i], frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected, assets.getSkin()));
        }

        return objects;
    }

    private static Array<CheckListItem> initMechanics(Game game){
        Assets assets = game.getAssets();

        Array<CheckListItem> objects = new Array<>();

        String[] mechanicNames = game.reward.mechanicNames;

        TextureRegionDrawable bgUnselected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("game_design_item.png")));

        TextureRegionDrawable bgSelected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("game_design_item_selected.png")));

        TextureRegionDrawable imageFrameUnselected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg.png")));

        TextureRegionDrawable imageFrameSelected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg_selected.png")));

        TextureRegionDrawable frame1Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame1.png")));
        TextureRegionDrawable  frame2Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame2.png")));

        for (int i = 0; i < mechanicNames.length; i++) {
            objects.add(new CheckListItem(mechanicNames[i], frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected, assets.getSkin()));
        }

        return objects;
    }

    public static Array<CoverListItem> initColors(Assets assets) {
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

        TextureRegionDrawable background = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("design_item.png")));

        TextureRegionDrawable imageBackground = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg.png")));

        for (int i = 0; i < colorNames.length; i++) {
            TextureRegionDrawable item = new TextureRegionDrawable(assets.designColorsAtlas.findRegion(colorRegions[i]));
            colors.add(new CoverListItem(colorNames[i], item, background, imageBackground, assets.getSkin()));
        }

        return colors;
    }

    private static Array<CoverListItem> initPlatforms(Game game) {
        Assets assets = game.getAssets();
        Array<CoverListItem> platforms = new Array<>();

        String[] platformNames = game.reward.platformNames;

        String[] platformRegions = {
                "free_web", "steam", "ubisoft", "google_play"
        };

        TextureRegionDrawable background = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("platform_item.png")));

        TextureRegionDrawable imageBackground = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("transparent.png")));

        for (int i = 0; i < platformNames.length; i++) {
            TextureRegionDrawable item = new TextureRegionDrawable(assets.platformAtlas.findRegion(platformRegions[i]));
            platforms.add(new CoverListItem(platformNames[i], item, background, imageBackground, assets.getSkin()));
        }

        return platforms;
    }
}
