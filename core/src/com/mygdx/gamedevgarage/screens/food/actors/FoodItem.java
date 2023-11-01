package com.mygdx.gamedevgarage.screens.food.actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createScalingImage;
import static com.mygdx.gamedevgarage.utils.Utils.createTable;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.gamedevgarage.screens.TableActor;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.TextImageObject;
import com.mygdx.gamedevgarage.utils.stats.Cost;

import java.util.HashMap;

public class FoodItem extends TableActor {

    private final TextImageObject item;

    public FoodItem(TextImageObject item) {
        this.item = item;

        createUIElements();
    }

    @Override
    protected void createUIElements(){
        Image image = createScalingImage(item.getItem(), Scaling.fit);

        Table headerTable = getHeaderTable();

        add(image).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                .left()
                .padRight(getWidthPercent(.05f));
        add(headerTable)
                .left()
                .expand();

        setBackground("food_item");
    }

    private Table getHeaderTable(){
        HashMap<Currency, Integer> positiveCurrencies = getPositiveCurrencies();

        Label nameLabel = createLabel(item.getName(), "default", false);

        Table headerTable = createTable(skin);
        headerTable.add(nameLabel).width(getWidthPercent(.2f))
                .colspan(positiveCurrencies.size() * 2)
                .row();

        float imageSize = getWidthPercent(.04f);
        float pad = getWidthPercent(.005f);

        for(Currency currency : positiveCurrencies.keySet()) {
            Drawable icon = skin.getDrawable(currency.getDrawableName());
            Image iconImage = createScalingImage(icon, Scaling.fit);

            int cost = positiveCurrencies.get(currency) * -1;

            Label currencyLabel = createLabel("+" + cost, "white_16", false);

            headerTable.add(currencyLabel).width(getWidthPercent(.005f))
                    .left()
                    .padRight(pad).padTop(pad);
            headerTable.add(iconImage).width(imageSize).height(imageSize)
                    .left()
                    .padRight(pad * 2).padTop(pad);
        }

        return headerTable;
    }

    private HashMap<Currency, Integer> getPositiveCurrencies(){
        Cost cost = item.getCost();

        Currency[] currencies = cost.getCostNames();
        int[] costs = cost.getCosts();
        HashMap<Currency, Integer> result = new HashMap<>();

        for(int i = 0; i < costs.length; i++){
            if(costs[i] < 0){
                result.put(currencies[i], costs[i]);
            }
        }

        return result;
    }

    public TextImageObject getItem() {
        return item;
    }
}
