package com.mygdx.gamedevgarage.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;
import com.mygdx.gamedevgarage.utils.stats.Stats;

import java.util.HashMap;
import java.util.HashSet;

public class DataManager {

    private final Game game;
    private static DataManager instance;

    private static Boolean canSkipTax;

    public DataManager() {
        this.game = Game.getInstance();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void save() {
        saveData();
        saveStats();
        saveGameState();
        saveGames();
        saveCanSkipTax();
        saveIsGameOver();
        saveRandomEvent();
        saveVolume();
    }

    private void saveVolume() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        prefs.putFloat("volume", Assets.getInstance().getVolume());
        prefs.flush();
    }

    private void saveIsGameOver() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        prefs.putBoolean("gameIsOver", game.isGameOver());
        prefs.flush();
    }

    private void saveRandomEvent() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        prefs.putBoolean("isRandomEventShown", game.isRandomEventShown);
        prefs.flush();
    }

    private void saveData() {
        HashSet<String> purchasedCovers = game.getCoverObjects();
        HashSet<String> purchasedTechnologies = game.getTechnologies();
        HashSet<String> purchasedMechanics = game.getMechanics();
        HashSet<String> purchasedPlatforms = game.getPlatforms();

        saveData(purchasedCovers, "purchasedCovers");
        saveData(purchasedTechnologies, "purchasedTechnologies");
        saveData(purchasedMechanics, "purchasedMechanics");
        saveData(purchasedPlatforms, "purchasedPlatforms");
    }

    private void saveData(HashSet<String> purchasedItems, String prefKey) {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        String purchasedItemsJson = new Json().toJson(purchasedItems);
        prefs.putString(prefKey, purchasedItemsJson);
        prefs.flush();
    }

    private void saveStats() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        Stats stats = Stats.getInstance();

        HashMap<String, Integer> statsMap = new HashMap<>();
        statsMap.put("level", stats.getStat(Currency.LEVEL));
        statsMap.put("exp", stats.getStat(Currency.EXPERIENCE));
        statsMap.put("money", stats.getStat(Currency.MONEY));
        statsMap.put("design", stats.getStat(Currency.DESIGN));
        statsMap.put("programming", stats.getStat(Currency.PROGRAMMING));
        statsMap.put("gameDesign", stats.getStat(Currency.GAME_DESIGN));
        statsMap.put("energy", stats.getStat(Currency.ENERGY));

        String purchasedItemsJson = new Json().toJson(statsMap);
        prefs.putString("stats", purchasedItemsJson);
        prefs.flush();
    }

    public void saveGameState() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        String json = new Json().toJson(game.gameState);
        prefs.putString("gameState", json);
        json = new Json().toJson(GameFactory.getProcessData());
        prefs.putString("gameData", json);

        prefs.putBoolean("isGameStarted", game.isGameStarted);
        prefs.putBoolean("screenShowed", game.isScreenShowed);
        prefs.flush();
    }

    public void saveGames() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        String jsonStr = new Json().toJson(game.getGames());
        prefs.putString("games", jsonStr);
        prefs.flush();
    }

    public void saveCanSkipTax() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        if(canSkipTax == null)
            canSkipTax = true;

        prefs.putBoolean("canSkipTax", canSkipTax);
        prefs.flush();
    }

    private boolean getCanSkipTax() {
        return getBooleanData("canSkipTax", true);
    }

    public boolean getSkipTax() {
        if(canSkipTax != null) return canSkipTax;

        canSkipTax = getCanSkipTax();
        return canSkipTax;
    }

    public void setSkipTax(boolean skipTax) {
        canSkipTax = skipTax;
    }

    public float getVolume() {
        return getFloatData("volume", .5f);
    }

    public boolean getGameOver() {
        return getBooleanData("gameIsOver", false);
    }

    public HashSet<String> getCovers() {
        return getData(HashSet.class, "purchasedCovers", new HashSet());
    }

    public HashSet<String> getTechnologies() {
        return getData(HashSet.class, "purchasedTechnologies", new HashSet());
    }

    public HashSet<String> getMechanics() {
        return getData(HashSet.class, "purchasedMechanics", new HashSet());
    }

    public HashSet<String> getPlatforms() {
        return getData(HashSet.class, "purchasedPlatforms", new HashSet());
    }

    public HashMap<String, Integer> getStats() {
        return getData(HashMap.class, "stats", new HashMap<String, Integer>());
    }

    public boolean isGameStarted() {
        return getBooleanData("isGameStarted", false);
    }

    public GameState getGameState() {
        return getData(GameState.class, "gameState", null);
    }

    public boolean isScreenShowed() {
        return getBooleanData("screenShowed", false);
    }

    public boolean isRandomEventStarted() {
        return getBooleanData("isRandomEventShown", false);
    }

    public HashMap<String, String> getGameData() {
        return getData(HashMap.class, "gameData", new HashMap<String, String>());
    }

    public HashSet<GameObject> getGames() {
        return getData(HashSet.class, "games", new HashSet<>());
    }

    private <T> T getData(Class<T> type, String prefKey, T defaultValue) {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");
        String dataJson = prefs.getString(prefKey, null);

        if (dataJson == null || dataJson.isEmpty() || dataJson.equals("null")) {
            return defaultValue;
        }

        return new Json().fromJson(type, dataJson);
    }

    private boolean getBooleanData(String prefKey, boolean defaultValue) {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");
        boolean dataJson = prefs.getBoolean(prefKey, defaultValue);

        return new Json().fromJson(Boolean.class, String.valueOf(dataJson));
    }

    private float getFloatData(String prefKey, float defaultValue) {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");
        float dataJson = prefs.getFloat(prefKey, defaultValue);

        return new Json().fromJson(Float.class, String.valueOf(dataJson));
    }
}
