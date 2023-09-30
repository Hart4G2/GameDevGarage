package com.mygdx.gamedevgarage.screens.game_event.tax;

import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
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
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.stats.Cost;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

public class GameOverScreen implements Screen {

    private final Game game;
    private final I18NBundle bundle;
    private final Skin skin;
    private Stage stage;
    private StatsTable statsTable;

    private TextButton restartButton;
    private TextButton adButton;
    private TextButton skipButton;
    private Label loadingLabel;

    private boolean canSkipTax;

    public GameOverScreen() {
        game = Game.getInstance();
        bundle = Assets.getInstance().myBundle;
        skin = Assets.getInstance().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        canSkipTax = DataManager.getInstance().getSkipTax();

        game.stopAllThreads();
        game.setGameOver(true);

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = StatsTable.getInstance();

        String text = bundle.get("You_don't_have_enough_money_to_pay_taxes.");

        if(canSkipTax){
            text += bundle.get("But_it's_the_first_time,_so_you_can_skip_taxes!");
        } else {
            text += bundle.get("You_already_used_skipping_taxes.");
        }

        Label header = createLabel(text, "white_24", true);
        header.setAlignment(Align.center);

        restartButton = createTextButton(bundle.get("New_game"), "red_18");
        adButton = createTextButton(bundle.get("Skip_with_ad"), "white_18");
        skipButton = createTextButton(bundle.get("Skip_and_continue"), "green_18");
        skipButton.setDisabled(!canSkipTax);
        adButton.setDisabled(canSkipTax);

        loadingLabel = createLabel(bundle.get("Loading"), "white_18", false);
        loadingLabel.setAlignment(Align.center);
        loadingLabel.setVisible(false);

        Table table = createTable(skin, true);
        table.add(header).width(getWidthPercent(.8f)).height(getHeightPercent(.2f))
                .pad(getHeightPercent(.1f), getWidthPercent(.05f), getHeightPercent(.1f), getWidthPercent(.05f)).row();
        table.add(loadingLabel).width(getWidthPercent(.5f)).height(getHeightPercent(.05f))
                .padBottom(getHeightPercent(.03f))
                .row();
        table.add(adButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .row();
        table.add(skipButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .row();
        table.add(restartButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .row();

        Stack stack = createBgStack("window_red", table);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners(){
        skipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!skipButton.isDisabled()){
                    game.setMainScreen();
                    DataManager.getInstance().setSkipTax(false);

                    setBonus(bundle.get("Bonus"), 10);

                    game.resumeAllThreads();
                    game.setGameOver(false);
                }
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.restart();
                game.restartAllThreads();
            }
        });
        adButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!adButton.isDisabled()) {
                    game.getAdHandler().showRewardAd("gameOver");
                    if(!loadingLabel.hasActions())
                        startAnimation();
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void setBonus(String text, int value) {
        Cost bonus = new Cost(
                new Currency[]{Currency.MONEY},
                new int[]{-value}
        );

        Stats.getInstance().pay(bonus);

        StatsTable.setHint(Currency.MONEY, text, "default");
    }

    public void setCanSkipTax(boolean canSkipTax) {
        stopAnimation();
        if(!canSkipTax) {
            loadingLabel.setVisible(true);
            loadingLabel.setText(bundle.get("There_was_a_problem"));
        } else {
            DataManager.getInstance().setSkipTax(true);
            this.canSkipTax = true;
            adButton.setDisabled(true);
            skipButton.setDisabled(false);
            setBonus(bundle.get("TY_for_ad"), 10);
        }
    }

    public void stopAnimation(){
        loadingLabel.setVisible(false);
        loadingLabel.clearActions();
    }

    public void startAnimation(){
        loadingLabel.setVisible(true);
        loadingLabel.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingLabel.setText(bundle.get("Loading"));
                                    }
                                }),
                                dotSequenceAction(),
                                dotSequenceAction(),
                                dotSequenceAction()
                        )
                )
        );
    }

    private SequenceAction dotSequenceAction(){
        return Actions.sequence(
                Actions.delay(.2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        loadingLabel.setText(loadingLabel.getText() + ".");
                    }
                })
        );
    }
}
