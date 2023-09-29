package com.mygdx.gamedevgarage.utils.data;

import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.stats.Cost;

public class Object {

    private final String name;
    private final String bundleKey;
    private boolean isPurchased = false;
    private Cost cost;

    public Object(String name, String bundleKey, Cost cost) {
        this.name = name;
        this.bundleKey = bundleKey;
        this.cost = cost;
    }

    public Object(String bundleKey) {
        this.name = Assets.getInstance().myBundle.get(bundleKey);
        this.bundleKey = bundleKey;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public String getName() {
        return name;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public Cost getCost() {
        return cost;
    }

    public String getBundleKey() {
        return bundleKey;
    }
}
