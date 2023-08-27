package com.mygdx.gamedevgarage.mini_games;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.cover_actors.CoverMainActor;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.Utils;

public class DesignScreen implements Screen {

    private Game game;
    private Assets assets;
    private Skin skin;
    private Stage stage;

    private Button okButton;
    private StatsTable statsTable;

    public DesignScreen(Game game) {
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
        CoverMainActor coverListActor = new CoverMainActor(game, this);

        okButton = Utils.createTextButton("OK", skin, "white_18");
        okButton.setDisabled(true);

        statsTable = new StatsTable(assets, game.stats);
        statsTable.setLabelsStyle("black_18");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(coverListActor).width(getWidthPercent(1f)).height(getHeightPercent(.78f))
                .pad(getHeightPercent(.03f), getHeightPercent(.1f), getHeightPercent(.02f), getHeightPercent(.1f))
                .row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.006f))
                .center().row();

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground("window_yellow");
    }

    private void setupUIListeners() {
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    game.setMainScreen();
                    DialogThread.getDesignThread().cancel();
                    new Timer().scheduleTask(DialogThread.getProgrammingThread(), DialogThread.getDesignTime());
                }
            }
        });
    }

    public void setSelectedCoverItems(String selectedColor, String selectedObject) {
        game.reward.color = selectedColor;
        game.reward.object = selectedObject;

        okButton.setDisabled(false);
    }
}