package com.mygdx.gamedevgarage.screens.game_event.random;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createStatsTable;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.stats.StatsTable;

public class RandomEventScreen implements Screen {

    private final Game game;
    private final Skin skin;
    private Stage stage;
    private StatsTable statsTable;
    private final RandomEvent event;

    private Image image;
    private Label label;
    private TextButton confirmButton;
    private TextButton rejectButton;

    public RandomEventScreen(RandomEvent event) {
        game = Game.getInstance();
        skin = Assets.getInstance().getSkin();
        this.event = event;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        game.stopAllThreads();

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = createStatsTable();

        String text = event.getText();

        image = new Image(new Texture(Gdx.files.internal("event_debug.png")));

        label = createLabel(text, "white_24", true);

        confirmButton = createTextButton("Confirm", "white_18");
        rejectButton = createTextButton("Reject", "white_18");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(image).width(getWidthPercent(.9f)).height(getHeightPercent(.3f))
                .pad(getHeightPercent(.1f), getWidthPercent(.05f), getHeightPercent(.03f), getWidthPercent(.05f))
                .colspan(2).center().row();
        table.add(label).width(getWidthPercent(.85f)).height(getHeightPercent(.4f))
                .padBottom(getHeightPercent(.03f))
                .colspan(2).center().row();
        table.add(confirmButton).width(getWidthPercent(.4f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f));
        table.add(rejectButton).width(getWidthPercent(.4f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f));

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground("window_purple");
    }

    private void setupUIListeners(){
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainScreen();
                RandomEventScreen.this.event.confirm();
                game.resumeAllThreads();
            }
        });
        rejectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainScreen();
                RandomEventScreen.this.event.reject();
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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
