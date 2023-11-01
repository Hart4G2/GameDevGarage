package com.mygdx.gamedevgarage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.assets.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.reward.Reward;
import com.mygdx.gamedevgarage.utils.stats.Stats;

public abstract class GameScreen implements Screen {

    protected Game game;
    protected Assets assets;
    protected Skin skin;
    protected DataManager dataManager;
    protected Reward reward;
    protected Stats stats;

    protected I18NBundle bundle;

    protected Stage stage;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        showInitialise();
        createUIElements();
        setupUIListeners();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    protected void showInitialise() {
        game = Game.getInstance();
        assets = Assets.getInstance();
        skin = assets.getSkin();
        bundle = assets.getBundle().getBundle();
        dataManager = DataManager.getInstance();
        reward = Reward.getInstance();
        stats = Stats.getInstance();
    }

    protected abstract void createUIElements();

    protected abstract void setupUIListeners();
}
