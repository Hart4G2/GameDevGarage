package com.mygdx.gamedevgarage.screens.mini_games;

import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createTable;
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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.end_actor.NumberActor;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;
import com.mygdx.gamedevgarage.utils.reward.Reward;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

public class EndScreen implements Screen {

    private final Game game;
    private final Reward reward;
    private final I18NBundle bundle;
    private final Skin skin;
    private Stage stage;

    private StatsTable statsTable;
    private TextButton okButton;

    public EndScreen() {
        game = Game.getInstance();
        reward = Reward.getInstance();
        bundle = Assets.getInstance().myBundle;
        skin = Assets.getInstance().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        game.isScreenShowed = true;

        if(!game.isEndScreenCalculated) {
            reward.calculateScores();
        }

        createUIElements();
        setupUIListeners();

        playSound("end_screen");
    }

    private void createUIElements(){
        statsTable = StatsTable.getInstance();

        okButton = createTextButton(bundle.get("ok"), "white_18");
        okButton.setDisabled(true);

        final boolean isSoundNeeded = Stats.getInstance().getStat(Currency.LEVEL) < reward.getLvl();

        okButton.addAction(
            Actions.sequence(
                Actions.delay(2.7f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        okButton.setDisabled(false);

                        if(isSoundNeeded) {
                            playSound("lvl_up");
                        }
                    }
                })
        ));

        NumberActor numberActor = new NumberActor();

        Table table = createTable(skin, true);
        table.add(numberActor).width(getWidthPercent(.9f)).height(getHeightPercent(.8f))
                .pad(getHeightPercent(.01f), getWidthPercent(.05f), getHeightPercent(.01f), getWidthPercent(.05f))
                .center().row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .center().row();

        Stack stack = createBgStack("window_lightblue", table);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    createGameObject();
                    game.getMainScreen().setGameEnded();
                    game.setMainScreen();
                }
            }
        });
    }

    private void createGameObject(){
        GameFactory.previousGenre = GameFactory.genre.getBundleKey();
        GameFactory.previousTheme = GameFactory.theme.getBundleKey();

        GameObject gameObject = GameFactory.createGameObject();
        game.addGame(gameObject);
        gameObject.startSelling();
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
        game.isEndScreenCalculated = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void playSound(String name){
        Assets.getInstance().setSound(name);
    }
}
