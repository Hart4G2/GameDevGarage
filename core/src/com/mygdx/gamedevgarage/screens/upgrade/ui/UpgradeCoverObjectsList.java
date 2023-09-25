package com.mygdx.gamedevgarage.screens.upgrade.ui;

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
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.CoverObject;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;

import java.util.ArrayList;
import java.util.List;

public class UpgradeCoverObjectsList extends Table {

    private final Game game;
    private final Stats stats;

    private final List<CoverListItem> items;
    private final List<Button> buttons;

    public UpgradeCoverObjectsList() {
        super(Assets.getInstance().getSkin());
        this.game = Game.getInstance();
        this.stats = Stats.getInstance();
        this.items = DataArrayFactory.createCoverObjects();

        buttons = new ArrayList<>();
        addItems();
    }

    private void addItems() {
        for (CoverListItem item : items) {
            if(!item.getCoverObject().isPurchased()) {
                Button button = createTextButton(item.getCoverObject());

                addClickListener(button);
                buttons.add(button);

                add(item.getImage()).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                        .padRight(getWidthPercent(.1f)).padBottom(getHeightPercent(.001f));
                add(button).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                        .padBottom(getHeightPercent(.001f)).row();
            }
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

            button.add(label)
                    .padRight(getWidthPercent(.005f));
            button.add(image).width(imageSize).height(imageSize)
                    .padRight(i == icons.size() - 1 ? 0 : getWidthPercent(.01f));
        }

        return button;
    }

    private void addClickListener(final Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSound("buying_cover");

                CoverListItem item = findItem(button.getName());
                Cost cost = item.getCoverObject().getCost();

                if(stats.isEnough(cost)){
                    stats.pay(cost);
                    item.getCoverObject().setPurchased(true);
                    game.setCoverObjectPurchased(item.getCoverObject().getName());
                    removeActor(item);
                }
            }
        });
    }

    private CoverListItem findItem(String name){
        for(CoverListItem item : items){
            if(item.getCoverObject().getName().equals(name)){
                return item;
            }
        }

        return null;
    }

    private void removeActor(CoverListItem actor) {
        items.remove(actor);
        buttons.clear();
        clear();
        addItems();
    }

    private void playSound(String sound){
        Assets.getInstance().setSound(sound);
    }
}
