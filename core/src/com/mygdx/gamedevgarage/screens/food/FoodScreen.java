package com.mygdx.gamedevgarage.screens.food;

import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createTable;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.screens.StatsGameScreen;
import com.mygdx.gamedevgarage.screens.food.actors.FoodList;

public class FoodScreen extends StatsGameScreen {

    private TextButton backButton;

    @Override
    protected void createUIElements() {
        initStatsTable();

        FoodList foodList = new FoodList();

        ScrollPane scrollPane = new ScrollPane(foodList, skin);

        backButton = createTextButton(bundle.get("ok"), "white_18");

        Table mainTable = createTable(skin, true);
        mainTable.add(scrollPane).width(getWidthPercent(.95f)).height(getHeightPercent(.75f))
                .pad(getHeightPercent(.1f), 0, getHeightPercent(.005f), 0)
                .row();
        mainTable.add(backButton).width(getWidthPercent(.35f)).height(getHeightPercent(.07f));

        Stack stack = createBgStack("window_darkgreen", mainTable);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    @Override
    protected void setupUIListeners() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMainScreen();
            }
        });
    }
}
