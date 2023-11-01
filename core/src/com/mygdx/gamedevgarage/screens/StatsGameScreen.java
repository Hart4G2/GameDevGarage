package com.mygdx.gamedevgarage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

public abstract class StatsGameScreen extends GameScreen {

    protected StatsTable statsTable;

    protected void initStatsTable(){
        statsTable = StatsTable.getInstance();
    }

    protected void initStatsTable(String valueStyle, String hintStyle){
        statsTable = StatsTable.getInstance();
        statsTable.setValueLabelsStyle(valueStyle);
        statsTable.setHintLabelsStyle(hintStyle);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        statsTable.update();

        stage.act(delta);
        stage.draw();
    }
}
