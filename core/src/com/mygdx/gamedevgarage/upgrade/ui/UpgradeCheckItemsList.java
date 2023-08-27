package com.mygdx.gamedevgarage.upgrade.ui;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckListItem;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;

public class UpgradeCheckItemsList extends Table {

    private final Game game;
    private final Assets assets;
    private final Skin skin;
    private final StatsTable statsTable;

    private final boolean isTechnology;
    private final String buttonText;

    private Array<CheckListItem> items;
    private Array<TextButton> buttons;

    public UpgradeCheckItemsList(Game game, UpgradeScreen parent, boolean isTechnology) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.assets = game.getAssets();
        this.skin = assets.getSkin();
        this.statsTable = parent.getStatsTable();
        this.isTechnology = isTechnology;
        this.items = isTechnology ? DataArrayFactory.createTechnologies(game) : DataArrayFactory.createMechanics(game);
        this.buttonText = isTechnology ? "2 P" : "2 GD";

        buttons = new Array<>();

        addItems();
    }

    private void addItems() {
        for (CheckListItem item : items) {
            TextButton button = Utils.createBuyButton(buttonText, skin, item.getText());
            buttons.add(button);
            addClickListener(button);
            add(item).width(getWidthPercent(.7f)).height(getHeightPercent(.15f))
                    .padRight(getWidthPercent(.001f));
            add(button).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                    .row();
        }
    }

    private void addClickListener(final TextButton button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClicked(button);
            }
        });
    }

    private void buttonClicked(final TextButton button){
        int property = isTechnology ? game.stats.getProgramming() : game.stats.getGameDesign();

        if(property >= 2){
            for (int i = 0; i < items.size; i++) {
                CheckListItem item = items.get(i);
                String itemText = item.getText();

                if(itemText.equals(button.getName())){
                    if(isTechnology){
                        statsTable.setProgramming(property - 2);
                        game.setTechnologyPurchased(itemText);
                    } else {
                        statsTable.setGameDesign(property - 2);
                        game.setMechanicPurchased(itemText);
                    }

                    removeActor(item);
                    removeActor(button);
                    break;
                }
            }
        }
    }

    public void render(float delta){
        for(CheckListItem item : items){
            item.render(delta);
        }
    }
}
