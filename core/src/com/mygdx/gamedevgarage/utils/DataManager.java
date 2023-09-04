package com.mygdx.gamedevgarage.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;

import java.util.HashMap;
import java.util.HashSet;

public class DataManager {

    private final Game game;

    public DataManager(Game game) {
        this.game = game;
    }

    public void save() {
        saveData();
        saveStats();
        saveGameState();
        saveGames();
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

        HashMap<String, Integer> statsMap = new HashMap<>();
        statsMap.put("level", game.stats.getLevel());
        statsMap.put("exp", game.stats.getExperience());
        statsMap.put("money", game.stats.getMoney());
        statsMap.put("design", game.stats.getDesign());
        statsMap.put("programming", game.stats.getProgramming());
        statsMap.put("gameDesign", game.stats.getGameDesign());

        String purchasedItemsJson = new Json().toJson(statsMap);
        prefs.putString("stats", purchasedItemsJson);
        prefs.flush();
    }

    public void saveGameState() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        String json = new Json().toJson(game.gameState);
        prefs.putString("gameState", json);
        json = new Json().toJson(game.isGameStarted);
        prefs.putString("isGameStarted", json);
        json = new Json().toJson(GameFactory.getProcessData());
        prefs.putString("gameData", json);
        prefs.flush();
    }

    public void saveGames() {
        Preferences prefs = Gdx.app.getPreferences("my-game-data");

        String jsonStr = new Json().toJson(game.getGames());
        prefs.putString("games", jsonStr);
        prefs.flush();
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
        return getData(Boolean.class, "isGameStarted", false);
    }

    public GameState getGameState() {
        return getData(GameState.class, "gameState", null);
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

        if (dataJson == null) {
            return defaultValue;
        }

        return new Json().fromJson(type, dataJson);
    }
}
