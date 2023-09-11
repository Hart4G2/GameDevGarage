package com.mygdx.gamedevgarage.utils.data;

import com.mygdx.gamedevgarage.utils.Cost;

public class Object {

    private final String name;
    private boolean isPurchased = false;
    private Cost cost;

    public Object(String name, Cost cost) {
        this.name = name;
        this.cost = cost;
    }

    public Object(String name) {
        this.name = name;
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
}
