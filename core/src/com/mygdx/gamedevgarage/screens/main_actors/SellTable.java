package com.mygdx.gamedevgarage.screens.main_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;

public class SellTable extends Table {
    private final Array<SellActor> sellActors = new Array<>();

    public SellTable() {
        super(Assets.getInstance().getSkin());
    }

    public void addSellActor(SellActor sellActor) {
        add(sellActor).width(getWidthPercent(0.5f)).height(getHeightPercent(0.13f))
                .row();
        sellActors.add(sellActor);
    }

    public void removeActor(SellActor actor) {
        sellActors.removeValue(actor, true);
        clear();
        addItems();
    }

    private void addItems() {
        for(SellActor sellActor : sellActors) {
            add(sellActor).width(getWidthPercent(0.5f)).height(getHeightPercent(0.13f))
                    .row();
        }
    }

    public void resume() {
        for(SellActor sellActor : sellActors) {
            sellActor.resumeSelling();
        }
    }

    public void stop() {
        for(SellActor sellActor : sellActors) {
            sellActor.stopSelling();
        }
    }
}