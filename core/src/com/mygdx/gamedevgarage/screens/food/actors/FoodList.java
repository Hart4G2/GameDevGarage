package com.mygdx.gamedevgarage.screens.food.actors;

import static com.mygdx.gamedevgarage.utils.Utils.createBuyButton;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.screens.TableActor;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.TextImageObject;
import com.mygdx.gamedevgarage.utils.stats.Cost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FoodList extends TableActor {

    private final List<FoodItem> items;
    private final List<Button> buttons;

    public FoodList() {
        this.items = DataArrayFactory.createFoodItems();

        buttons = new ArrayList<>();
        createUIElements();
    }

    @Override
    protected void createUIElements() {
        float itemWidth = getWidthPercent(.75f);
        float itemHeight = getWidthPercent(.2f);
        float itemPad = getWidthPercent(.005f);
        float buttonSize = getWidthPercent(.17f);
        float padBottom = getHeightPercent(.01f);


        for (FoodItem item : items) {
            Button button = createTextButton(item.getItem());

            addClickListener(button);
            buttons.add(button);

            add(item).width(itemWidth).height(itemHeight)
                    .padRight(itemPad).padBottom(padBottom);
            add(button).width(buttonSize).height(buttonSize)
                    .padBottom(padBottom).row();
        }
    }

    private Button createTextButton(TextImageObject textImageObject){
        HashMap<Currency, Integer> negativeCurrencies = getNegativeCurrencies(textImageObject);
        Set<Currency> currencies = negativeCurrencies.keySet();

        Button button = createBuyButton(textImageObject.getBundleKey());

        float imageSize = getWidthPercent(.04f);
        float labelPad = getWidthPercent(.005f);
        float imagePad = getWidthPercent(.01f);

        int i = 0;
        for(Currency currency : currencies){
            Drawable icon = skin.getDrawable(currency.getDrawableName());
            Image image = new Image(icon);
            Label label = createLabel(negativeCurrencies.get(currency) + "", "white_16", false);

            button.add(label)
                    .padRight(labelPad);
            button.add(image).width(imageSize).height(imageSize)
                    .padRight(i == currencies.size() - 1 ? 0 : imagePad);
            i++;
        }

        return button;
    }

    private HashMap<Currency, Integer> getNegativeCurrencies(TextImageObject item){
        Cost cost = item.getCost();

        Currency[] currencies = cost.getCostNames();
        int[] costs = cost.getCosts();
        HashMap<Currency, Integer> result = new HashMap<>();

        for(int i = 0; i < costs.length; i++){
            if(costs[i] > 0){
                result.put(currencies[i], costs[i]);
            }
        }

        return result;
    }

    private void addClickListener(final Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FoodItem item = findItem(button.getName());
                Cost cost = item.getItem().getCost();

                if(stats.isEnough(cost)){
                    playSound("eating");
                    stats.pay(cost);

                    Utils.setHint(cost, "white_16");
                } else {
                    playSound("not_eating");
                }
            }
        });
    }

    private FoodItem findItem(String name){
        for(FoodItem item : items){
            if(item.getItem().getBundleKey().equals(name)){
                return item;
            }
        }

        return null;
    }

    private void playSound(String sound){
        assets.setSound(sound);
    }
}
