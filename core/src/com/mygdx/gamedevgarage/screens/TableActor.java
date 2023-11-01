package com.mygdx.gamedevgarage.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.assets.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.assets.Bundle;
import com.mygdx.gamedevgarage.utils.reward.Reward;
import com.mygdx.gamedevgarage.utils.stats.Stats;

public abstract class TableActor extends Table {

    protected final Game game;
    protected final Assets assets;
    protected final Skin skin;
    protected final Stats stats;
    protected final Bundle bundle;
    protected final I18NBundle i18NBundle;
    protected final Reward reward;

    public TableActor() {
        super(Assets.getInstance().getSkin());
        game = Game.getInstance();
        assets = Assets.getInstance();
        skin = getSkin();
        stats = Stats.getInstance();
        bundle = assets.getBundle();
        i18NBundle = bundle.getBundle();
        reward = Reward.getInstance();
    }

    protected abstract void createUIElements();
}
