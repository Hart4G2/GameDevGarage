package com.mygdx.gamedevgarage;

import com.mygdx.gamedevgarage.mini_games.TechSelectionScreen;

public class Game extends com.badlogic.gdx.Game {

    private Assets assets;

    @Override
    public void create() {
        assets = new Assets();

        this.setScreen(new TechSelectionScreen(this));
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    public Assets getAssets() {
        return assets;
    }
}
