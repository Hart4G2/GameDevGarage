package com.mygdx.gamedevgarage.upgrade.ui;

import static com.mygdx.gamedevgarage.utils.Utils.createBuyButton;
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
import com.mygdx.gamedevgarage.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;

public class UpgradeCoverObjectsList extends Table {

    private final Game game;
    private final Assets assets;
    private final Skin skin;
    private final StatsTable statsTable;

    private Array<CoverListItem> items;
    private Array<TextButton> buttons;

    public UpgradeCoverObjectsList(Game game, UpgradeScreen parent) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.assets = game.getAssets();
        this.skin = assets.getSkin();
        this.statsTable = parent.getStatsTable();
        this.items = DataArrayFactory.createCoverObjects(game);

        buttons = new Array<>();

        addItems();
    }

    private void addItems() {
        for (CoverListItem item : items) {
            if(!item.isPurchased()) {
                TextButton button = createBuyButton("2 D", skin, item.getText());
                addClickListener(button);
                buttons.add(button);
                add(item.getImage()).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                        .padRight(getWidthPercent(.1f)).padBottom(getHeightPercent(.001f));
                add(button).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                        .padBottom(getHeightPercent(.001f)).row();
            }
        }
    }

    private void addClickListener(final TextButton button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int design = game.stats.getDesign();

                if(design >= 2){
                    statsTable.setDesign(design - 2);

                    for (int i = 0; i < items.size; i++) {
                        CoverListItem item = items.get(i);
                        String itemText = item.getText();

                        if(itemText.equals(button.getName())){
                            game.setCoverObjectPurchased(itemText);

                            removeActor(item.getImage());
                            removeActor(button);
                            break;
                        }
                    }
                }
            }
        });
    }
}
