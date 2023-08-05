package com.mygdx.gamedevgarage;


import com.mygdx.gamedevgarage.mini_games.DesignMiniGameScreen;
import com.mygdx.gamedevgarage.mini_games.GameDesignMiniGameScreen;
import com.mygdx.gamedevgarage.mini_games.ProgrammingMiniGameScreen;


public class Game extends com.badlogic.gdx.Game {

    private Assets assets;
    private MainScreen mainScreen;

    @Override
    public void create() {
        assets = new Assets();
        mainScreen = new MainScreen(this);

        this.setScreen(mainScreen);
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

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public void setMainScreen() {
        setScreen(mainScreen);
    }

    public void setCoverScreen() {
        setScreen(new DesignMiniGameScreen(this));
    }

    public void setTechScreen() {
        setScreen(new ProgrammingMiniGameScreen(this));
    }

    public void setMechanicScreen() {
        setScreen(new GameDesignMiniGameScreen(this));
    }
}
