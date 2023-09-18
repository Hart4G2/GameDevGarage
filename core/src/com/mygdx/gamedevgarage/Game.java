package com.mygdx.gamedevgarage;


import static com.mygdx.gamedevgarage.utils.Utils.convertStringToList;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.screens.MainScreen;
import com.mygdx.gamedevgarage.screens.collection.CollectionScreen;
import com.mygdx.gamedevgarage.screens.game_event.EventPublisher;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.game_event.Observer;
import com.mygdx.gamedevgarage.screens.game_event.random.RandomEvent;
import com.mygdx.gamedevgarage.screens.game_event.random.RandomEventScreen;
import com.mygdx.gamedevgarage.screens.game_event.tax.GameOverScreen;
import com.mygdx.gamedevgarage.screens.game_event.tax.TaxEvent;
import com.mygdx.gamedevgarage.screens.mini_games.DesignScreen;
import com.mygdx.gamedevgarage.screens.mini_games.EndScreen;
import com.mygdx.gamedevgarage.screens.mini_games.GameDesignScreen;
import com.mygdx.gamedevgarage.screens.mini_games.PlatformScreen;
import com.mygdx.gamedevgarage.screens.mini_games.ProgrammingScreen;
import com.mygdx.gamedevgarage.screens.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;

import java.util.HashMap;
import java.util.HashSet;


public class Game extends com.badlogic.gdx.Game implements Observer {

    private MainScreen mainScreen;

    private HashSet<String> coverObjects;
    private HashSet<String> technologies;
    private HashSet<String> mechanics;
    private HashSet<String> platforms;
    private HashSet<GameObject> games;

    private boolean isGameOver = false;
    public boolean isGameStarted = false;
    public GameState gameState;
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

        initData();
        setMainScreen();
        getSavedGameState();

        isGameOver = dataManager.getGameOver();
        if(isGameOver){
            new Timer().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    setScreen(new GameOverScreen());
                }
            }, 0);
        }
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

        DataArrayFactory.initialise();

        for(GameObject gameObject : games){
            if(!gameObject.isSold()){
                mainScreen.startSellGame(gameObject);
            }
        }

        eventPublisher = new EventPublisher();
        eventPublisher.addObserver(this);

        TaxEvent.scheduleNextEvent(new TaxEvent());

        RandomEvent randomEvent = new RandomEvent("Up is opinion message manners correct hearing husband my. " +
                "Disposing commanded dashwoods cordially depending at at. " +
                "Its strangers who you certainty earnestly resources suffering she. " +
                "Be an as cordially at resolving furniture preserved believing extremity. " +
                "Easy mr pain felt in. Too northward affection additions nay. " +
                "He no an nature ye talent houses wisdom vanity denied." +
                "Efergegre wefwefwef e!");
        RandomEvent.scheduleNextEvent(randomEvent);
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

    public void setGameOverScreen() {
        setScreen(new GameOverScreen());
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

    public void setRandomEventScreen(RandomEvent event) {
        setScreen(new RandomEventScreen(event));
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

        TaxEvent.scheduleNextEvent(new TaxEvent());

        RandomEvent randomEvent = new RandomEvent("Up is opinion message manners correct hearing husband my. " +
                "Disposing commanded dashwoods cordially depending at at. " +
                "Its strangers who you certainty earnestly resources suffering she. " +
                "Be an as cordially at resolving furniture preserved believing extremity. " +
                "Easy mr pain felt in. Too northward affection additions nay. " +
                "He no an nature ye talent houses wisdom vanity denied." +
                "Efergegre wefwefwef e!");
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
        games.clear();
        Stats.setInstance(new Stats(1, 0, 5, 5, 5, 10));
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
