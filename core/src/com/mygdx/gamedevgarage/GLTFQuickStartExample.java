package com.mygdx.gamedevgarage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

public class GLTFQuickStartExample extends ApplicationAdapter
{
    private SceneManager sceneManager;
    private SceneAsset sceneAsset;
    private Assets assets;
    private Scene scene;
    private PerspectiveCamera camera;
    private Cubemap diffuseCubemap;
    private Cubemap environmentCubemap;
    private Cubemap specularCubemap;
    private Texture brdfLUT;
    private float time;
    private DirectionalShadowLight light;
    private ShadowMap shadowMap;
    private FirstPersonCameraController cameraController;

    private Stage stage;
    private TextButton buttonMakeGame;
    private ProgressBar progressBarGameMaking;
    private Label labelDesign;
    private Label labelProgramming;
    private Label labelGameDesign;
    private Label labelExperience;
    private Label labelMoney;
    private Label labelLevel;

    @Override
    public void create() {

        assets = new Assets();

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

        cameraController = new FirstPersonCameraController(camera);
        Gdx.input.setInputProcessor(cameraController);

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

        scene.animationController.setAnimation("coding_loop.001", -1);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        buttonMakeGame = new TextButton("Make a game", assets.skin, "white_18");

        buttonMakeGame.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() / 7f);
        buttonMakeGame.setPosition(Gdx.graphics.getWidth() / 2f, 0);
        stage.addActor(buttonMakeGame);

        progressBarGameMaking = new ProgressBar(0, 100, 1, false, assets.skin, "default");

        progressBarGameMaking.setPosition(Gdx.graphics.getWidth() / 2f - Gdx.graphics.getWidth() / 10f, 550);
        stage.addActor(progressBarGameMaking);

        Table statsTable = new Table();
        statsTable.setFillParent(true);
        statsTable.top().padTop(10f);

        labelDesign = new Label("design: " + 1, assets.skin, "white_16");
        labelProgramming = new Label("programming: " + 1, assets.skin, "white_16");
        labelGameDesign = new Label("game design: " + 1, assets.skin, "white_16");
        labelExperience = new Label("exp: " + 1, assets.skin, "white_16");
        labelMoney = new Label("money: " + 1, assets.skin, "white_16");
        labelLevel = new Label("level: " + 1, assets.skin, "white_16");

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

    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);
        float buttonX = (width - buttonMakeGame.getWidth()) / 2f;
        float buttonY = (height - buttonMakeGame.getHeight()) / 25f ;
        buttonMakeGame.setPosition(buttonX, buttonY);
    }

    float value = 0f;
    boolean isGameInProcess = false;

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;

        cameraController.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        sceneManager.update(deltaTime);
        sceneManager.render();

        if(isGameInProcess) {

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
        final Dialog dialog = new Dialog("", assets.skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.setFillParent(true);

        final TextField nameTextField = new TextField("", assets.skin, "white_24");

        final SelectBox<String> genreSelectBox = new SelectBox<>(assets.skin);
        genreSelectBox.setItems("Shooter", "Strategy", "Arcade");

        final SelectBox<String> platformSelectBox = new SelectBox<>(assets.skin);
        platformSelectBox.setItems("PC", "PlayStation", "Xbox", "Nintendo Switch");

        final SelectBox<String> levelSelectBox = new SelectBox<>(assets.skin);
        levelSelectBox.setItems("Flash game", "AA game", "AAA game");

        Label headerLabel = new Label("Create Game", assets.skin, "default_32");
        Label nameLabel = new Label("Name:", assets.skin, "default_24");
        Label genreLabel = new Label("Genre:", assets.skin, "default_24");
        Label platformLabel = new Label("Platform:", assets.skin, "default_24");
        Label levelLabel = new Label("Level:", assets.skin, "default_24");

        dialog.getContentTable().add(headerLabel).align(Align.center).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(nameLabel).padRight(10f);
        dialog.getContentTable().add(nameTextField).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(genreLabel).padRight(10f);
        dialog.getContentTable().add(genreSelectBox).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(platformLabel).padRight(10f);
        dialog.getContentTable().add(platformSelectBox).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(levelLabel).padRight(10f);
        dialog.getContentTable().add(levelSelectBox).row();
        dialog.getContentTable().row();

        TextButton okButton = new TextButton("OK", assets.skin, "white_18");
        TextButton cancelButton = new TextButton("Cancel", assets.skin, "white_18");

        okButton.setSize(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 30f);
        cancelButton.setSize(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 30f);

        dialog.getButtonTable().add(okButton);
        dialog.getButtonTable().add(cancelButton);

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = nameTextField.getText();
                String genre = genreSelectBox.getSelected();
                String platform = platformSelectBox.getSelected();
                String level = levelSelectBox.getSelected();

                isGameInProcess = true;
                dialog.hide();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.show(stage);
    }
}