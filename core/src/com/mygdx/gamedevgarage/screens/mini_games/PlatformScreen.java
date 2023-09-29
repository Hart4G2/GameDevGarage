package com.mygdx.gamedevgarage.screens.mini_games;

import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.screens.mini_games.platform_actors.PlatformList;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

import java.util.List;

public class PlatformScreen implements Screen {

    private final I18NBundle bundle;
    private Stage stage;

    private Button okButton;
    private PlatformList platformsList;
    private StatsTable statsTable;

    public PlatformScreen() {
        bundle = Assets.getInstance().myBundle;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Game.getInstance().isScreenShowed = true;

        GameFactory.platform = "";

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = StatsTable.getInstance();

        List<CoverListItem> platforms = DataArrayFactory.createPlatforms();

        platformsList = new PlatformList(platforms);

        okButton = createTextButton(bundle.get("ok"), "white_18");
        okButton.setDisabled(true);

        Label headerLabel = createLabel(bundle.get("Choose_platform"), "white_20", false);

        Table table = new Table(Assets.getInstance().getSkin());
        table.setFillParent(true);
        table.add(headerLabel)
                .pad(getHeightPercent(.07f), 0, getHeightPercent(.003f), 0)
                .row();
        table.add(platformsList).width(getWidthPercent(1f)).height(getHeightPercent(.8f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .center().row();

        Stack stack = createBgStack("window_purple", table);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().setMainScreen();
                DialogThread.getInstance().setEndGameThread(true);
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
        Game.getInstance().isScreenShowed = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
