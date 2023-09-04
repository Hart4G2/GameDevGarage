package com.mygdx.gamedevgarage.main_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Game;

public class SellTable extends Table {
    private final Array<SellActor> sellActors = new Array<>();

    public SellTable(Game game) {
        super(game.getAssets().getSkin());
    }

    public void addSellActor(SellActor sellActor) {
        add(sellActor).width(getWidthPercent(0.35f)).height(getHeightPercent(0.13f))
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
            add(sellActor).width(getWidthPercent(0.35f)).height(getHeightPercent(0.13f))
                    .row();
        }
    }
}
