package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.utils.Cost;

import java.util.Arrays;

public class CoverObject extends Object {

    private final Drawable item;

    public CoverObject(String text, Drawable item, Cost cost) {
        super(text, cost);
        this.item = item;
    }

    public CoverObject(String text, Drawable item) {
        super(text);
        this.item = item;
    }

    public Drawable getItem() {
        return item;
    }

    public String toJson() {
        return "{" +
                "\"text\": \"" + getName() + "\"," +
                "\"item\": \"" + getName().replace(" ", "_").toLowerCase() + "\"," +
                "\"cost\": {" +
                "\"value\": " + Arrays.toString(getCost().getCosts()) + "," +
                "\"currency\": \"" + Arrays.toString(getCost().getCostNames()) + "\"" +
                "}" +
                "}";
    }
}
