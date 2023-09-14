package com.mygdx.gamedevgarage.screens.mini_games.platform_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createBuyButton;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.CoverObject;
import com.mygdx.gamedevgarage.utils.data.GameFactory;

import java.util.ArrayList;
import java.util.List;

public class PlatformList extends Table {

    private final Stats stats;

    private final List<CoverListItem> items;
    private final List<Button> buttons;

    private final Drawable selected;
    private final Drawable unSelected;

    public PlatformList(List<CoverListItem> items) {
        super(Assets.getInstance().getSkin());
        this.stats = Stats.getInstance();
        this.unSelected = getSkin().getDrawable("platform_item");
        this.selected = getSkin().getDrawable("platform_item_selected");
        this.items = items;

        buttons = new ArrayList<>();

        addItems();
    }

    private void addItems() {
        for (CoverListItem item : items) {
            if(!item.getCoverObject().isPurchased()) {
                Button button = createTextButton(item.getCoverObject());

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

    private Button createTextButton(CoverObject coverObject){
        Cost cost = coverObject.getCost();
        Currency[] currencies = cost.getCostNames();
        int[] costs = cost.getCosts();

        List<Drawable> icons = new ArrayList<>();

        for(Currency currency : currencies){
            icons.add(getSkin().getDrawable(currency.getDrawableName()));
        }

        Button button = createBuyButton(coverObject.getName());

        float imageSize = getWidthPercent(.04f);

        for (int i = 0; i < icons.size(); i++) {
            Image image = new Image(icons.get(i));
            Label label = createLabel(costs[i] + "", "white_18", false);

            button.add(label);
            button.add(image).width(imageSize).height(imageSize)
                    .padRight(i == icons.size() - 1 ? 0 : getWidthPercent(.01f));
        }

        return button;
    }

    private void addClickListener(final CoverListItem item) {
        item.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(item.getCoverObject().isPurchased()){
                    selectPlatform(item);
                    playSound("buying_platform");
                } else {
                    buyPlatform(item);
                }
            }
        });
    }

    private void addClickListener(final Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyPlatform(findItem(button.getName()));
            }
        });
    }

    private void buyPlatform(CoverListItem item){
        CoverObject coverObject = item.getCoverObject();
        Cost cost = coverObject.getCost();

        if(stats.isEnough(cost)){
            playSound("buying_platform");
            stats.pay(cost);

            item.getCoverObject().setPurchased(true);
            Game.getInstance().setPlatformPurchased(item.getCoverObject().getName());

            Button button = findButton(item.getCoverObject().getName());
            buttons.remove(button);
            removeActor(button);

            selectPlatform(item);
        } else {
            playSound("platform_unavailable");
        }
    }

    private void selectPlatform(CoverListItem item){
        String selectedPlatform = GameFactory.platform;
        String thisPlatform = item.getCoverObject().getName();

        if(!selectedPlatform.equals("") && !selectedPlatform.equals(thisPlatform)) {
            CoverListItem previousItem = findItem(selectedPlatform);
            previousItem.setBackground(unSelected);
        }

        if(selectedPlatform.equals("") || !selectedPlatform.equals(thisPlatform)){
            GameFactory.platform = thisPlatform;
            item.setBackground(selected);
        }
    }

    private CoverListItem findItem(String name){
        for(CoverListItem item : items){
            if(item.getCoverObject().getName().equals(name)){
                return item;
            }
        }

        return null;
    }

    private Button findButton(String name){
        for(Button button : buttons){
            if(button.getName().equals(name)){
                return button;
            }
        }

        return null;
    }

    private void playSound(String name) {
        Assets.getInstance().setSound(name);
    }
}
