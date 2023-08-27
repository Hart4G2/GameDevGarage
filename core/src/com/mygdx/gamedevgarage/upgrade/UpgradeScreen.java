package com.mygdx.gamedevgarage.upgrade;

import static com.mygdx.gamedevgarage.utils.Utils.createButton;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.upgrade.ui.UpgradeCheckItemsList;
import com.mygdx.gamedevgarage.upgrade.ui.UpgradeCoverObjectsList;

import java.util.HashMap;
import java.util.Map;

public class UpgradeScreen implements Screen {

    private final Game game;
    private final Assets assets;
    private final Skin skin;
    private Stage stage;

    private TextButton showCover;
    private TextButton showTech;
    private TextButton showMechanic;
    private Button backButton;
    private UpgradeCoverObjectsList coverList;
    private UpgradeCheckItemsList techList;
    private UpgradeCheckItemsList mechanicList;
    private ScrollPane coverObjectsScrollPane;
    private ScrollPane technologiesScrollPane;
    private ScrollPane mechanicsScrollPane;
    private StatsTable statsTable;
    private Map<String, ScrollPane> buttonPanelMap;

    private static final String SHOW_COVER_BUTTON = "showCover";
    private static final String SHOW_TECH_BUTTON = "showTech";
    private static final String SHOW_MECHANIC_BUTTON = "showMechanic";

    public UpgradeScreen(Game game) {
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
        statsTable = new StatsTable(assets, game.stats);

        coverList = new UpgradeCoverObjectsList(game, this);
        coverObjectsScrollPane = new ScrollPane(coverList, skin);
        coverObjectsScrollPane.setFillParent(true);
        coverObjectsScrollPane.setScrollbarsVisible(true);

        techList = new UpgradeCheckItemsList(game, this,true);
        technologiesScrollPane = new ScrollPane(techList, skin);
        technologiesScrollPane.setFillParent(true);
        technologiesScrollPane.setScrollbarsVisible(true);
        technologiesScrollPane.setVisible(false);

        mechanicList = new UpgradeCheckItemsList(game, this,false);
        mechanicsScrollPane = new ScrollPane(mechanicList, skin);
        mechanicsScrollPane.setFillParent(true);
        mechanicsScrollPane.setScrollbarsVisible(true);
        mechanicsScrollPane.setVisible(false);

        Group group = new Group();
        group.addActor(coverObjectsScrollPane);
        group.addActor(technologiesScrollPane);
        group.addActor(mechanicsScrollPane);
        group.setSize(getWidthPercent(.95f), getHeightPercent(.8f));

        showCover = createTextButton("Covers", skin, "white_16", SHOW_COVER_BUTTON);
        showTech = createTextButton("Technologies", skin, "white_16", SHOW_TECH_BUTTON);
        showMechanic = createTextButton("Mechanics", skin, "white_16", SHOW_MECHANIC_BUTTON);
        backButton = createButton(skin, "back_button");
        showCover.setDisabled(true);

        buttonPanelMap = new HashMap<>();

        buttonPanelMap.put("showCover", coverObjectsScrollPane);
        buttonPanelMap.put("showTech", technologiesScrollPane);
        buttonPanelMap.put("showMechanic", mechanicsScrollPane);

        buttonClicked(SHOW_COVER_BUTTON);

        Table table = new Table();
        table.setFillParent(true);
        table.add(backButton).width(getWidthPercent(0.15f)).height(getWidthPercent(0.15f))
                .pad(0, getWidthPercent(0.01f), getHeightPercent(0.01f), getWidthPercent(0.01f));
        table.add(showCover).width(getWidthPercent(0.25f)).height(getHeightPercent(0.06f))
                .pad(0, getWidthPercent(0.01f), getHeightPercent(0.01f), getWidthPercent(0.01f));
        table.add(showTech).width(getWidthPercent(0.28f)).height(getHeightPercent(0.06f))
                .pad(0, getWidthPercent(0.01f), getHeightPercent(0.01f), getWidthPercent(0.01f));
        table.add(showMechanic).width(getWidthPercent(0.25f)).height(getHeightPercent(0.06f))
                .pad(0, getWidthPercent(0.01f), getHeightPercent(0.01f), getWidthPercent(0.01f)).row();
        table.add(group).colspan(4)
                .pad(0, getWidthPercent(0.01f), getHeightPercent(0.01f), getWidthPercent(0.01f)).row();

        stage.addActor(table);
        stage.addActor(statsTable);

        table.setBackground(skin.getDrawable("window_red"));
    }

    private void setupUIListeners(){
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainScreen();
            }
        });

        showCover.addListener(createButtonClickListener(SHOW_COVER_BUTTON));
        showTech.addListener(createButtonClickListener(SHOW_TECH_BUTTON));
        showMechanic.addListener(createButtonClickListener(SHOW_MECHANIC_BUTTON));
    }

    private ClickListener createButtonClickListener(final String buttonName) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClicked(buttonName);
            }
        };
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        techList.render(delta);
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

    private void buttonClicked(String buttonName) {
        showCover.setDisabled(SHOW_COVER_BUTTON.equals(buttonName));
        showTech.setDisabled(SHOW_TECH_BUTTON.equals(buttonName));
        showMechanic.setDisabled(SHOW_MECHANIC_BUTTON.equals(buttonName));

        ScrollPane panelToShow = buttonPanelMap.get(buttonName);
        showPanel(panelToShow);
    }

    private void showPanel(ScrollPane panelToShow) {
        coverObjectsScrollPane.setVisible(panelToShow == coverObjectsScrollPane);
        technologiesScrollPane.setVisible(panelToShow == technologiesScrollPane);
        mechanicsScrollPane.setVisible(panelToShow == mechanicsScrollPane);
    }

    public StatsTable getStatsTable() {
        return statsTable;
    }
}
