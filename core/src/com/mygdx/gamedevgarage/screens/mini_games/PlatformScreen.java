package com.mygdx.gamedevgarage.screens.mini_games;

import static com.mygdx.gamedevgarage.utils.Utils.createStatsTable;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.screens.mini_games.platform_actors.PlatformList;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameFactory;

import java.util.List;

public class PlatformScreen implements Screen {

    private Stage stage;

    private Button okButton;
    private PlatformList platformsList;
    private StatsTable statsTable;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        GameFactory.platform = "";

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        List<CoverListItem> platforms = DataArrayFactory.createPlatforms();

        platformsList = new PlatformList(platforms);

        okButton = createTextButton("OK", "white_18");
        okButton.setDisabled(true);

        statsTable = createStatsTable();

        Table table = new Table(Assets.getInstance().getSkin());
        table.setFillParent(true);
        table.add(platformsList).width(getWidthPercent(1f)).height(getHeightPercent(.8f))
                .row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .center().row();

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground("window_purple");
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().setMainScreen();
                DialogThread.getInstance().setEndGameThread();
            }
        });

        platformsList.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                okButton.setDisabled(GameFactory.platform.equals(""));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statsTable.update();

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
}
