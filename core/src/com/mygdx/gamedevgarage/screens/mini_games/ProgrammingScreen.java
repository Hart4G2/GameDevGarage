package com.mygdx.gamedevgarage.screens.mini_games;

import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.selection_actors.CheckList;
import com.mygdx.gamedevgarage.screens.mini_games.selection_actors.CheckListItem;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.utils.data.CheckObject;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

import java.util.ArrayList;
import java.util.List;

public class ProgrammingScreen implements Screen {

    private final Skin skin;
    private final I18NBundle bundle;
    private Stage stage;

    private Label headerLabel;
    private Button okButton;
    private CheckList techList;
    private StatsTable statsTable;

    private String genre;

    public ProgrammingScreen() {
        skin = Assets.getInstance().getSkin();
        bundle = Assets.getInstance().myBundle;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Game.getInstance().isScreenShowed = true;

        genre = GameFactory.genre.getText();

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = StatsTable.getInstance();

        GameFactory.technologies = new ArrayList<>();
        List<CheckListItem> technologies = DataArrayFactory.createTechnologies();

        techList = new CheckList(technologies);

        ScrollPane scrollPane = new ScrollPane(techList, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollbarsVisible(true);

        headerLabel = createLabel(bundle.get("Choose_technologies_for") + genre + " (0 / 3)", "white_20", false);

        okButton = createTextButton("OK", "white_18");
        okButton.setDisabled(true);

        Group group = new Group();
        group.addActor(scrollPane);
        group.setSize(getWidthPercent(1f), getHeightPercent(0.86f));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(headerLabel)
                .pad(getHeightPercent(.07f), 0, getHeightPercent(.003f), 0)
                .colspan(2).center()
                .row();
        table.add(group).width(getWidthPercent(1f)).height(getHeightPercent(.78f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .row();
        table.add(okButton).width(getWidthPercent(.5f)).height(getHeightPercent(.08f))
                .colspan(2).center()
                .row();

        Stack stack = createBgStack("window_blue", table);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    Game.getInstance().setMainScreen();
                    DialogThread.getInstance().setGameDesignThread(true);
                    for(CheckObject object : techList.getSelectedItems()){
                        GameFactory.technologies.add(object.getBundleKey());
                    }
                }
            }
        });

        techList.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                okButton.setDisabled(techList.getSelectedItems().size() == 0);
                setSelectedCount();
            }
        });
    }

    private void setSelectedCount(){
        headerLabel.setText(bundle.get("Choose_technologies_for") + genre + " (" + techList.getSelectedItems().size() + " / 3)");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        techList.render(delta);

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
