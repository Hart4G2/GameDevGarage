package com.mygdx.gamedevgarage.mini_games.platform_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createBuyButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.PlatformScreen;
import com.mygdx.gamedevgarage.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.utils.data.GameFactory;

public class PlatformList extends Table {

    private final Game game;
    private final Stats stats;
    private final PlatformScreen parent;

    private final Array<CoverListItem> items;
    private final Array<TextButton> buttons;

    private final Drawable selected;
    private final Drawable unSelected;

    public PlatformList(Array<CoverListItem> items, PlatformScreen parent) {
        super(Assets.getInstance().getSkin());
        this.stats = Stats.getInstance();
        this.game = Game.getInstance();
        this.parent = parent;
        this.unSelected = getSkin().getDrawable("platform_item");
        this.selected = getSkin().getDrawable("platform_item_selected");
        this.items = items;

        buttons = new Array<>();

        addItems();
    }

    private void addItems() {
        for (CoverListItem item : items) {
            if(!item.isPurchased()) {
                TextButton button = createBuyButton("10 M", item.getText());
                addClickListener(button);
                buttons.add(button);

                add(item).width(getWidthPercent(.8f)).height(getHeightPercent(.15f))
                        .padRight(getWidthPercent(.01f));
                add(button).width(getWidthPercent(.17f)).height(getWidthPercent(.17f)).row();
            } else {
                add(item).width(getWidthPercent(.8f)).height(getHeightPercent(.15f))
                        .padRight(getWidthPercent(.01f)).row();
            }
            addClickListener(item);
        }
    }

    private void addClickListener(final CoverListItem item) {
        item.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(item.isPurchased()){
                    selectPlatform(item);
                    playSound("buying_platform");
                } else {
                    buyPlatform(item.getText());
                }
            }
        });
    }

    private void addClickListener(final TextButton button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyPlatform(button.getName());
            }
        });
    }

    private void buyPlatform(String itemName){
        int money = stats.getMoney();

        if(money >= 10){
            playSound("buying_platform");

            stats.setMoney(money - 10);

            for (int i = 0; i < items.size; i++) {
                CoverListItem item = items.get(i);
                String itemText = item.getText();

                if(itemText.equals(itemName)){
                    game.setPlatformPurchased(itemText);
                    item.setPurchased(true);

                    selectPlatform(item);

                    for(TextButton button : buttons){
                        if(button.getName().equals(itemName)) {
                            removeActor(button);
                        }
                    }
                    break;
                }
            }
        } else {
            playSound("platform_unavailable");
        }
    }

    private void selectPlatform(CoverListItem item){
        String selectedPlatform = GameFactory.platform;

        if(selectedPlatform == null || !selectedPlatform.equals(item.getText())){
            for(CoverListItem platform : items){
                if(platform.getText().equals(selectedPlatform)){
                    platform.setBackground(unSelected);
                }
            }

            item.setBackground(selected);
            parent.setPlatform(item.getText());
        }
    }

    private void playSound(String name) {
        Assets.getInstance().setSound(name);
    }
}
