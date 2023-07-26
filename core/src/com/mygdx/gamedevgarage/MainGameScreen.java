package com.mygdx.gamedevgarage;

import static com.mygdx.gamedevgarage.DialogThread.isGameInProgress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

public class MainGameScreen implements Screen {

    private Game game;
    private Assets assets;

    private SceneManager sceneManager;
    private SceneAsset sceneAsset;
    private Scene scene;
    private PerspectiveCamera camera;
    private Cubemap diffuseCubemap;
    private Cubemap environmentCubemap;
    private Cubemap specularCubemap;
    private Texture brdfLUT;
    private float time;
    private DirectionalShadowLight light;
    private ShadowMap shadowMap;

    private Stage stage;
    private TextButton buttonMakeGame;
    private ProgressBar progressBarGameMaking;
    private Label labelDesign;
    private Label labelProgramming;
    private Label labelGameDesign;
    private Label labelExperience;
    private Label labelMoney;
    private Label labelLevel;

    public MainGameScreen(Game game) {

        this.game = game;
        this.assets = game.getAssets();

        // create scene
        sceneAsset = new GLTFLoader().load(Gdx.files.internal("models/mobile_game_background.gltf"));
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        scene = new Scene(sceneAsset.scene);
        sceneManager = new SceneManager();
        sceneManager.addScene(scene);

        // setup camera (The BoomBox model is very small so you may need to adapt camera settings for your scene)
        camera = new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 0.02f;
        camera.far = 10f;
        camera.position.set(0.8f, 1.7f, 1.2f);
        camera.lookAt(-0.5f,0.9f,0);
        sceneManager.setCamera(camera);

        // setup light
        light = new DirectionalShadowLight(1024, 1024, 10f, 10f, 1f, 20f);
        light.set(0.8f, 0.8f, 0.8f, -.4f, -.4f, -.4f);
        sceneManager.environment.add(light);

        // setup quick IBL (image based lighting)
        IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);
        environmentCubemap = iblBuilder.buildEnvMap(1024);
        diffuseCubemap = iblBuilder.buildIrradianceMap(256);
        specularCubemap = iblBuilder.buildRadianceMap(10);
        iblBuilder.dispose();

        // This texture is provided by the library, no need to have it in your assets.
        brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));

        sceneManager.setAmbientLight(1f);
        sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));
        sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));
        sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));
    }

    @Override
    public void show() {
        Skin skin = assets.getSkin();

        scene.animationController.setAnimation("coding_loop.001", -1);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        buttonMakeGame = new TextButton("Make a game", skin, "white_18");

        buttonMakeGame.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() / 7f);
        buttonMakeGame.setPosition(Gdx.graphics.getWidth() / 2f, 0);
        stage.addActor(buttonMakeGame);

        progressBarGameMaking = new ProgressBar(0, 100, 1, false, skin, "default");

        progressBarGameMaking.setPosition(Gdx.graphics.getWidth() / 2f - Gdx.graphics.getWidth() / 10f, 550);
        stage.addActor(progressBarGameMaking);

        Table statsTable = new Table();
        statsTable.setFillParent(true);
        statsTable.top().padTop(10f);

        labelDesign = new Label("design: " + 1, skin, "white_16");
        labelProgramming = new Label("programming: " + 1, skin, "white_16");
        labelGameDesign = new Label("game design: " + 1, skin, "white_16");
        labelExperience = new Label("exp: " + 1, skin, "white_16");
        labelMoney = new Label("money: " + 1, skin, "white_16");
        labelLevel = new Label("level: " + 1, skin, "white_16");

        statsTable.add(labelLevel).padRight(20f);
        statsTable.add(labelDesign).padRight(20f);
        statsTable.add(labelProgramming).padRight(20f);
        statsTable.add(labelGameDesign).padRight(20f);
        statsTable.add(labelExperience).padRight(20f);
        statsTable.add(labelMoney);

        stage.addActor(statsTable);

        buttonMakeGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonMakeGameClicked();
            }
        });
    }

    float value = 0f;

    @Override
    public void render(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        sceneManager.update(deltaTime);
        sceneManager.render();

        if(isGameInProgress) {

            if (Math.round(deltaTime) % 2 == 0) {
                value += 0.1;
            }

            progressBarGameMaking.setValue(value);

            if (value >= 100) {
                value = 0;
            }
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);
        float buttonX = (width - buttonMakeGame.getWidth()) / 2f;
        float buttonY = (height - buttonMakeGame.getHeight()) / 25f ;
        buttonMakeGame.setPosition(buttonX, buttonY);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        sceneManager.dispose();
        sceneAsset.dispose();
        environmentCubemap.dispose();
        diffuseCubemap.dispose();
        specularCubemap.dispose();
        brdfLUT.dispose();
        stage.dispose();
        assets.dispose();
    }

    public void buttonMakeGameClicked() {
        DialogThread dialogThread = new DialogThread(game,
                stage,
                assets,
                10000,
                0.4,
                0.4,
                0.2);

        dialogThread.start();
    }
}