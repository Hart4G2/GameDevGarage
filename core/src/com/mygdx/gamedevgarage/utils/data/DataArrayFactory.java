package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.screens.mini_games.selection_actors.CheckListItem;
import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

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

    public static List<CoverObject> objects;
    public static List<CheckObject> technologies;
    public static List<CheckObject> mechanics;
    public static List<CoverObject> platforms;
    public static List<CoverObject> colors;

    public static void initialise(){
        Assets assets = Assets.getInstance();

        String objectsJson = Gdx.files.internal("objects.json").readString();
        String technologiesJson = Gdx.files.internal("technologies.json").readString();
        String mechanicsJson = Gdx.files.internal("mechanics.json").readString();
        String platformsJson = Gdx.files.internal("platforms.json").readString();
        String colorsJson = Gdx.files.internal("colors.json").readString();

        objects = readCoverObjectsFromJson(objectsJson, assets.designObjectsAtlas, Game.getInstance().getCoverObjects());
        technologies = readCheckObjectsFromJson(technologiesJson, assets.techAtlas, Game.getInstance().getTechnologies());
        mechanics = readMechanicsFromJson(mechanicsJson);
        platforms = readCoverObjectsFromJson(platformsJson, assets.platformAtlas, Game.getInstance().getPlatforms());
        colors = readCoverObjectsWithoutCostFromJson(colorsJson, assets.designColorsAtlas);
    }

    public static List<CheckObject> readCheckObjectsFromJson(String json, TextureAtlas atlas, HashSet<String> purchasedSet) {
        List<CheckObject> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String name = item.getString("name");
            String description = item.getString("description");

            String regionName = name.replace("\n", "");
            TextureRegionDrawable frame1 = createDrawable(atlas, regionName, 1);
            TextureRegionDrawable frame2 = createDrawable(atlas, regionName, 2);

            JsonValue costJson = item.get("cost");
            JsonValue valueArrayJson = costJson.get("value");

            int[] values = new int[valueArrayJson.size];
            for (int i = 0; i < valueArrayJson.size; i++) {
                values[i] = valueArrayJson.getInt(i);
            }

            JsonValue currencyArrayJson = costJson.get("currency");
            Currency[] currencies = new Currency[currencyArrayJson.size];
            for (int i = 0; i < currencyArrayJson.size; i++) {
                currencies[i] = Currency.fromString(currencyArrayJson.getString(i));
            }

            Cost cost = new Cost(currencies, values);

            CheckObject checkObject = new CheckObject(name, description, frame1, frame2, cost);
            checkObject.setPurchased(purchasedSet.contains(name));

            result.add(checkObject);
        }

        return result;
    }

    public static List<CheckObject> readMechanicsFromJson(String json) {
        List<CheckObject> result = new ArrayList<>();
        HashSet<String> purchasedSet = Game.getInstance().getMechanics();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String name = item.getString("name");
            String description = item.getString("description");

            TextureRegionDrawable frame1 = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame1.png")));
            TextureRegionDrawable  frame2 = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame2.png")));

            JsonValue costJson = item.get("cost");
            JsonValue valueArrayJson = costJson.get("value");

            int[] values = new int[valueArrayJson.size];
            for (int i = 0; i < valueArrayJson.size; i++) {
                values[i] = valueArrayJson.getInt(i);
            }

            JsonValue currencyArrayJson = costJson.get("currency");
            Currency[] currencies = new Currency[currencyArrayJson.size];
            for (int i = 0; i < currencyArrayJson.size; i++) {
                currencies[i] = Currency.fromString(currencyArrayJson.getString(i));
            }

            Cost cost = new Cost(currencies, values);

            CheckObject checkObject = new CheckObject(name, description, frame1, frame2, cost);
            checkObject.setPurchased(purchasedSet.contains(name));

            result.add(checkObject);
        }

        return result;
    }

    public static List<CoverObject> readCoverObjectsFromJson(String json, TextureAtlas atlas, HashSet<String> purchasedSet) {
        List<CoverObject> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String name = item.getString("text");
            String itemName = item.getString("item");
            int itemIndex = item.getInt("item_index");

            JsonValue costJson = item.get("cost");
            JsonValue valueArrayJson = costJson.get("value");

            int[] values = new int[valueArrayJson.size];
            for (int i = 0; i < valueArrayJson.size; i++) {
                values[i] = valueArrayJson.getInt(i);
            }

            JsonValue currencyArrayJson = costJson.get("currency");
            Currency[] currencies = new Currency[currencyArrayJson.size];
            for (int i = 0; i < currencyArrayJson.size; i++) {
                currencies[i] = Currency.fromString(currencyArrayJson.getString(i));
            }

            Cost cost = new Cost(currencies, values);

            Drawable itemDrawable = createDrawable(atlas, itemName, itemIndex);

            CoverObject coverObject = new CoverObject(name, itemDrawable, cost);
            coverObject.setPurchased(purchasedSet.contains(name));

            result.add(coverObject);
        }

        return result;
    }

    public static List<CoverObject> readCoverObjectsWithoutCostFromJson(String json, TextureAtlas atlas) {
        List<CoverObject> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String text = item.getString("text");
            String itemName = item.getString("item");
            int itemIndex = item.getInt("item_index");

            Drawable itemDrawable = createDrawable(atlas, itemName, itemIndex);

            result.add(new CoverObject(text, itemDrawable));
        }

        return result;
    }

    public static List<CoverListItem> createCoverObjects(){
        List<CoverListItem> result = new ArrayList<>();

        for (CoverObject item : objects) {
            result.add(new CoverListItem(item));
        }

        return result;
    }

    public static List<CheckListItem> createTechnologiesListItems(){
        List<CheckListItem> result = new ArrayList<>();

        for(CheckObject item : technologies){
            result.add(new CheckListItem(item));
        }

        return result;
    }

    public static List<CheckListItem> createMechanics(){
        List<CheckListItem> result = new ArrayList<>();
        HashSet<String> mechanicsSet = Game.getInstance().getMechanics();

        for(CheckObject item : mechanics){
            item.setPurchased(mechanicsSet.contains(item.getName()));
            result.add(new CheckListItem(item));
        }

        return result;
    }

    public static List<CoverListItem> createPlatforms(){
        List<CoverListItem> result = new ArrayList<>();

        Skin skin = Assets.getInstance().getSkin();
        Drawable background = skin.getDrawable("platform_item");
        Drawable imageBackground = skin.getDrawable("transparent");

        for (CoverObject platform : platforms) {
            result.add(new CoverListItem(platform, background, imageBackground));
        }

        return result;
    }

    public static List<CoverListItem> createColors(){
        List<CoverListItem> result = new ArrayList<>();

        Skin skin = Assets.getInstance().getSkin();
        Drawable background = skin.getDrawable("design_item");
        Drawable imageBackground = skin.getDrawable("item_image_bg");

        for (CoverObject color : colors) {
            result.add(new CoverListItem(color, background, imageBackground));
        }

        return result;
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

    private static TextureRegionDrawable createDrawable(TextureAtlas atlas, String regionName, int index) {
        return new TextureRegionDrawable(atlas.findRegion(regionName, index));
    }

    public static Drawable getObject(String name){
        TextureAtlas atlas = Assets.getInstance().designObjectsAtlas;

        String drawableName = name.toLowerCase(Locale.ROOT).substring(0, name.length() - 2);
        int drawableIndex = name.charAt(name.length() - 1) == '2' ? 1 : 2;

        return new TextureRegionDrawable(atlas.findRegion(drawableName, drawableIndex));
    }
}
