package com.mygdx.gamedevgarage.mini_games.end_actor;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.utils.reward.Reward;

public class NumberActor extends Table {

    private final Stats stats;
    private final Reward reward;

    private int score;
    private int design = 1;
    private int programming = 0;
    private int gameDesign = 2;
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

        String labelStyle100 = "white_100";
        String labelStyle32 = "white_32";

        ScoreItem scoreItem = new ScoreItem(scoreIcon, score, labelStyle100);
        ScoreItem designItem = new ScoreItem(designIcon, design, labelStyle32);
        ScoreItem programmingItem = new ScoreItem(programingIcon, programming, labelStyle32);
        ScoreItem gameDesignItem = new ScoreItem(gameDesignIcon, gameDesign, labelStyle32);
        ScoreItem expItem = new ScoreItem(expIcon, exp, labelStyle32);
        ScoreItem lvlItem = new ScoreItem(lvlIcon, lvl, labelStyle32);

        if(stats.getLevel() < lvl){
            lvlItem.setLabelStyle("green_32");
            lvlItem.setName("lvlItem");
        }

        add(scoreItem).width(getWidthPercent(.9f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .colspan(2).center()
                .row();
        add(designItem).width(getWidthPercent(.9f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .colspan(2).center()
                .row();
        add(programmingItem).width(getWidthPercent(.9f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .colspan(2).center()
                .row();
        add(gameDesignItem).width(getWidthPercent(.9f)).height(getHeightPercent(.15f))
                .pad(getHeightPercent(.001f), 0, getHeightPercent(.001f), 0)
                .colspan(2).center()
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
        expItem.startAnimation();
        lvlItem.startAnimation();

        reward.setNewValues();
    }
}
