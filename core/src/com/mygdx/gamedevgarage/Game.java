package com.mygdx.gamedevgarage;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends com.badlogic.gdx.Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public static Assets assets;

    @Override
    public void create() {
        assets = new Assets();

        batch = new SpriteBatch();

        this.setScreen(new MainScreen(this));
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
        batch.dispose();
        font.dispose();
        assets.assetManager.dispose();
    }
}
