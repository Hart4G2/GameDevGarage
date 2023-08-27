package com.mygdx.gamedevgarage.mini_games;

import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.end_actor.NumberActor;
import com.mygdx.gamedevgarage.mini_games.reward.Reward;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.data.GameObject;

public class EndScreen implements Screen {

    private Game game;
    private Assets assets;
    private Skin skin;
    private Stage stage;

    private StatsTable statsTable;
    private NumberActor numberActor;
    private TextButton okButton;

    public EndScreen(Game game) {
        this.game = game;
        assets = game.getAssets();
        skin = assets.getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = new StatsTable(assets, game.stats);

        okButton = createTextButton("OK", skin, "white_18");
        okButton.setDisabled(true);

        game.reward.calculateScores();
        numberActor = new NumberActor(game);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(numberActor).width(getWidthPercent(.9f)).height(getHeightPercent(.8f))
                .pad(getHeightPercent(.01f), getWidthPercent(.05f), getHeightPercent(.01f), getWidthPercent(.05f))
                .center().row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .center().row();

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground("window_lightblue");

        okButton.addAction(
                Actions.sequence(
                        Actions.delay(2.5f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        okButton.setDisabled(false);
                                    }
                                }
                        )
                ));
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    createGameObject();
                    game.setMainScreen();
                }
            }
        });
    }

    private void createGameObject(){
        Reward reward = game.reward;

        String name = reward.name;
        String color = reward.color;
        String object = reward.object;
        String[] technologies = reward.technologies.toArray(new String[0]);
        String[] mechanics = reward.mechanics.toArray(new String[0]);
        String platform = reward.platform;
        int score = reward.getScore();
        int profitMoney = reward.getProfitMoney();
        float sellTime = reward.getSellTime();

        GameObject gameObject = new GameObject(game, name, color, object, technologies,
                mechanics, platform, score, profitMoney, sellTime);
        game.addGame(gameObject);
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
