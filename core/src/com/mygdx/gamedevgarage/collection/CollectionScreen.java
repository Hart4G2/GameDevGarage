package com.mygdx.gamedevgarage.collection;

import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.createStatsTable;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.collection.actors.GameList;
import com.mygdx.gamedevgarage.stats.StatsTable;

public class CollectionScreen implements Screen {

    private final Game game;
    private final Skin skin;
    private Stage stage;

    private StatsTable statsTable;
    private Button backButton;

    public CollectionScreen(Game game) {
        this.game = game;
        skin = game.getAssets().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = createStatsTable(game);

        GameList gameList = new GameList(game);
        ScrollPane gamesScrollPane = new ScrollPane(gameList, skin);
        gamesScrollPane.setFillParent(true);
        gamesScrollPane.setScrollbarsVisible(true);

        Group scrollPaneContainer = new Group();
        scrollPaneContainer.addActor(gamesScrollPane);
        scrollPaneContainer.setSize(getWidthPercent(.95f), getHeightPercent(0.7f));

        backButton = createButton(skin, "back_button");

        Table table = new Table();
        table.setFillParent(true);
        table.add(backButton).width(getWidthPercent(.15f)).height(getWidthPercent(.15f))
                .left().row();
        table.add(scrollPaneContainer)
                .center();

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground(skin.getDrawable("window_purple"));
    }

    private void setupUIListeners(){
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainScreen();
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
