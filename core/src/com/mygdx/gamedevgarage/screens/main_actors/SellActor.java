package com.mygdx.gamedevgarage.screens.main_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createProgressBar;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.GameObject;


public class SellActor extends Table {

    private final Game game;
    private final Stats stats;

    private final GameObject gameObject;

    private Label priceLabel;
    private ProgressBar progressBar;

    private Timer sellTimer;

    private final float iterationTime;
    private int i;

    public SellActor(GameObject gameObject){
        super(Assets.getInstance().getSkin());
        this.game = Game.getInstance();
        this.stats = Stats.getInstance();
        this.gameObject = gameObject;
        this.iterationTime = gameObject.getSellTime() / 10;
        i = (int) (gameObject.getSoldTime() / iterationTime);

        createUIElements();
        sellTimer = new Timer();
    }

    private void createUIElements(){
        String name = validName(gameObject.getName());

        Label nameLabel = createLabel(name, "white_18", false);
        priceLabel = createLabel("", "white_16", false);
        progressBar = createProgressBar(0, gameObject.getProfitMoney());
        progressBar.setValue(gameObject.getSoldMoney());

        add(nameLabel)
                .padBottom(5).colspan(2)
                .row();
        add(progressBar).width(getWidthPercent(0.25f))
                .padLeft(getWidthPercent(0.05f));
        add(priceLabel).width(getWidthPercent(0.07f))
                .padLeft(getWidthPercent(0.02f)).padRight(getWidthPercent(0.02f));

        setBackground("game_sell_item");
    }


    public void startSelling(){
        sellTimer.scheduleTask(sellTask, iterationTime);
    }

    float[] coefficients = new float[]{.16f, .14f, .13f, .12f, .1f, .09f, .08f, .07f, .06f, .05f};

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

        stats.pay(new Cost(new Currency[]{Currency.MONEY}, new int[]{-value}));
    }

    private String validName(String name){
        if(name.isEmpty()){
            name = "game" + gameObject.getId();
        }

        if(name.length() > 20){
            name = name.substring(0, 20) + "...";
        }
        return name;
    }

    public void resumeSelling() {
        sellTimer.start();
    }

    public void stopSelling() {
        sellTimer.stop();
    }
}
