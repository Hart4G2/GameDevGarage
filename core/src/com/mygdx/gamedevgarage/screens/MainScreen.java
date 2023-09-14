package com.mygdx.gamedevgarage.screens;

import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.createProgressBar;
import static com.mygdx.gamedevgarage.utils.Utils.createStatsTable;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.main_actors.SellActor;
import com.mygdx.gamedevgarage.screens.main_actors.SellTable;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.data.GameObject;

public class MainScreen implements Screen {

    private final Game game;
    private final Assets assets;

    private PerspectiveCamera cam;
    private ModelBatch modelBatch;
    private final Array<ModelInstance> instances = new Array<>();
    private Environment environment;

    private Stage stage;
    private TextButton makeGameButton;
    private Button upgradeButton;
    private Button collectionButton;
    private ProgressBar gameMakingProgressBar;
    private StatsTable statsTable;
    private SellTable sellTable;
    private ScrollPane sellScrollPane;

    private AnimationController animationController;

    private boolean isGameInProgress = false;

    private DirectionalShadowLight shadowLight;
    private ModelBatch shadowBatch;


    public MainScreen() {
        this.game = Game.getInstance();
        this.assets = Assets.getInstance();

        createUIElements();
        setupUIListeners();
        createScene();
    }

    private void createScene(){
        modelBatch = new ModelBatch();
        modelBatch.getRenderContext().setCullFace(GL20.GL_BACK);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        environment.add((shadowLight = new DirectionalShadowLight(2048, 2048,
                60f, 60f, .1f, 20f))
                .set(1f, 1f, 1f, -.5f, -2f, -3.5f));
        environment.shadowMap = shadowLight;

        shadowBatch = new ModelBatch(new DepthShaderProvider());

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0.8f, 1.6f, 1.1f);
        cam.lookAt(-0.5f,0.9f,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        Model model = assets.model;

        for (int i = 0; i < model.nodes.size; i++) {
            String id = model.nodes.get(i).id;
            try {
                ModelInstance instance;
                if(id.equals("girl")){
                    instance = new ModelInstance(model, id, "Armature");
                    animationController = new AnimationController(instance);
                } else {
                    instance = new ModelInstance(model, id);
                }
                instances.add(instance);
            } catch (NullPointerException e){
                System.out.println("\n" + id);
                e.printStackTrace();
            }
        }
    }

    private void createUIElements(){
        makeGameButton = createTextButton("Make a game", "white_18");
        upgradeButton = createButton("store_button");
        collectionButton = createButton("collection_button");
        gameMakingProgressBar = createProgressBar(0, 100);

        upgradeButton.setPosition(getWidthPercent(0.05f), getHeightPercent(0.78f));
        upgradeButton.setSize(getWidthPercent(0.18f), getWidthPercent(0.18f));

        collectionButton.setPosition(getWidthPercent(0.05f), getHeightPercent(0.75f) - getWidthPercent(0.18f));
        collectionButton.setSize(getWidthPercent(0.18f), getWidthPercent(0.18f));

        makeGameButton.setPosition(getWidthPercent(0.1f), getHeightPercent(0.02f));
        makeGameButton.setSize(getWidthPercent(0.84f), getHeightPercent(0.15f));

        gameMakingProgressBar.setPosition(getWidthPercent(0.38f), getHeightPercent(0.58f));
        gameMakingProgressBar.setSize(getWidthPercent(0.3f), getHeightPercent(0.1f));

        sellTable = new SellTable();
        sellScrollPane = new ScrollPane(sellTable, assets.getSkin());
        sellScrollPane.setFadeScrollBars(false);

        sellScrollPane.setPosition(getWidthPercent(.45f), getHeightPercent(0.6f));
        sellScrollPane.setSize(getWidthPercent(0.5f), getHeightPercent(0.3f));
    }

    public void startSellGame(GameObject gameObject){
        SellActor sellActor = new SellActor(gameObject);
        sellActor.setName(String.valueOf(gameObject.getId()));
        sellTable.addSellActor(sellActor);
        sellActor.startSelling();
    }

    public void gameSold(GameObject gameObject){
        assets.setSound("game_sold");

        gameObject.setSold(true);
        game.setGameSold(gameObject);
        SellActor sellActor = sellTable.findActor(String.valueOf(gameObject.getId()));
        sellTable.removeActor(sellActor);
    }

    private void setupUIListeners() {
        makeGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DialogThread.getInstance().start();
            }
        });

        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setUpgradeScreen();
            }
        });

        collectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setCollectionScreen();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        statsTable = createStatsTable();

        stage.addActor(makeGameButton);
        stage.addActor(upgradeButton);
        stage.addActor(collectionButton);
        stage.addActor(gameMakingProgressBar);
        stage.addActor(sellScrollPane);
        stage.addActor(statsTable);

        resume();
    }

    float value = 0f;

    @Override
    public void render(float delta) {
        shadowLight.begin(Vector3.Zero, cam.direction);
        shadowBatch.begin(shadowLight.getCamera());
        shadowBatch.render(instances);
        shadowBatch.end();
        shadowLight.end();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        animationController.update(deltaTime);

        if(isGameInProgress) {
            if (Math.round(deltaTime) % 2 == 0) {
                value += 0.1;
            }

            gameMakingProgressBar.setValue(value);

            if (value >= 100) {
                value = 0;
            }
        }

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();

        statsTable.update();

        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        isGameInProgress = false;
    }

    @Override
    public void resume() {
        if(game.isGameStarted){
            isGameInProgress = true;
        }
    }

    @Override
    public void hide() {
        isGameInProgress = false;
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        stage.dispose();
    }

    public void setGameStarted(){
        game.isGameStarted = true;
        isGameInProgress = true;
        makeGameButton.setVisible(false);
        System.out.println("Game started");
        animationController.setAnimation("coding_loop", -1);
    }

    public void setGameEnded(){
        makeGameButton.setVisible(true);
        game.isGameStarted = false;
        isGameInProgress = false;
        System.out.println("Game ended");
        gameMakingProgressBar.setValue(0);
        animationController.current = null;
    }

    public void setGameCanceled(){
        makeGameButton.setVisible(true);
        game.isGameStarted = false;
        isGameInProgress = false;
        System.out.println("Game canceled");
        gameMakingProgressBar.setValue(0);
        animationController.current = null;
    }

    public Stage getStage() {
        return stage;
    }

    public void removeSellingGames() {
        sellTable.clear();
    }

    public void resumeSelling() {
        sellTable.stop();
    }

    public void stopSelling() {
        sellTable.stop();
    }
}