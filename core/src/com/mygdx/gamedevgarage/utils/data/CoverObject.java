package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.utils.stats.Cost;

import java.util.Arrays;

public class CoverObject extends Object {

    private final Drawable item;

    public CoverObject(String name, String bundleKey, Drawable item, Cost cost) {
        super(name, bundleKey, cost);
        this.item = item;
    }

    public CoverObject(String bundleKey, Drawable item) {
        super(bundleKey);
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
