package com.mygdx.gamedevgarage.screens.upgrade;

import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.createTable;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.upgrade.ui.UpgradeCheckItemsList;
import com.mygdx.gamedevgarage.screens.upgrade.ui.UpgradeCoverObjectsList;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.stats.Cost;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UpgradeScreen implements Screen {

    private final Skin skin;
    private final I18NBundle bundle;
    private Stage stage;

    private TextButton showCover;
    private TextButton showTech;
    private TextButton showMechanic;
    private TextButton showAd;
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

    public UpgradeScreen() {
        skin = Assets.getInstance().getSkin();
        bundle = Assets.getInstance().myBundle;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        statsTable = StatsTable.getInstance();

        coverList = new UpgradeCoverObjectsList();
        coverObjectsScrollPane = new ScrollPane(coverList, skin);
        coverObjectsScrollPane.setFillParent(true);
        coverObjectsScrollPane.setScrollbarsVisible(true);

        techList = new UpgradeCheckItemsList(true);
        technologiesScrollPane = new ScrollPane(techList, skin);
        technologiesScrollPane.setFillParent(true);
        technologiesScrollPane.setScrollbarsVisible(true);
        technologiesScrollPane.setVisible(false);

        mechanicList = new UpgradeCheckItemsList(false);
        mechanicsScrollPane = new ScrollPane(mechanicList, skin);
        mechanicsScrollPane.setFillParent(true);
        mechanicsScrollPane.setScrollbarsVisible(true);
        mechanicsScrollPane.setVisible(false);

        Group group = new Group();
        group.addActor(coverObjectsScrollPane);
        group.addActor(technologiesScrollPane);
        group.addActor(mechanicsScrollPane);

        showCover = createTextButton(bundle.get("Covers"), "default", SHOW_COVER_BUTTON);
        showTech = createTextButton(bundle.get("Technologies"), "red_16", SHOW_TECH_BUTTON);
        showMechanic = createTextButton(bundle.get("Mechanics"), "default", SHOW_MECHANIC_BUTTON);
        showAd = createTextButton(bundle.get("Get_bonus"), "green_16");
        backButton = createButton("back_button");
        showCover.setDisabled(true);

        buttonPanelMap = new HashMap<>();

        buttonPanelMap.put("showCover", coverObjectsScrollPane);
        buttonPanelMap.put("showTech", technologiesScrollPane);
        buttonPanelMap.put("showMechanic", mechanicsScrollPane);

        buttonClicked(SHOW_COVER_BUTTON);

        float buttonPad = getWidthPercent(.001f);
        float topPad = getHeightPercent(.07f);

        Table table = createTable(skin, true);
        table.add(backButton).width(getWidthPercent(.13f)).height(getWidthPercent(.13f))
                .pad(topPad, buttonPad, buttonPad, buttonPad);
        table.add(showCover).width(getWidthPercent(.2f)).height(getHeightPercent(.06f))
                .pad(topPad, buttonPad, buttonPad, buttonPad);
        table.add(showTech).width(getWidthPercent(.22f)).height(getHeightPercent(.06f))
                .pad(topPad, buttonPad, buttonPad, buttonPad);
        table.add(showMechanic).width(getWidthPercent(.2f)).height(getHeightPercent(.06f))
                .pad(topPad, buttonPad, buttonPad, buttonPad);
        table.add(showAd).width(getWidthPercent(.2f)).height(getHeightPercent(.06f))
                .pad(topPad, buttonPad, buttonPad, buttonPad)
                .row();
        table.add(group).width(getWidthPercent(.999f)).height(getHeightPercent(.8f))
                .padBottom(getHeightPercent(0.01f))
                .colspan(5).row();

        Stack stack = createBgStack("window_darkred", table);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private void setupUIListeners(){
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().setMainScreen();
            }
        });

        showCover.addListener(createButtonClickListener(showCover));
        showTech.addListener(createButtonClickListener(showTech));
        showMechanic.addListener(createButtonClickListener(showMechanic));
        showAd.addListener(showAdButton());
    }

    private ClickListener createButtonClickListener(final TextButton button) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!button.isDisabled()){
                    buttonClicked(button.getName());
                }
            }
        };
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statsTable.update();
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

    private void showPanel(final ScrollPane panelToShow) {
        panelToShow.addAction(Actions.sequence(
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        coverObjectsScrollPane.setVisible(panelToShow == coverObjectsScrollPane);
                        technologiesScrollPane.setVisible(panelToShow == technologiesScrollPane);
                        mechanicsScrollPane.setVisible(panelToShow == mechanicsScrollPane);
                    }
                }),
                Actions.alpha(0,0),
                Actions.fadeIn(.4f)
        ));
    }

    Timer timer = new Timer();

    private ClickListener showAdButton() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!showAd.isDisabled()) {
                    Game.getInstance().getAdHandler().showRewardAd("upgrade");

                    showAd.setDisabled(true);
                    timer.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            showAd.setDisabled(false);
                        }
                    }, 3);
                }
            }
        };
    }

    public void setAdShowed(boolean isShowed){
        if(isShowed){
            setBonus();
        } else {
            if(!showAd.hasActions())
                showAd.addAction(Actions.sequence(
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                showAd.setText(bundle.get("error"));
                                showAd.getLabel().getStyle().font.setColor(Color.RED);
                            }
                        }),
                        Actions.delay(2f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                showAd.getLabel().getStyle().font.setColor(Color.WHITE);
                                showAd.setText(bundle.get("Get_bonus"));
                            }
                        })
                ));
        }
    }

    public void setBonus(){
        Random r = new Random();

        int rCur = r.nextInt(4);
        Currency currency;

        switch (rCur){
            case 0:
                currency = Currency.DESIGN;
                break;
            case 1:
                currency = Currency.PROGRAMMING;
                break;
            case 2:
                currency = Currency.GAME_DESIGN;
                break;
            default:
                currency = Currency.MONEY;
                break;
        }

        int value = r.nextInt(8) + 3;

        Cost bonus = new Cost(
                new Currency[]{currency},
                new int[]{-value}
        );

        Stats.getInstance().pay(bonus);

        StatsTable.setHint(currency, bundle.get("THANK_YOU"), "white_16");
    }
}
