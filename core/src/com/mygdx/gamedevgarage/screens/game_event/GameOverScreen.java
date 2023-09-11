package com.mygdx.gamedevgarage.screens.game_event;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createStatsTable;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.List;

public class GameOverScreen implements Screen {

    private Stage stage;
    private StatsTable statsTable;

    private TextButton restartButton;
    private TextButton skipButton;

    private final boolean canSkipTax;

    public GameOverScreen() {
        canSkipTax = DataManager.getInstance().getSkipTax();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();

        DialogThread.getInstance().pause();
    }

    private void createUIElements(){
        statsTable = createStatsTable();

        String text = "You don't have enough money to pay taxes.";

        if(canSkipTax){
            text += "\nBut it's the first time, so you can skip taxes!";
        }

        Label header = createLabel(text, "white_18");

        restartButton = createTextButton("New game", "white_18");
        skipButton = createTextButton("Skip and continue", "white_18");
        skipButton.setDisabled(!canSkipTax);

        Table table = new Table(Assets.getInstance().getSkin());
        table.setFillParent(true);
        table.add(header).width(getWidthPercent(.9f)).height(getHeightPercent(.4f))
                .pad(getHeightPercent(.1f), getWidthPercent(.05f), getHeightPercent(.05f), getWidthPercent(.05f))
                .row();
        table.add(skipButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .row();
        table.add(restartButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .row();

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground("window_red");
    }

    private void setupUIListeners(){
        skipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!skipButton.isDisabled()){
                    Game.getInstance().setMainScreen();
                    DataManager.getInstance().setSkipTax(false);

                    Stats stats = Stats.getInstance();

                    int money = Stats.getInstance().getStat(Currency.MONEY);
                    stats.setMoney(money + 10);

                    List<StatsTable> tables = StatsTable.tables;
                    for (StatsTable table : tables) {
                        table.getProperty("money").setHint("Get bonus", "green_16");
                    }

                    DialogThread.getInstance().resume();
                }
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().restart();
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
