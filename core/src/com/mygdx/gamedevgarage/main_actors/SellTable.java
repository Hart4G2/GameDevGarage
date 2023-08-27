package com.mygdx.gamedevgarage.main_actors;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;

public class SellTable extends Table {

    private Game game;
    private Assets assets;

    private Array<SellActor> sellActors;

    public SellTable(Game game) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.assets = game.getAssets();
        this.sellActors = new Array<>();
    }

    public void addSellActor(SellActor sellActor) {
        this.sellActors.add(sellActor);
        addSellActorToTable(sellActor);
    }

    private void addSellActorToTable(SellActor sellActor) {
        add(sellActor).row();
    }

    public void removeSellActor(SellActor sellActor) {
        this.sellActors.removeValue(sellActor, false);
        removeSellActorFromTable(sellActor);
    }

    private void removeSellActorFromTable(SellActor sellActor) {
        removeActor(sellActor);
    }
}
