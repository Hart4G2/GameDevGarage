package com.mygdx.gamedevgarage.main_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.data.GameObject;


public class SellActor extends Table {

    private final Game game;

    private Label priceLabel;
    private ProgressBar progressBar;

    private final GameObject gameObject;

    private final float iterationTime;
    private int i;

    public SellActor(Game game, GameObject gameObject){
        super(game.getAssets().getSkin());
        this.game = game;
        this.gameObject = gameObject;
        this.iterationTime = gameObject.getSellTime() / 5;
        i = (int) (gameObject.getSoldTime() / iterationTime);

        createUIElements();
    }

    private void createUIElements(){
        String name = gameObject.getName();
        if(name.isEmpty())
            name = "game" + gameObject.getId();

        Label nameLabel = createLabel(name, getSkin(), "white_18");
        priceLabel = createLabel("", getSkin(), "white_16");
        progressBar = new ProgressBar(0, gameObject.getProfitMoney(), 1,
                false, getSkin(), "default-horizontal");
        progressBar.setValue(gameObject.getSoldMoney());

        add(nameLabel)
                .padBottom(5).colspan(2)
                .row();
        add(progressBar).width(getWidthPercent(0.2f))
                .padLeft(getWidthPercent(0.05f));
        add(priceLabel).width(getWidthPercent(0.07f))
                .padLeft(getWidthPercent(0.02f)).padRight(getWidthPercent(0.02f));

        setBackground("game_sell_item");
    }

    public void startSelling(){
        new Timer().scheduleTask(sellTask, iterationTime);
    }

    float[] coefficients = new float[]{.4f, .3f, .15f, .1f, .05f};

    private final Timer.Task sellTask = new Timer.Task() {
        @Override
        public void run() {
            float soldTime = gameObject.getSoldTime();
            float sellTime = gameObject.getSellTime();

            gameObject.setSoldTime(soldTime + iterationTime);

            if(soldTime >= sellTime) {
                game.getMainScreen().gameSold(gameObject);
                i = 0;
                return;
            }
            sell(i++);
            new Timer().scheduleTask(this, iterationTime);
        }
    };

    private void sell(int i){
        int profitMoney = gameObject.getProfitMoney();
        int soldMoney = gameObject.getSoldMoney();

        int value = Math.round(profitMoney * coefficients[i]);

        priceLabel.setText("+" + value);
        progressBar.setValue(progressBar.getValue() + value);

        gameObject.setSoldMoney(soldMoney + value);

        game.stats.setMoney(game.stats.getMoney() + value);
    }
}
