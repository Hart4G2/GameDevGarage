package com.mygdx.gamedevgarage;


import com.mygdx.gamedevgarage.mini_games.CoverCreationScreen;
import com.mygdx.gamedevgarage.mini_games.MechanicSelectionScreen;
import com.mygdx.gamedevgarage.mini_games.TechSelectionScreen;


public class Game extends com.badlogic.gdx.Game {

    private Assets assets;
    private MainGameScreen mainScreen;

    @Override
    public void create() {
        assets = new Assets();
        mainScreen = new MainGameScreen(this);

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

    public MainGameScreen getMainScreen() {
        return mainScreen;
    }

    public void setMainScreen() {
        setScreen(mainScreen);
    }

    public void setCoverScreen() {
        setScreen(new CoverCreationScreen(this));
    }

    public void setTechScreen() {
        setScreen(new TechSelectionScreen(this));
    }

    public void setMechanicScreen() {
        setScreen(new MechanicSelectionScreen(this));
    }
}
