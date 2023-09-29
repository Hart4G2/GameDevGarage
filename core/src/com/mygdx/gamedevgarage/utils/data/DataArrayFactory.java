package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.screens.mini_games.selection_actors.CheckListItem;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.events.Event;
import com.mygdx.gamedevgarage.utils.stats.Cost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

public class DataArrayFactory {

    public static TranslatableObject[] themes;

    public static TranslatableObject[] genres;

    public static String[] getGenresAsStringArray() {
        String[] strings = new String[genres.length];

        for(int i = 0; i < strings.length; i++) {
            strings[i] = genres[i].getText();
        }

        return strings;
    }

    public static String[] getThemesAsStringArray() {
        String[] strings = new String[themes.length];

        for(int i = 0; i < strings.length; i++) {
            strings[i] = themes[i].getText();
        }

        return strings;
    }

    public static TranslatableObject getGenre(String text){
        for (TranslatableObject genre : genres) {
            if(genre.getText().equals(text))
                return genre;
        }
        return null;
    }

    public static TranslatableObject getTheme(String text){
        for (TranslatableObject theme : themes) {
            if(theme.getText().equals(text))
                return theme;
        }
        return null;
    }

    public static final String[] objectNames = {
            "Aliens", "Aviation", "Business", "Cinema", "City", "Comedy", "Construction",
            "Cooking", "Criminal", "Cyberpunk", "Dance", "Detective", "Fantasy", "Farm",
            "Fashion", "Game development", "Government", "Hacker", "Horror", "Hospital",
            "Hunting", "Life", "Medieval", "Music", "Ninja", "Pirates", "Prison", "Race",
            "Romantic", "Rhythm", "School", "Space", "Sport", "Superheros", "Time traveling",
            "Transport", "Vampires", "Virtual animals", "War", "Wild west", "Zombie"
    };

    public static final String[] techNames = {
            "Physics_of_motion", "Procedural_level_generation", "Artificial_intelligence", "Surround_sound",
            "Photorealism", "Virtual_reality", "Multiplayer", "Photomode", "Animated_videos",
            "Realistic_illumination", "Realistic_destruction_physics", "Volumetric_Effects",
            "Interactive_sound", "Add_gamepad_vibration", "Procedural_animation",
            "Gesture_control_system", "Integration_with_cloud_services", "Reactive_environment",
            "Dynamic_change_of_time_of_day", "Loading_screens"
    };

    public static final String[] mechanicNames = {
            "Free_movement_on_the_map", "Time_slows_down", "First_person_camera_control",
            "Nonlinear_plot", "Dodging_and_blocking", "Multiplayer_on_one_screen",
            "Dialogue_selection_system", "Stealth/Invisibility", "Lots_of_playable_characters",
            "Environmental_influence", "Squad_formation", "Change_of_perspective",
            "Base_leveling", "Split-dresser_mode", "Identity_substitution",
            "Time_attack", "Character_evolution", "Online_multiplayer",
            "Creation_of_unique_game_elements_by_the_player", "Complete_destruction_of_the_environment"
    };

    public static final String[] platformNames = {
            "Push_to_free_web", "Buy_a_place_in_play_store", "Order_an_expensive_site", "Order_an_advertising_campaign"
    };

    public static String[] talkHints;
    public static List<CoverObject> objects;
    public static List<CheckObject> technologies;
    public static List<CheckObject> mechanics;
    public static List<CoverObject> platforms;
    public static List<CoverObject> colors;
    public static List<Event> randomEvents;
    public static List<Event> shownRandomEvents;

    public static void initialise(){
        Assets assets = Assets.getInstance();

        String themesJson = Gdx.files.internal("themes.json").readString();
        String genresJson = Gdx.files.internal("genres.json").readString();
        String talksJson = Gdx.files.internal("talks.json").readString();
        String objectsJson = Gdx.files.internal("objects.json").readString();
        String technologiesJson = Gdx.files.internal("technologies.json").readString();
        String mechanicsJson = Gdx.files.internal("mechanics.json").readString();
        String platformsJson = Gdx.files.internal("platforms.json").readString();
        String colorsJson = Gdx.files.internal("colors.json").readString();
        String randomEventsJson = Gdx.files.internal("random_events.json").readString();

        themes = readTranslatableObjectsFromJson(themesJson);
        genres = readTranslatableObjectsFromJson(genresJson);
        talkHints = readStringArrayFromJson(talksJson);
        objects = readCoverObjectsFromJson(objectsJson, assets.designObjectsAtlas, Game.getInstance().getCoverObjects());
        technologies = readCheckObjectsFromJson(technologiesJson, assets.techAtlas, Game.getInstance().getTechnologies());
        mechanics = readCheckObjectsFromJson(mechanicsJson, assets.mechAtlas, Game.getInstance().getMechanics());
        platforms = readCoverObjectsFromJson(platformsJson, assets.platformAtlas, Game.getInstance().getPlatforms());
        colors = readCoverObjectsWithoutCostFromJson(colorsJson, assets.designColorsAtlas);
        randomEvents = readEventsFromJson(randomEventsJson, assets.randomEventsAtlas);
        shownRandomEvents = new ArrayList<>();
    }

    public static String[] readStringArrayFromJson(String json) {
        I18NBundle bundle = Assets.getInstance().myBundle;
        List<String> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String bundleKey = item.getString("text");

            String text = bundle.get(bundleKey);

            result.add(text);
        }

        return result.toArray(new String[0]);
    }

    public static TranslatableObject[] readTranslatableObjectsFromJson(String json) {
        List<TranslatableObject> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String bundleKey = item.getString("text");

            result.add(new TranslatableObject(bundleKey));
        }

        return result.toArray(new TranslatableObject[0]);
    }

    public static List<Event> readEventsFromJson(String json, TextureAtlas atlas) {
        I18NBundle bundle = Assets.getInstance().myBundle;
        List<Event> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String bundleKey = item.getString("text");
            String drawableName = item.getString("drawable");

            String text = bundle.get(bundleKey);

            Drawable drawable = createDrawable(atlas, drawableName, -1);

            JsonValue confirmCostJson = item.get("confirmCost");
            JsonValue rejectCostJson = item.get("rejectCost");
            Cost confirmCost = readCostFromJson(confirmCostJson);
            Cost rejectCost = readCostFromJson(rejectCostJson);

            result.add(new Event(text, drawable, confirmCost, rejectCost));
        }

        return result;
    }

    public static List<CheckObject> readCheckObjectsFromJson(String json, TextureAtlas atlas, HashSet<String> purchasedSet) {
        I18NBundle bundle = Assets.getInstance().myBundle;
        List<CheckObject> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String bundleKey = item.getString("name");
            String regionName = item.getString("drawable");

            String name = bundle.get(bundleKey);
            String description = bundle.get(bundleKey + "_description");

            TextureRegionDrawable frame1 = createDrawable(atlas, regionName, 1);
            TextureRegionDrawable frame2 = createDrawable(atlas, regionName, 2);

            Cost cost = readCostFromJson(item.get("cost"));

            CheckObject checkObject = new CheckObject(name, bundleKey, description, frame1, frame2, cost);
            checkObject.setPurchased(purchasedSet.contains(bundleKey));

            result.add(checkObject);
        }

        return result;
    }

    public static List<CoverObject> readCoverObjectsFromJson(String json, TextureAtlas atlas, HashSet<String> purchasedSet) {
        I18NBundle bundle = Assets.getInstance().myBundle;
        List<CoverObject> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String bundleKey = item.getString("text");
            String itemName = item.getString("item");
            int itemIndex = item.getInt("item_index");

            String name = bundleKey;
            try{
                name = bundle.get(bundleKey);
            } catch (MissingResourceException ignore) {

            }

            Cost cost = readCostFromJson(item.get("cost"));

            Drawable itemDrawable = createDrawable(atlas, itemName, itemIndex);

            CoverObject coverObject = new CoverObject(name, bundleKey, itemDrawable, cost);
            coverObject.setPurchased(purchasedSet.contains(bundleKey));

            result.add(coverObject);
        }

        return result;
    }

    public static List<CoverObject> readCoverObjectsWithoutCostFromJson(String json, TextureAtlas atlas) {
        List<CoverObject> result = new ArrayList<>();

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(json);

        for (JsonValue item : value) {
            String bundleKey = item.getString("text");
            String itemName = item.getString("item");
            int itemIndex = item.getInt("item_index");

            Drawable itemDrawable = createDrawable(atlas, itemName, itemIndex);

            result.add(new CoverObject(bundleKey, itemDrawable));
        }

        return result;
    }

    private static Cost readCostFromJson(JsonValue costJson){
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

        return new Cost(currencies, values);
    }



    public static List<CoverListItem> createCoverObjects(){
        List<CoverListItem> result = new ArrayList<>();

        for (CoverObject item : objects) {
            result.add(new CoverListItem(item));
        }

        return result;
    }

    public static List<CheckListItem> createTechnologies(){
        List<CheckListItem> result = new ArrayList<>();

        for(CheckObject item : technologies){
            result.add(new CheckListItem(item, true));
        }

        return result;
    }

    public static List<CheckListItem> createMechanics(){
        List<CheckListItem> result = new ArrayList<>();

        for(CheckObject item : mechanics){
            result.add(new CheckListItem(item, false));
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
