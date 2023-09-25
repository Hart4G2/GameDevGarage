package com.mygdx.gamedevgarage.screens.mini_games;

import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.cover_actors.CoverMainActor;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

public class DesignScreen implements Screen {

    private Stage stage;

    private Button okButton;
    private StatsTable statsTable;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Game.getInstance().isScreenShowed = true;

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements() {
        statsTable = StatsTable.getInstance();
        statsTable.setValueLabelsStyle("black_18");
        statsTable.setHintLabelsStyle("default");

        CoverMainActor coverListActor = new CoverMainActor(this);

        Label headerLabel = Utils.createLabel("Create a game cover", "black_20", false);

        okButton = createTextButton("OK", "white_18");
        okButton.setDisabled(true);

        Table table = new Table(Assets.getInstance().getSkin());
        table.setFillParent(true);
        table.add(headerLabel)
                .pad(getHeightPercent(.1f), 0, getHeightPercent(.005f), 0)
                .center().row();
        table.add(coverListActor).width(getWidthPercent(1f)).height(getHeightPercent(.75f))
                .pad(0, 0, getHeightPercent(.005f), 0)
                .row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.05f))
                .center().row();

        Image bg = new Image(Assets.getInstance().getSkin().getDrawable("window_yellow"));
        bg.setScaling(Scaling.fill);
        Stack stack = new Stack(bg, table);
        stack.setFillParent(true);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners() {
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    Game.getInstance().setMainScreen();
                    DialogThread.getInstance().setProgrammingThread(true);
                }
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
        statsTable.setValueLabelsStyle("white_18");
        statsTable.setHintLabelsStyle("white_16");
        Gdx.input.setInputProcessor(null);
        Game.getInstance().isScreenShowed = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setSelectedCoverItems(String selectedColor, String selectedObject) {
        GameFactory.color = selectedColor;
        GameFactory.object = selectedObject;

        okButton.setDisabled(false);
    }
}