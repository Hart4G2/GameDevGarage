package com.mygdx.gamedevgarage;


import static com.mygdx.gamedevgarage.utils.Utils.convertStringToList;

import com.mygdx.gamedevgarage.collection.CollectionScreen;
import com.mygdx.gamedevgarage.mini_games.DesignScreen;
import com.mygdx.gamedevgarage.mini_games.EndScreen;
import com.mygdx.gamedevgarage.mini_games.GameDesignScreen;
import com.mygdx.gamedevgarage.mini_games.PlatformScreen;
import com.mygdx.gamedevgarage.mini_games.ProgrammingScreen;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;
import com.mygdx.gamedevgarage.utils.reward.Reward;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Game extends com.badlogic.gdx.Game {

    private Assets assets;
    private MainScreen mainScreen;

    private HashSet<String> coverObjects;
    private HashSet<String> technologies;
    private HashSet<String> mechanics;
    private HashSet<String> platforms;
    private HashSet<GameObject> games;

    public boolean isGameStarted = false;
    public GameState gameState;

    public Stats stats;
    public Reward reward;
    public DataManager dataManager;
    public DialogThread dialogThread;

    @Override
    public void create() {
        assets = new Assets();
        dataManager = new DataManager(this);
        reward = new Reward(this);

        initData();
        setMainScreen();
        getSavedGameState();

        debug();
//        mainScreen.debug();
    }

    private void debug(){
        ArrayList<String> technologies = new ArrayList<>();
        technologies.add("Surround sound");
        ArrayList<String> mechanics = new ArrayList<>();
        mechanics.add("Nonlinear plot");

        GameObject gameObject1 = GameFactory.createGameObjectDebug(this, 0, "Aviation_2", "Light Blue",
                "Aviation_2", technologies, mechanics, "Create a site",
                7, 105, 35, false, 0 , 0);

        games.add(gameObject1);
    }

    private void initData(){
        games = dataManager.getGames();

        HashMap<String, Integer> statsMap = dataManager.getStats();
        if(statsMap.isEmpty()){
            stats = new Stats(1, 49, 10, 10, 10, 1);
        } else {
            stats = new Stats(statsMap.get("level"), statsMap.get("exp"),
                    statsMap.get("design"), statsMap.get("programming"),
                    statsMap.get("gameDesign"), statsMap.get("money"));
        }

        mainScreen = new MainScreen(this);
        dialogThread = new DialogThread(this);

        if(!getSavedData()) {
            coverObjects = DataArrayFactory.createDataObjectsSet(this);
            technologies = DataArrayFactory.createDataTechnologiesSet(this);
            mechanics = DataArrayFactory.createDataMechanicsSet(this);
            platforms = DataArrayFactory.createDataPlatformsSet(this);
        }

        for(GameObject gameObject : games){
            System.out.println(gameObject);
            gameObject.setGame(this);
            if(!gameObject.isSold()){
                mainScreen.startSellGame(gameObject);
            }
        }
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

    private void getSavedGameState(){
        isGameStarted = dataManager.isGameStarted();
        if(isGameStarted){
            gameState = dataManager.getGameState();
            setGameState();
            mainScreen.setGameStarted();
        }
    }

    private void setSavedGameData() {
        HashMap<String, String> gameData = dataManager.getGameData();

        GameFactory.name = gameData.get("name");
        GameFactory.genre = gameData.get("genre");
        GameFactory.theme = gameData.get("theme");
        GameFactory.color = gameData.get("color");
        GameFactory.object = gameData.get("object");
        GameFactory.technologies = convertStringToList(gameData.get("technologies"));
        GameFactory.mechanics = convertStringToList(gameData.get("mechanics"));
        GameFactory.platform = gameData.get("platform");
    }

    private void setGameState(){
        setSavedGameData();

        switch (gameState){
            case MAIN: {
                dialogThread.start();
                break;
            }
            case DESIGN: {
                DialogThread.setDesignThread();
                break;
            }
            case PROGRAMMING: {
                DialogThread.setProgrammingThread();
                break;
            }
            case GAMEDESIGN: {
                DialogThread.setGameDesignThread();
                break;
            }
            case PLATFORM: {
                DialogThread.setPlatformThread();
                break;
            }
            case END: {
                DialogThread.setEndGameThread();
                break;
            }
        }
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

    public void setCollectionScreen() {
        setScreen(new CollectionScreen(this));
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

    public HashSet<GameObject> getGames() {
        return games;
    }

    public void addGame(GameObject gameObject){
        games.add(gameObject);
    }

    public void setGameSold(GameObject gameObject){
        for(GameObject game : games){
            if(gameObject.equals(game)){
                game.setSold(true);
            }
        }
    }
}
