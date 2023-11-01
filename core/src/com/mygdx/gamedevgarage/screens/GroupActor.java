package com.mygdx.gamedevgarage.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.gamedevgarage.assets.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.assets.Bundle;

public abstract class GroupActor extends Group {

    protected final Game game;
    protected final Assets assets;
    protected final Skin skin;
    protected final Bundle bundle;

    public GroupActor() {
        game = Game.getInstance();
        assets = Assets.getInstance();
        skin = assets.getSkin();
        bundle = assets.getBundle();
    }

    protected void addActors(Actor... actors) {
        for (Actor actor : actors) {
            addActor(actor);
        }
    }

    protected abstract void createUIElements();
}
