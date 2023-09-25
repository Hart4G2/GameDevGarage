package com.mygdx.gamedevgarage.screens.mini_games;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
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

public class GameDesignScreen implements Screen {

    private final Skin skin;
    private Stage stage;

    private Button okButton;
    private CheckList mechanicList;
    private StatsTable statsTable;
    private Label headerLabel;

    public GameDesignScreen() {
        this.skin = Assets.getInstance().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Game.getInstance().isScreenShowed = true;

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = StatsTable.getInstance();
        statsTable.setValueLabelsStyle("black_18");
        statsTable.setHintLabelsStyle("default");

        GameFactory.mechanics = new ArrayList<>();
        List<CheckListItem> mechanics = DataArrayFactory.createMechanics();

        mechanicList = new CheckList(mechanics);

        ScrollPane scrollPane = new ScrollPane(mechanicList, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollbarsVisible(true);

        headerLabel = createLabel("Choose mechanics (0 / 3)", "white_20", false);

        okButton = createTextButton("OK", "white_18");
        okButton.setDisabled(true);

        Group group = new Group();
        group.addActor(scrollPane);
        group.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 1.15f);

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

        Image bg = new Image(Assets.getInstance().getSkin().getDrawable("window_green"));
        bg.setScaling(Scaling.fill);
        Stack stack = new Stack(bg, table);
        stack.setFillParent(true);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    Game.getInstance().setMainScreen();
                    DialogThread.getInstance().setPlatformThread(true);
                    for(CheckObject object : mechanicList.getSelectedItems()){
                        GameFactory.mechanics.add(object.getName());
                    }
                }
            }
        });

        mechanicList.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                okButton.setDisabled(mechanicList.getSelectedItems().size() == 0);
                setSelectedCount();
            }
        });
    }

    private void setSelectedCount(){
        headerLabel.setText("Choose mechanics (" + mechanicList.getSelectedItems().size() + " / 3)");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mechanicList.render(delta);

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
        // Действия при приостановке экрана (например, когда игра переходит в фоновый режим)
    }

    @Override
    public void resume() {
        // Действия при возобновлении экрана (например, когда игра возвращается из фонового режима)
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

}
