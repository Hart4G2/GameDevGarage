package com.mygdx.gamedevgarage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;

public class MainScreen implements Screen {

    private final Game game;
    private final Stage stage;
    public ModelBatch modelBatch;
    private PerspectiveCamera cam;

    public Array<ModelInstance> objectInstances = new Array<>();
    public boolean loading;
    private float elapsedTime;
    private Skin tableSkin;
    private TextButtonStyle buttonStyle;


    public MainScreen(Game game) {
        this.game = game;
        modelBatch = new ModelBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0, 10);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 1000f;
        cam.update();
        stage.getViewport().setCamera(cam);

        initButtonStyle();
        initTableSkin();

        Table table = new Table(tableSkin);

        TextButton btnContinue = new TextButton("Make a game", buttonStyle);
        table.add(btnContinue).pad(10).row();

        table.setPosition(Gdx.graphics.getWidth() / 2f, btnContinue.getHeight());

//        Image bgImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("atlases/background.png"))));

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        modelBatch.render(objectInstances);

        modelBatch.begin(cam);
        elapsedTime += delta;
        modelBatch.render(objectInstances);
        modelBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        modelBatch.dispose();
        stage.dispose();
        tableSkin.dispose();
        modelBatch.dispose();
        objectInstances.clear();
    }

    private void initButtonStyle(){
        TextureAtlas mainButtonAtlas = game.getAssets().mainButtonAtlas;

        buttonStyle = new TextButton.TextButtonStyle();

        buttonStyle.font = new BitmapFont();

        buttonStyle.up = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-up"));
        buttonStyle.down = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-down"));
        buttonStyle.over = new TextureRegionDrawable(mainButtonAtlas.findRegion("button-hover"));
    }

    private void initTableSkin(){
        tableSkin = new Skin();

        Label.LabelStyle defaultLabelStyle = new Label.LabelStyle();

        defaultLabelStyle.font = new BitmapFont();

        tableSkin.add("default", defaultLabelStyle);
    }
}
