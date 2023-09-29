package com.mygdx.gamedevgarage.screens;

import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.main_actors.SellActor;
import com.mygdx.gamedevgarage.screens.main_actors.SellTable;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

import java.util.Random;

public class MainScreen implements Screen {

    private final Game game;
    private final Assets assets;
    private final I18NBundle bundle;

    private PerspectiveCamera cam;
    private ModelBatch modelBatch;
    private final Array<ModelInstance> instances = new Array<>();
    private Environment environment;

    private Stage stage;
    private TextButton makeGameButton;
    private Button upgradeButton;
    private Button collectionButton;
    private Label talkLabel;
    private StatsTable statsTable;
    private SellTable sellTable;
    private ScrollPane sellScrollPane;

    private AnimationController animationController;

    private DirectionalShadowLight shadowLight;
    private ModelBatch shadowBatch;

    public MainScreen() {
        game = Game.getInstance();
        assets = Assets.getInstance();
        bundle = assets.myBundle;

        createUIElements();
        setupUIListeners();
        createScene();

        talkTimer.scheduleTask(talkTask, 10, 10, -1);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(makeGameButton);
        stage.addActor(upgradeButton);
        stage.addActor(collectionButton);
        stage.addActor(talkLabel);
        stage.addActor(sellScrollPane);
        stage.addActor(statsTable);

        resume();
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
        statsTable = StatsTable.getInstance();

        makeGameButton = createTextButton(bundle.get("Make_a_game"), "white_18");
        upgradeButton = createButton("store_button");
        collectionButton = createButton("collection_button");
        talkLabel = createLabel("", "white_18", true);
        talkLabel.setAlignment(Align.center);
        talkLabel.setVisible(false);

        upgradeButton.setPosition(getWidthPercent(.05f), getHeightPercent(.78f));
        upgradeButton.setSize(getWidthPercent(.18f), getWidthPercent(.18f));

        collectionButton.setPosition(getWidthPercent(.05f), getHeightPercent(.75f) - getWidthPercent(.18f));
        collectionButton.setSize(getWidthPercent(.18f), getWidthPercent(.18f));

        makeGameButton.setPosition(getWidthPercent(.1f), getHeightPercent(.02f));
        makeGameButton.setSize(getWidthPercent(.84f), getHeightPercent(.15f));

        talkLabel.setPosition(getWidthPercent(.3f), getHeightPercent(.55f));
        talkLabel.setSize(getWidthPercent(.5f), getHeightPercent(.15f));

        sellTable = new SellTable();
        sellScrollPane = new ScrollPane(sellTable, assets.getSkin());
        sellScrollPane.setFadeScrollBars(false);

        sellScrollPane.setPosition(getWidthPercent(.45f), getHeightPercent(.6f));
        sellScrollPane.setSize(getWidthPercent(.5f), getHeightPercent(.3f));
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
        talkTimer.stop();
    }

    @Override
    public void resume() {
        talkTimer.start();
    }

    @Override
    public void hide() {
        talkTimer.stop();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        stage.dispose();
    }

    public void setGameStarted(){
        game.isGameStarted = true;
        makeGameButton.setVisible(false);
        animationController.setAnimation("coding_loop", -1);
    }

    public void setGameEnded(){
        makeGameButton.setVisible(true);
        game.isGameStarted = false;
        System.out.println("Game ended");
        animationController.current = null;
    }

    public void setGameCanceled(){
        makeGameButton.setVisible(true);
        game.isGameStarted = false;
        System.out.println("Game canceled");
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

    boolean dialogOpened = false;

    public void setDialogOpened(boolean value){
        dialogOpened = value;
    }

    public boolean isDialogClosed(){
        return !dialogOpened;
    }


    private final Timer talkTimer = new Timer();
    private String previousPhrase;

    Timer.Task talkTask = new Timer.Task() {
        @Override
        public void run() {
            String[] hints = DataArrayFactory.talkHints;
            int r = new Random().nextInt(hints.length);
            String hint = hints[r];

            while(hint.equals(previousPhrase)){
                r = new Random().nextInt(hints.length);
                hint = hints[r];
            }

            previousPhrase = hint;
            setHintTalk(hint);
        }
    };

    private void setHintTalk(final String hint){
        talkLabel.addAction(Actions.sequence(
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        talkLabel.setText(hint);
                        talkLabel.addAction(Actions.alpha(0f, .001f));
                        talkLabel.setVisible(true);
                    }
                }),
                Actions.delay(.01f),
                Actions.fadeIn(.2f),
                Actions.delay(3f),
                Actions.fadeOut(.3f),
                Actions.delay(.1f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        talkLabel.setVisible(false);
                    }
                })
        ));
    }
}