package com.mygdx.gamedevgarage;


import com.mygdx.gamedevgarage.screens.MainScreen;
import com.mygdx.gamedevgarage.screens.collection.CollectionScreen;
import com.mygdx.gamedevgarage.screens.game_event.EventPublisher;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.game_event.Observer;
import com.mygdx.gamedevgarage.screens.game_event.random.RandomEvent;
import com.mygdx.gamedevgarage.screens.game_event.random.RandomEventScreen;
import com.mygdx.gamedevgarage.screens.game_event.tax.GameOverScreen;
import com.mygdx.gamedevgarage.screens.game_event.tax.TaxEvent;
import com.mygdx.gamedevgarage.screens.menu.MenuScreen;
import com.mygdx.gamedevgarage.screens.mini_games.DesignScreen;
import com.mygdx.gamedevgarage.screens.mini_games.EndScreen;
import com.mygdx.gamedevgarage.screens.mini_games.GameDesignScreen;
import com.mygdx.gamedevgarage.screens.mini_games.PlatformScreen;
import com.mygdx.gamedevgarage.screens.mini_games.ProgrammingScreen;
import com.mygdx.gamedevgarage.screens.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

import java.util.HashSet;


public class Game extends com.badlogic.gdx.Game implements Observer {

    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private CollectionScreen collectionScreen;
    private UpgradeScreen upgradeScreen;
    private DesignScreen designScreen;
    private ProgrammingScreen programmingScreen;
    private GameDesignScreen gameDesignScreen;
    private PlatformScreen platformScreen;
    private EndScreen endScreen;
    private RandomEventScreen randomEventScreen;
    private GameOverScreen gameOverScreen;

    public HashSet<String> coverObjects;
    public HashSet<String> technologies;
    public HashSet<String> mechanics;
    public HashSet<String> platforms;
    public HashSet<GameObject> games;


    public boolean isRandomEventShown = false;
    public boolean isRandomEventShownFromLastOpen = false;
    public boolean isGameOver = false;
    public boolean isGameStarted = false;
    public GameState gameState;
    public boolean isScreenShowed;
    public boolean isEndScreenCalculated;
    private DataManager dataManager;
    public EventPublisher eventPublisher;

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
        Assets.getInstance().loadMusic();

        menuScreen = new MenuScreen();

        setMenuScreen();
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
        menuScreen.dispose();
        try {
            mainScreen.dispose();
            collectionScreen.dispose();
            upgradeScreen.dispose();
            designScreen.dispose();
            programmingScreen.dispose();
            gameDesignScreen.dispose();
            platformScreen.dispose();
            endScreen.dispose();
            randomEventScreen.dispose();
            gameOverScreen.dispose();
        } catch (NullPointerException ignored){

        }
    }

    public void setMenuScreen() {
        setScreen(menuScreen);
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public void setMainScreen() {
        if(mainScreen == null) mainScreen = new MainScreen();

        setScreen(mainScreen);
    }

    public void setGameOverScreen() {
        if(gameOverScreen == null) gameOverScreen = new GameOverScreen();

        setScreen(gameOverScreen);
    }

    public void setCoverScreen() {
        if(designScreen == null) designScreen = new DesignScreen();

        setScreen(designScreen);
    }

    public void setTechScreen() {
        if(programmingScreen == null) programmingScreen = new ProgrammingScreen();

        setScreen(programmingScreen);
    }

    public void setMechanicScreen() {
        if(gameDesignScreen == null) gameDesignScreen = new GameDesignScreen();

        setScreen(gameDesignScreen);
    }

    public void setPlatformScreen() {
        if(platformScreen == null) platformScreen = new PlatformScreen();

        setScreen(platformScreen);
    }

    public void setUpgradeScreen() {
        if(upgradeScreen == null) upgradeScreen = new UpgradeScreen();

        setScreen(upgradeScreen);
    }

    public void setCollectionScreen() {
        if(collectionScreen == null) collectionScreen = new CollectionScreen();

        setScreen(collectionScreen);
    }

    public void setEndScreen() {
        if(endScreen == null) endScreen = new EndScreen();

        setScreen(endScreen);
    }

    public void setRandomEventScreen(RandomEvent event) {
        if(randomEventScreen == null) randomEventScreen = new RandomEventScreen();

        randomEventScreen.setRandomEvent(event);
        setScreen(randomEventScreen);
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

    @Override
    public void onEventReceived(final GameEvent event) {
        event.start();
    }

    public void stopAllThreads(){
        RandomEvent.eventTimer.stop();
        TaxEvent.eventTimer.stop();
        getMainScreen().stopSelling();
        DialogThread.getInstance().pause();
    }

    public void resumeAllThreads(){
        RandomEvent.eventTimer.start();
        TaxEvent.eventTimer.start();
        getMainScreen().resumeSelling();
        DialogThread.getInstance().resume();
    }

    public void restartAllThreads(){
        RandomEvent.eventTimer.clear();
        RandomEvent.eventTimer.start();
        TaxEvent.eventTimer.clear();
        TaxEvent.eventTimer.start();

        TaxEvent.scheduleEvent(new TaxEvent());

        RandomEvent randomEvent = new RandomEvent();
        RandomEvent.scheduleNextEvent(randomEvent);

        getMainScreen().resumeSelling();
        DialogThread.getInstance().restart();
    }

    public void restart() {
        setMainScreen();
        coverObjects = DataArrayFactory.createDataObjectsSet();
        technologies = DataArrayFactory.createDataTechnologiesSet();
        mechanics = DataArrayFactory.createDataMechanicsSet();
        platforms = DataArrayFactory.createDataPlatformsSet();
        DataArrayFactory.shownRandomEvents.clear();
        games.clear();
        Stats.setInstance(new Stats(1, 0, 5, 5, 5, 20, 8));
        StatsTable.getInstance().update();
        gameState = null;
        DataManager.getInstance().setSkipTax(true);
        mainScreen.removeSellingGames();
        mainScreen.setGameCanceled();
        isGameOver = false;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
