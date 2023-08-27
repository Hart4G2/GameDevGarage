package com.mygdx.gamedevgarage;


import com.mygdx.gamedevgarage.mini_games.DesignScreen;
import com.mygdx.gamedevgarage.mini_games.EndScreen;
import com.mygdx.gamedevgarage.mini_games.GameDesignScreen;
import com.mygdx.gamedevgarage.mini_games.PlatformScreen;
import com.mygdx.gamedevgarage.mini_games.ProgrammingScreen;
import com.mygdx.gamedevgarage.mini_games.reward.Reward;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class Game extends com.badlogic.gdx.Game {

    private Assets assets;
    private MainScreen mainScreen;

    private HashSet<String> coverObjects;
    private HashSet<String> technologies;
    private HashSet<String> mechanics;
    private HashSet<String> platforms;
    private List<GameObject> games;

    public Stats stats;
    public Reward reward;
    public DataManager dataManager;

    @Override
    public void create() {
        assets = new Assets();
        dataManager = new DataManager(this);

        games = new ArrayList<>();

        HashMap<String, Integer> statsMap = dataManager.getStats();
        if(statsMap.isEmpty()){
            stats = new Stats(1, 0, 10, 10, 10, 1);
        } else {
            stats = new Stats(statsMap.get("level"), statsMap.get("exp"),
                    statsMap.get("design"), statsMap.get("programming"),
                    statsMap.get("gameDesign"), statsMap.get("money"));
        }

        reward = new Reward(this);

        mainScreen = new MainScreen(this);

        if(!getSavedData()) {
            coverObjects = DataArrayFactory.createDataObjectsSet(this);
            technologies = DataArrayFactory.createDataTechnologiesSet(this);
            mechanics = DataArrayFactory.createDataMechanicsSet(this);
            platforms = DataArrayFactory.createDataPlatformsSet(this);
        }

        setMainScreen();
    }

    @Override
    public void pause() {
        dataManager.save();
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        dataManager.save();
        assets.dispose();
    }

    private boolean getSavedData(){
        coverObjects = dataManager.getCovers();
        if(coverObjects.isEmpty()) return false;

        technologies = dataManager.getTechnologies();
        mechanics = dataManager.getMechanics();
        platforms = dataManager.getPlatforms();
        return true;
    }

    public Assets getAssets() {
        return assets;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public void setMainScreen() {
        setScreen(mainScreen);
    }

    public void setCoverScreen() {
        setScreen(new DesignScreen(this));
    }

    public void setTechScreen() {
        setScreen(new ProgrammingScreen(this));
    }

    public void setMechanicScreen() {
        setScreen(new GameDesignScreen(this));
    }

    public void setPlatformScreen() {
        setScreen(new PlatformScreen(this));
    }

    public void setUpgradeScreen() {
        setScreen(new UpgradeScreen(this));
    }

    public void setEndScreen() {
        setScreen(new EndScreen(this));
    }

    public HashSet<String> getCoverObjects() {
        return coverObjects;
    }

    public void setCoverObjectPurchased(String itemName) {
        coverObjects.add(itemName);
    }

    public HashSet<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologyPurchased(String itemName) {
        technologies.add(itemName);
    }

    public HashSet<String> getMechanics() {
        return mechanics;
    }

    public void setMechanicPurchased(String itemName) {
        mechanics.add(itemName);
    }

    public HashSet<String> getPlatforms() {
        return platforms;
    }

    public void setPlatformPurchased(String itemName) {
        platforms.add(itemName);
    }

    public List<GameObject> getGames() {
        return games;
    }

    public void addGame(GameObject gameObject){
        games.add(gameObject);
        System.out.println(games.size());
    }
}
