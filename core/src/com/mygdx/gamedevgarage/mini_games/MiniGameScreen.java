package com.mygdx.gamedevgarage.mini_games;

public interface MiniGameScreen {

    void render(float delta);
    void addItem(String item);
    void removeItem(String item);
}
