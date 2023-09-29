package com.mygdx.gamedevgarage.screens.mini_games.end_actor;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.reward.Reward;
import com.mygdx.gamedevgarage.utils.stats.Stats;

import java.util.List;
import java.util.Random;

public class NumberActor extends Table {

    private final Stats stats;
    private final Reward reward;

    Label hintLabel;

    private int score;
    private int design = 1;
    private int programming = 0;
    private int gameDesign = 2;
    private int energy = 2;
    private int exp = 3;
    private int lvl = 1;

    public NumberActor() {
        super(Assets.getInstance().getSkin());
        this.stats = Stats.getInstance();
        this.reward = Reward.getInstance();

        score = reward.getScore();
        design = reward.getDesign();
        programming = reward.getProgramming();
        gameDesign = reward.getGameDesign();
        energy = reward.getEnergy();
        exp = reward.getExp();
        lvl = reward.getLvl();

        createUIElements();
    }

    private void createUIElements(){
        Skin skin = getSkin();

        Drawable scoreIcon = skin.getDrawable("score");
        Drawable expIcon = skin.getDrawable("exp");
        Drawable lvlIcon = skin.getDrawable("lvl");
        Drawable designIcon = skin.getDrawable("design");
        Drawable programingIcon = skin.getDrawable("programing");
        Drawable gameDesignIcon = skin.getDrawable("game_design");
        Drawable energyIcon = skin.getDrawable("energy");

        String labelStyle100 = "white_100";
        String labelStyle32 = "white_32";

        ScoreItem scoreItem = new ScoreItem(scoreIcon, score, labelStyle100);
        ScoreItem designItem = new ScoreItem(designIcon, design, labelStyle32);
        ScoreItem programmingItem = new ScoreItem(programingIcon, programming, labelStyle32);
        ScoreItem gameDesignItem = new ScoreItem(gameDesignIcon, gameDesign, labelStyle32);
        ScoreItem energyItem = new ScoreItem(energyIcon, energy, labelStyle32);
        ScoreItem expItem = new ScoreItem(expIcon, exp, labelStyle32);
        ScoreItem lvlItem = new ScoreItem(lvlIcon, lvl, labelStyle32);

        if(stats.getStat(Currency.LEVEL) < lvl){
            lvlItem.setLabelStyle("green_32");
            lvlItem.setName("lvlItem");
        }

        energyItem.setLabelStyle(stats.getStat(Currency.ENERGY) < energy ? "green_32" : "red_32");


        List<String> hints = reward.getHints();
        String hint = "";

        if(hints != null && !(hints.size() == 0)){
            int r = new Random().nextInt(hints.size());
            hint = Assets.getInstance().myBundle.get(hints.get(r));
        }

        hintLabel = createLabel(hint, "white_24", true);
        hintLabel.setAlignment(Align.center);
        hintLabel.setVisible(false);

        add(scoreItem).width(getWidthPercent(.9f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .colspan(2).center()
                .row();
        if(hints != null && !(hints.size() == 0)) {
            add(hintLabel).width(getWidthPercent(.75f)).height(getHeightPercent(.1f))
                    .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                    .colspan(2).center()
                    .row();
        }
        add(designItem).width(getWidthPercent(.35f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0);
        add(programmingItem).width(getWidthPercent(.35f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .row();
        add(gameDesignItem).width(getWidthPercent(.35f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0);
        add(energyItem).width(getWidthPercent(.35f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .row();
        add(lvlItem).width(getWidthPercent(.35f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), getWidthPercent(.05f));
        add(expItem).width(getWidthPercent(.35f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .row();

        scoreItem.startAnimation();
        designItem.startAnimation();
        programmingItem.startAnimation();
        gameDesignItem.startAnimation();
        energyItem.startAnimation();
        expItem.startAnimation();
        lvlItem.startAnimation();
        startHintAnimation();

        if(!Game.getInstance().isEndScreenCalculated){
            reward.setNewValues();
        }
    }

    private void startHintAnimation(){
        hintLabel.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        hintLabel.addAction(Actions.alpha(0f, .001f));
                        hintLabel.setVisible(true);
                    }
                }),
                Actions.delay(.01f),
                Actions.fadeIn(.2f)
        ));
    }
}
