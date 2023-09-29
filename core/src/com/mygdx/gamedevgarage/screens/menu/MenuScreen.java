package com.mygdx.gamedevgarage.screens.menu;

import static com.mygdx.gamedevgarage.utils.Utils.convertStringToList;
import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createSlider;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.game_event.EventPublisher;
import com.mygdx.gamedevgarage.screens.game_event.random.RandomEvent;
import com.mygdx.gamedevgarage.screens.game_event.tax.TaxEvent;
import com.mygdx.gamedevgarage.screens.menu.actors.SelectActor;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;
import com.mygdx.gamedevgarage.utils.data.TranslatableObject;
import com.mygdx.gamedevgarage.utils.reward.Reward;

import java.util.HashMap;

public class MenuScreen implements Screen {

    private final Game game;
    private final DataManager dataManager;
    private final Assets assets;
    private Stage stage;

    private TextButton startButton;
    private TextButton settingsButton;
    private TextButton howToPlayButton;
    private Button backButton;
    private Slider volumeSlider;
    private Label volumeHeaderLabel;
    private Label volumeLabel;
    private Table mainMenuTable;
    private Table settingsTable;
    private SelectActor selectActor;

    public MenuScreen() {
        game = Game.getInstance();
        dataManager = DataManager.getInstance();
        assets = Assets.getInstance();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        I18NBundle bundle = assets.myBundle;

        startButton = createTextButton(bundle.get("Start"), "green_18");
        settingsButton = createTextButton(bundle.get("Settings"), "default");
        howToPlayButton = createTextButton(bundle.get("How_to_play"), "red_18");
        startButton.addAction(Actions.alpha(.8f));
        settingsButton.addAction(Actions.alpha(.8f));
        howToPlayButton.addAction(Actions.alpha(.8f));

        backButton = createButton("back_button");
        volumeSlider = createSlider(0, 1, 0.01f, getHeightPercent(.065f));
        volumeSlider.setValue(assets.getVolume());
        volumeHeaderLabel = createLabel(bundle.get("Volume"), "white_24", false);
        volumeLabel = createLabel(String.valueOf(Math.round(assets.getVolume() * 100)), "white_24", false);

        volumeSlider.addAction(Actions.alpha(.8f));
        volumeHeaderLabel.addAction(Actions.alpha(.8f));
        volumeLabel.addAction(Actions.alpha(.8f));
        selectActor = new SelectActor();

        mainMenuTable = new Table();
        mainMenuTable.setFillParent(true);
        mainMenuTable.add(startButton)
                .width(getWidthPercent(.5f)).height(getHeightPercent(.1f))
                .pad(getHeightPercent(.5f), 0, getHeightPercent(.03f), 0)
                .row();
        mainMenuTable.add(settingsButton)
                .width(getWidthPercent(.5f)).height(getHeightPercent(.1f))
                .padBottom(getHeightPercent(.03f))
                .row();
        mainMenuTable.add(howToPlayButton)
                .width(getWidthPercent(.5f)).height(getHeightPercent(.1f));

        settingsTable = new Table();
        settingsTable.setFillParent(true);
        settingsTable.add(backButton)
                .width(getWidthPercent(.13f)).height(getWidthPercent(.13f))
                .pad(getHeightPercent(.3f), 0, getHeightPercent(.03f), 0)
                .colspan(2).left().row();
        settingsTable.add(volumeHeaderLabel)
                .width(getWidthPercent(.4f))
                .padBottom(getHeightPercent(.01f))
                .colspan(2).center().row();
        settingsTable.add(volumeSlider)
                .width(getWidthPercent(.5f))
                .padRight(getWidthPercent(.03f)).padLeft(getWidthPercent(.02f))
                .left();
        settingsTable.add(volumeLabel)
                .width(getWidthPercent(.05f))
                .padBottom(getHeightPercent(.1f))
                .row();
        settingsTable.add(selectActor).width(getWidthPercent(.8f)).height(getHeightPercent(.1f))
                .width(getWidthPercent(.05f));
        settingsTable.setVisible(false);

        Stack mainStack = createBgStack("window_mainbg", settingsTable, mainMenuTable);

        stage.addActor(mainStack);

        fadeInTable(mainMenuTable, .5f);
    }

    private void setupUIListeners() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                start();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeInTable(mainMenuTable, .3f);
                fadeOutTable(settingsTable, .3f);
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeInTable(settingsTable, .3f);
                fadeOutTable(mainMenuTable, .3f);
            }
        });

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float value = volumeSlider.getValue();

                assets.setVolume(value);
                volumeLabel.setText(Math.round(value * 100));
            }
        });
        selectActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateBundle();
            }
        });
    }

    private void fadeInTable(final Table table, float duration){
        table.clearActions();
        table.addAction(Actions.sequence(
                Actions.alpha(0f, .0f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        table.setVisible(true);
                    }
                }),
                Actions.fadeIn(duration)
        ));
    }

    private void fadeOutTable(final Table table, float duration){
        table.clearActions();
        table.addAction(Actions.sequence(
                Actions.fadeOut(duration),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        table.setVisible(false);
                    }
                })
        ));
    }

    private void start(){
        game.needToSave = true;
        game.games = dataManager.getGames();

        game.setMainScreen();

        if(!getSavedData()) {
            game.coverObjects = DataArrayFactory.createDataObjectsSet();
            game.technologies = DataArrayFactory.createDataTechnologiesSet();
            game.mechanics = DataArrayFactory.createDataMechanicsSet();
            game.platforms = DataArrayFactory.createDataPlatformsSet();
        }

        DataArrayFactory.initialise();

        for(GameObject gameObject : game.games){
            if(!gameObject.isSold()){
                game.getMainScreen().startSellGame(gameObject);
            }
        }

        game.eventPublisher = new EventPublisher();
        game.eventPublisher.addObserver(game);

        TaxEvent.scheduleEvent(new TaxEvent());

        game.isRandomEventShownFromLastOpen = DataManager.getInstance().isRandomEventStarted();

        if(game.isRandomEventShownFromLastOpen){
            RandomEvent.delaySeconds = 0;
        }

        RandomEvent randomEvent = new RandomEvent();
        RandomEvent.scheduleNextEvent(randomEvent);

        getSavedGameState();

        game.isGameOver = dataManager.getGameOver();
        if(game.isGameOver){
            new Timer().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    game.setGameOverScreen();
                }
            }, 0);
        }
    }

    private void getSavedGameState(){
        game.isGameStarted = dataManager.isGameStarted();

        HashMap<String, String> gameData = dataManager.getGameData();

        GameFactory.previousTheme = gameData.get("previousTheme");
        GameFactory.previousGenre = gameData.get("previousGenre");

        if(game.isGameStarted){
            game.gameState = dataManager.getGameState();
            game.isScreenShowed = dataManager.isScreenShowed();
            game.isEndScreenCalculated = game.gameState == GameState.END && game.isScreenShowed;
            setGameState(gameData);
            game.getMainScreen().setGameStarted();
        }
    }

    private void setSavedGameData(HashMap<String, String> gameData) {
        GameFactory.name = gameData.get("name");
        GameFactory.genre = new TranslatableObject(gameData.get("genre"));
        GameFactory.theme = new TranslatableObject(gameData.get("theme"));
        GameFactory.color = gameData.get("color");
        GameFactory.object = gameData.get("object");
        GameFactory.technologies = convertStringToList(gameData.get("technologies"));
        GameFactory.mechanics = convertStringToList(gameData.get("mechanics"));
        GameFactory.platform = gameData.get("platform");

        if(Game.getInstance().gameState.equals(GameState.END) && game.isScreenShowed){
            Reward reward = Reward.getInstance();

            reward.setScore(Integer.parseInt(gameData.get("rewardScore")));
            reward.setDesign(Integer.parseInt(gameData.get("rewardDesign")));
            reward.setProgramming(Integer.parseInt(gameData.get("rewardProgramming")));
            reward.setGameDesign(Integer.parseInt(gameData.get("rewardGameDesign")));
            reward.setEnergy(Integer.parseInt(gameData.get("rewardEnergy")));
            reward.setExp(Integer.parseInt(gameData.get("rewardExp")));
            reward.setLvl(Integer.parseInt(gameData.get("rewardLvl")));
            reward.setSellTime(Integer.parseInt(gameData.get("rewardSellTime")));
            reward.setProfitMoney(Integer.parseInt(gameData.get("rewardProfitMoney")));
        }
    }

    private void setGameState(HashMap<String, String> gameData){
        setSavedGameData(gameData);

        DialogThread dialogThread = DialogThread.getInstance();

        switch (game.gameState){
            case MAIN: {
                dialogThread.start();
                break;
            }
            case DESIGN: {
                dialogThread.setDesignThread(!game.isScreenShowed);
                break;
            }
            case PROGRAMMING: {
                dialogThread.setProgrammingThread(!game.isScreenShowed);
                break;
            }
            case GAMEDESIGN: {
                dialogThread.setGameDesignThread(!game.isScreenShowed);
                break;
            }
            case PLATFORM: {
                dialogThread.setPlatformThread(!game.isScreenShowed);
                break;
            }
            case END: {
                dialogThread.setEndGameThread(!game.isScreenShowed);
                break;
            }
        }
    }

    private boolean getSavedData(){
        game.coverObjects = dataManager.getCovers();
        if(game.coverObjects == null || game.coverObjects.isEmpty()) return false;

        game.technologies = dataManager.getTechnologies();
        game.mechanics = dataManager.getMechanics();
        game.platforms = dataManager.getPlatforms();

        return true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void updateBundle(){
        I18NBundle bundle = Assets.getInstance().myBundle;
        startButton.setText(bundle.get("Start"));
        settingsButton.setText(bundle.get("Settings"));
        howToPlayButton.setText(bundle.get("How_to_play"));
        volumeHeaderLabel.setText(bundle.get("Volume"));
    }
}
