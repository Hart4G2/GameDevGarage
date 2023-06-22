package com.mygdx.gamedevgarage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private TextButton.TextButtonStyle buttonStyle;
    private TextButton buttonMakeGame;

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

        initButtonStyle();

        buttonMakeGame = new TextButton("Make a game", buttonStyle);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        buttonMakeGame.setPosition(Gdx.graphics.getWidth() / 2f, 0);
        stage.addActor(buttonMakeGame);
    }

    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);
        float buttonX = (width - buttonMakeGame.getWidth()) / 2f;
        float buttonY = (height - buttonMakeGame.getHeight()) / 25f ;
        buttonMakeGame.setPosition(buttonX, buttonY);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;

        cameraController.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        sceneManager.update(deltaTime);
        sceneManager.render();

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
    }

    private void initButtonStyle(){
        TextureAtlas mainButtonAtlas = assets.mainButtonAtlas;

        buttonStyle = new TextButton.TextButtonStyle();

        buttonStyle.font = new BitmapFont();

        buttonStyle.up = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-up"));
        buttonStyle.down = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-down"));
        buttonStyle.over = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-hover"));
    }
}