package com.mygdx.gamedevgarage;


import static com.mygdx.gamedevgarage.utils.Utils.convertStringToList;

import com.mygdx.gamedevgarage.collection.CollectionScreen;
import com.mygdx.gamedevgarage.mini_games.DesignScreen;
import com.mygdx.gamedevgarage.mini_games.EndScreen;
import com.mygdx.gamedevgarage.mini_games.GameDesignScreen;
import com.mygdx.gamedevgarage.mini_games.PlatformScreen;
import com.mygdx.gamedevgarage.mini_games.ProgrammingScreen;
import com.mygdx.gamedevgarage.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;

import java.util.HashMap;
import java.util.HashSet;


public class Game extends com.badlogic.gdx.Game {

    private MainScreen mainScreen;

    private HashSet<String> coverObjects;
    private HashSet<String> technologies;
    private HashSet<String> mechanics;
    private HashSet<String> platforms;
    private HashSet<GameObject> games;

    public boolean isGameStarted = false;
    public GameState gameState;
    private DataManager dataManager;

    private static Game instance;

    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    @Override
    public void create() {
        dataManager = DataManager.getInstance();

        initData();
        setMainScreen();
        getSavedGameState();
    }

    private void initData(){
        games = dataManager.getGames();

        mainScreen = new MainScreen();

        if(!getSavedData()) {
            coverObjects = DataArrayFactory.createDataObjectsSet();
            technologies = DataArrayFactory.createDataTechnologiesSet();
            mechanics = DataArrayFactory.createDataMechanicsSet();
            platforms = DataArrayFactory.createDataPlatformsSet();
        }

        for(GameObject gameObject : games){
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
        Assets.getInstance().dispose();
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

        DialogThread dialogThread = DialogThread.getInstance();

        switch (gameState){
            case MAIN: {
                dialogThread.start();
                break;
            }
            case DESIGN: {
                dialogThread.setDesignThread();
                break;
            }
            case PROGRAMMING: {
                dialogThread.setProgrammingThread();
                break;
            }
            case GAMEDESIGN: {
                dialogThread.setGameDesignThread();
                break;
            }
            case PLATFORM: {
                dialogThread.setPlatformThread();
                break;
            }
            case END: {
                dialogThread.setEndGameThread();
                break;
            }
        }
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public void setMainScreen() {
        setScreen(mainScreen);
    }

    public void setCoverScreen() {
        setScreen(new DesignScreen());
    }

    public void setTechScreen() {
        setScreen(new ProgrammingScreen());
    }

    public void setMechanicScreen() {
        setScreen(new GameDesignScreen());
    }

    public void setPlatformScreen() {
        setScreen(new PlatformScreen());
    }

    public void setUpgradeScreen() {
        setScreen(new UpgradeScreen());
    }

    public void setCollectionScreen() {
        setScreen(new CollectionScreen());
    }

    public void setEndScreen() {
        setScreen(new EndScreen());
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
