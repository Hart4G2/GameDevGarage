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
import com.mygdx.gamedevgarage.screens.mini_games.selection_actors.CheckListItem;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.CheckObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpgradeCheckItemsList extends Table {

    private final Game game;
    private final Stats stats;

    private final boolean isTechnology;

    private final List<CheckListItem> items;
    private final List<Button> buttons;

    public UpgradeCheckItemsList(boolean isTechnology) {
        super(Assets.getInstance().getSkin());
        this.game = Game.getInstance();
        this.stats = Stats.getInstance();
        this.isTechnology = isTechnology;
        this.items = isTechnology ? DataArrayFactory.createTechnologies() : DataArrayFactory.createMechanics();
        Collections.shuffle(items);
        buttons = new ArrayList<>();

        addItems();
    }

    private void addItems() {
        for (CheckListItem item : items) {
            if(!item.getCheckObject().isPurchased()) {
                Button button = createTextButton(item.getCheckObject());

                buttons.add(button);
                addClickListener(button);

                add(item).width(getWidthPercent(.79f)).height(getHeightPercent(.2f))
                        .padRight(getWidthPercent(.001f));
                add(button).width(getWidthPercent(.17f)).height(getWidthPercent(.17f))
                        .row();
            }
        }
    }

    private Button createTextButton(CheckObject checkObject){
        Cost cost = checkObject.getCost();
        Currency[] currencies = cost.getCostNames();
        int[] costs = cost.getCosts();

        List<Drawable> icons = new ArrayList<>();

        for(Currency currency : currencies){
            icons.add(getSkin().getDrawable(currency.getDrawableName()));
        }

        Button button = createBuyButton(checkObject.getBundleKey());

        float imageSize = getWidthPercent(.04f);

        for (int i = 0; i < icons.size(); i++) {
            Image image = new Image(icons.get(i));
            Label label = createLabel(costs[i] + "", "white_14", false);

            button.add(label)
                    .padRight(getWidthPercent(.005f));
            button.add(image).width(imageSize).height(imageSize)
                    .padRight(i == icons.size() - 1 ? 0 : getWidthPercent(.01f));;
        }

        return button;
    }

    private void addClickListener(final Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSound(isTechnology ? "buying_tech" : "buying_mech");

                CheckListItem item = findItem(button.getName());
                Cost cost = item.getCheckObject().getCost();

                if(stats.isEnough(cost)){
                    stats.pay(cost);

                    if(isTechnology){
                        game.setTechnologyPurchased(item.getCheckObject().getBundleKey());
                    } else {
                        game.setMechanicPurchased(item.getCheckObject().getBundleKey());
                    }

                    item.getCheckObject().setPurchased(true);

                    removeActor(item);
                }
            }
        });
    }

    private CheckListItem findItem(String name){
        for(CheckListItem item : items){
            if(item.getCheckObject().getBundleKey().equals(name)){
                return item;
            }
        }

        return null;
    }

    private void removeActor(CheckListItem actor) {
        items.remove(actor);
        buttons.clear();
        clear();
        addItems();
    }

    public void render(float delta){
        for(CheckListItem item : items){
            item.render(delta);
        }
    }

    private void playSound(String sound){
        Assets.getInstance().setSound(sound);
    }
}
