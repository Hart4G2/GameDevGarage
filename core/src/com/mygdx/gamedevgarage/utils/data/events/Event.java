package com.mygdx.gamedevgarage.utils.data.events;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.utils.stats.Cost;

public class Event {

    private Drawable drawable;
    private String text;
    private Cost confirmCost;
    private Cost rejectCost;

    public Event(String text, Drawable drawable, Cost confirmCost, Cost rejectCost) {
        this.drawable = drawable;
        this.text = text;
        this.confirmCost = confirmCost;
        this.rejectCost = rejectCost;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public String getText() {
        return text;
    }

    public Cost getConfirmCost() {
        return confirmCost;
    }

    public Cost getRejectCost() {
        return rejectCost;
    }
}
