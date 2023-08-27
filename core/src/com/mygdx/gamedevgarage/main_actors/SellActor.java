package com.mygdx.gamedevgarage.main_actors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.Utils;

import java.util.Random;


public class SellActor extends Table {

    private Game game;
    private Assets assets;

    private Label nameLabel;
    private Label priceLabel;
    private ProgressBar progressBar;

    private String name;
    private int price;
    private final float time;
    private final float iterationTime;
    private int iterationCount = 5;

    public SellActor(Game game, String name, int price, float time) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.assets = game.getAssets();
        this.name = name;
        this.price = price;
        this.time = time;
        this.iterationTime = time / iterationCount;

        createUIElements();
    }

    private void createUIElements(){
        Skin skin = assets.getSkin();

        nameLabel = Utils.createLabel(name, skin, "default");
        priceLabel = Utils.createLabel("", skin, "green");
        progressBar = new ProgressBar(0, price, 1, false, skin, "default-horizontal");

        add(nameLabel).padBottom(5)
                .colspan(2).row();
        add(progressBar);
        add(priceLabel).padLeft(5);

        setBackground("list_background");
    }

    public void startSelling(){
        new Timer().scheduleTask(sellTask, iterationTime);
    }

    int i = 0;
    float[] coefficients = new float[]{.4f, .3f, .15f, .1f, .05f};

    private final Timer.Task sellTask = new Timer.Task() {
        @Override
        public void run() {
            if(i > 4) {
                i = 0;
                return;
            }
            sell(new Random().nextInt(10), i);
            i++;
            new Timer().scheduleTask(this, iterationTime);
        }
    };

    private void sell(int r, int i){
        int value = Math.round(price * coefficients[i]);
        priceLabel.setText("+" + value);
        progressBar.setValue(progressBar.getValue() + value);
    }
}
