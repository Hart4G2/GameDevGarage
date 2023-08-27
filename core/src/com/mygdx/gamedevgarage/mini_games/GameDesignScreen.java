package com.mygdx.gamedevgarage.mini_games;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckList;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckListItem;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;

import java.util.ArrayList;

public class GameDesignScreen implements Screen, MiniGameScreen {

    private Game game;
    private Assets assets;
    private Skin skin;
    private Stage stage;

    private Button okButton;
    private Array<CheckListItem> mechanics;
    private CheckList mechanicList;
    private StatsTable statsTable;

    public GameDesignScreen(Game game) {
        this.game = game;
        this.assets = game.getAssets();
        this.skin = assets.getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        game.reward.mechanics = new ArrayList<>();
        mechanics = DataArrayFactory.createMechanics(game);

        mechanicList = new CheckList(mechanics, this, assets);

        ScrollPane scrollPane = new ScrollPane(mechanicList, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollbarsVisible(true);

        okButton = new TextButton("OK", skin, "white_18");
        okButton.setDisabled(true);

        Group group = new Group();
        group.addActor(scrollPane);
        group.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 1.15f);

        statsTable = new StatsTable(assets, game.stats);
        statsTable.setLabelsStyle("black_18");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(group).width(getWidthPercent(1f)).height(getHeightPercent(.78f))
                .pad(40, 0, 20, 0).row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .colspan(2).center().row();

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground("window_green");
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    game.setMainScreen();
                    DialogThread.getGameDesignThread().cancel();
                    new Timer().scheduleTask(DialogThread.getPlatformThread(), DialogThread.getGameDesignTime());
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mechanicList.render(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Действия при приостановке экрана (например, когда игра переходит в фоновый режим)
    }

    @Override
    public void resume() {
        // Действия при возобновлении экрана (например, когда игра возвращается из фонового режима)
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void addItem(String item) {
        game.reward.mechanics.add(item);
        okButton.setDisabled(false);
    }

    @Override
    public void removeItem(String item) {
        if(game.reward.mechanics.contains(item)){
            game.reward.mechanics.remove(item);
            if(game.reward.mechanics.size() == 0){
                okButton.setDisabled(true);
            }
        }
    }
}
