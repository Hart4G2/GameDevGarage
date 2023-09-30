package com.mygdx.gamedevgarage.screens.game_event.random;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.data.events.Event;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

public class RandomEventScreen implements Screen {

    private final Game game;
    private final Skin skin;
    private final I18NBundle bundle;
    private Stage stage;
    private StatsTable statsTable;
    private RandomEvent randomEvent;

    private TextButton confirmButton;
    private TextButton rejectButton;

    public RandomEventScreen() {
        game = Game.getInstance();
        skin = Assets.getInstance().getSkin();
        bundle = Assets.getInstance().myBundle;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        game.stopAllThreads();

        game.isRandomEventShown = true;

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = StatsTable.getInstance();

        Event event = randomEvent.getEvent();

        Image image = new Image(event.getDrawable());

        Label label = createLabel(event.getText(), "white_24", true);

        confirmButton = createTextButton(bundle.get("Confirm"), "white_18");
        rejectButton = createTextButton(bundle.get("Reject"), "white_18");

        Table table = createTable(skin, true);
        table.add(image).width(getHeightPercent(.4f)).height(getHeightPercent(.4f))
                .padTop(getHeightPercent(.1f)).padBottom(getHeightPercent(.01f))
                .colspan(2).center().row();
        table.add(label).width(getWidthPercent(.85f)).height(getHeightPercent(.3f))
                .padBottom(getHeightPercent(.03f))
                .colspan(2).center().row();
        table.add(confirmButton).width(getWidthPercent(.4f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f));
        table.add(rejectButton).width(getWidthPercent(.4f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f));

        boolean canConfirm = Stats.getInstance().isEnough(event.getConfirmCost());
        confirmButton.setDisabled(!canConfirm);

        Stack stack = createBgStack("window_darkblue", table);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners(){
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainScreen();
                RandomEventScreen.this.randomEvent.confirm();
                game.resumeAllThreads();
            }
        });
        rejectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainScreen();
                RandomEventScreen.this.randomEvent.reject();
                game.resumeAllThreads();
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
        game.isRandomEventShown = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setRandomEvent(RandomEvent randomEvent) {
        this.randomEvent = randomEvent;
    }
}
