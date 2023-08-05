package com.mygdx.gamedevgarage.mini_games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.cover_actors.CoverMainActor;

public class DesignMiniGameScreen implements Screen {

    private Game game;
    private Assets assets;
    private Skin skin;

    private Stage stage;

    private Button okButton;

    private String selectedColor;
    private String selectedObject;

    public DesignMiniGameScreen(Game game) {
        this.game = game;
        this.assets = game.getAssets();
        this.skin = assets.getSkin();
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();
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
        // Действия при приостановке экрана (например, когда игра переходит в фоновый режим)
    }

    @Override
    public void resume() {
        // Действия при возобновлении экрана (например, когда игра возвращается из фонового режима)
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void createUIElements() {
        CoverMainActor coverListActor = new CoverMainActor(this, assets);

        okButton = new TextButton("OK", skin, "white_18");
        okButton.setDisabled(true);

        Table table = new Table();
        table.setFillParent(true);
        table.add(coverListActor).row();
        table.add(okButton).colspan(2).center().row();

        stage.addActor(table);

        table.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("atlases/styles/window_yellow.png"))));
    }

    private void setupUIListeners() {
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    System.out.println("Selected Color: " + selectedColor);
                    System.out.println("Selected Object: " + selectedObject);

                    game.setMainScreen();
                    DialogThread.getDesignThread().cancel();
                    new Timer().scheduleTask(DialogThread.getProgrammingThread(), DialogThread.getDesignTime());
                }
            }
        });
    }

    public void setSelectedCoverItems(String selectedColor, String selectedObject) {
        this.selectedColor = selectedColor;
        this.selectedObject = selectedObject;

        okButton.setDisabled(false);
    }
}