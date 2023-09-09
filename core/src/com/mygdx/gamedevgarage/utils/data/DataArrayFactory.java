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
import java.util.function.Function;

public class DataArrayFactory {

    public static final String[] themes = {
            "Aliens", "Aviation", "Business", "Cinema", "City", "Comedy", "Construction",
            "Cooking", "Criminal", "Cyberpunk", "Dance", "Detective", "Fantasy", "Farm",
            "Fashion", "Game development", "Government", "Hacker", "Horror", "Hospital",
            "Hunting", "Life", "Medieval", "Music", "Ninja", "Pirates", "Prison", "Race",
            "Romantic", "Rhythm", "School", "Space", "Sport", "Superheros", "Time traveling",
            "Transport", "Vampires", "Virtual animals", "War", "Wild west", "Zombie"
    };

    public static final String[] genres = {
            "Shooter", "Arcade", "Strategy", "RPG", "Platform", "Stealth",
            "Survival", "Action", "Quest"
    };

    public static final String[] gameLevels = {
            "Flash game", "AA game", "AAA game"
    };

    public static final String[] objectNames = {
            "Aliens", "Aviation", "Business", "Cinema", "City", "Comedy", "Construction",
            "Cooking", "Criminal", "Cyberpunk", "Dance", "Detective", "Fantasy", "Farm",
            "Fashion", "Game development", "Government", "Hacker", "Horror", "Hospital",
            "Hunting", "Life", "Medieval", "Music", "Ninja", "Pirates", "Prison", "Race",
            "Romantic", "Rhythm", "School", "Space", "Sport", "Superheros", "Time traveling",
            "Transport", "Vampires", "Virtual animals", "War", "Wild west", "Zombie"
    };

    public static final String[] techNames = {
            "Physics of motion", "Procedural level \ngeneration", "Artificial intelligence", "Surround sound",
            "Photorealism", "Virtual reality", "Multiplayer", "Photomode", "Animated videos",
            "Realistic illumination", "Realistic destruction physics", "Volumetric Effects",
            "Interactive sound", "Add gamepad vibration", "Procedural animation",
            "Gesture control system", "Integration with cloud services", "Reactive environment",
            "Dynamic change of time of day", "Loading screens"
    };

    public static final String[] mechanicNames = {
            "Free movement on the map", "Time slows down", "First person camera control",
            "Nonlinear plot", "Dodging and blocking", "Multiplayer on one screen",
            "Dialogue selection system", "Stealth/Invisibility", "Lots of playable characters",
            "Environmental influence", "Squad formation", "Change of perspective", "Base leveling",
            "Split-dresser mode", "Identity substitution", "Time attack", "Character evolution",
            "Online multiplayer", "Creation of unique game elements by the player", "Hospital",
            "Complete destruction of the environment"
    };

    public static final String[] platformNames = {
            "Push to free web", "Buy a place in play store", "Order an expensive site", "Order an advertising campaign"
    };

    public static Array<CoverListItem> createCoverObjects(){
        Array<CoverListItem> objects = initObjects();
        HashSet<String> objectSet = Game.getInstance().getCoverObjects();

        for (CoverListItem item : objects) {
            item.setPurchased(objectSet.contains(item.getText()));
        }

        return objects;
    }

    public static Array<CheckListItem> createTechnologies(){
        Array<CheckListItem> technologies = initTechnologies();
        HashSet<String> technologySet = Game.getInstance().getTechnologies();

        for (CheckListItem item : technologies) {
            item.setPurchased(technologySet.contains(item.getText()));
        }

        return technologies;
    }

    public static Array<CheckListItem> createMechanics(){
        Array<CheckListItem> mechanics = initMechanics();
        HashSet<String> mechanicsSet = Game.getInstance().getMechanics();

        for (CheckListItem mechanic : mechanics) {
            mechanic.setPurchased(mechanicsSet.contains(mechanic.getText()));
        }

        return mechanics;
    }

    public static Array<CoverListItem> createPlatform(){
        Array<CoverListItem> platforms = initPlatforms();
        HashSet<String> platformSet = Game.getInstance().getPlatforms();

        for (CoverListItem platform : platforms) {
            platform.setPurchased(platformSet.contains(platform.getText()));
        }

        return platforms;
    }

    public static HashSet<String> createDataObjectsSet(){
        HashSet<String> objects = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            objects.add(objectNames[i] + "_1");
            objects.add(objectNames[i] + "_2");
        }

        return objects;
    }

    public static HashSet<String> createDataTechnologiesSet(){
        return new HashSet<>(Arrays.asList(techNames).subList(0, 5));
    }

    public static HashSet<String> createDataMechanicsSet(){
        return new HashSet<>(Arrays.asList(mechanicNames).subList(0, 5));
    }

    public static HashSet<String> createDataPlatformsSet(){
        return new HashSet<>(Arrays.asList(platformNames).subList(0, 1));
    }

    private static Array<CoverListItem> initObjects(){
        Assets assets = Assets.getInstance();
        TextureAtlas atlas = assets.designObjectsAtlas;
        Array<CoverListItem> objects = new Array<>();

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

    private static Array<CheckListItem> initTechnologies() {
        Assets assets = Assets.getInstance();
        TextureAtlas atlas = assets.techAtlas;
        Skin skin = assets.getSkin();

        Array<CheckListItem> objects = new Array<>();

        Drawable bgUnselected = skin.getDrawable("programming_item");
        Drawable bgSelected = skin.getDrawable("programming_item_selected");
        Drawable imageFrameUnselected = skin.getDrawable("item_image_bg");
        Drawable imageFrameSelected = skin.getDrawable("item_image_bg_selected");


        for (String techName : techNames) {
            String regionName = techName.replace("\n", "");

            TextureRegionDrawable frame1Texture = createDrawable(atlas, regionName, 1);
            TextureRegionDrawable frame2Texture = createDrawable(atlas, regionName, 2);

            if(atlas.findRegion(regionName, 1) == null|| atlas.findRegion(regionName, 2) == null){
                System.out.println(regionName);
            }

            objects.add(new CheckListItem(techName, frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected));
        }

        return objects;
    }

    private static TextureRegionDrawable createDrawable(TextureAtlas atlas, String regionName, int index) {
        return new TextureRegionDrawable(atlas.findRegion(regionName, index));
    }

    private static Array<CheckListItem> initMechanics(){
        Skin skin = Assets.getInstance().getSkin();

        Array<CheckListItem> objects = new Array<>();

        Drawable bgUnselected = skin.getDrawable("game_design_item");
        Drawable bgSelected = skin.getDrawable("game_design_item_selected");
        Drawable imageFrameUnselected = skin.getDrawable("item_image_bg");
        Drawable imageFrameSelected = skin.getDrawable("item_image_bg_selected");

        TextureRegionDrawable frame1Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame1.png")));
        TextureRegionDrawable  frame2Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame2.png")));

        for (String mechanicName : mechanicNames) {
            objects.add(new CheckListItem(mechanicName, frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected));
        }

        return objects;
    }

    public static Array<CoverListItem> initColors() {
        Assets assets = Assets.getInstance();
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
            colors.add(new CoverListItem(colorNames[i], item, background, imageBackground));
        }

        return colors;
    }

    private static Array<CoverListItem> initPlatforms() {
        Assets assets = Assets.getInstance();
        Skin skin = assets.getSkin();
        Array<CoverListItem> platforms = new Array<>();

        String[] platformRegions = {
                "placeinplaystore", "freeweb", "expensivesite", "advertisingcampaign"
        };

        Drawable background = skin.getDrawable("platform_item");

        Drawable imageBackground = skin.getDrawable("transparent");

        for (int i = 0; i < platformNames.length; i++) {
            TextureRegionDrawable item = new TextureRegionDrawable(assets.platformAtlas.findRegion(platformRegions[i]));
            platforms.add(new CoverListItem(platformNames[i], item, background, imageBackground));
        }

        return platforms;
    }

    public static Drawable getObject(String name){
        TextureAtlas atlas = Assets.getInstance().designObjectsAtlas;

        String drawableName = name.toLowerCase(Locale.ROOT).substring(0, name.length() - 2);
        int drawableIndex = name.charAt(name.length() - 1) == '2' ? 1 : 2;

        return new TextureRegionDrawable(atlas.findRegion(drawableName, drawableIndex));
    }
}
