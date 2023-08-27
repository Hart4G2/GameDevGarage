package com.mygdx.gamedevgarage.mini_games.end_actor;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.reward.Reward;

public class NumberActor extends Table {

    private Game game;
    private Assets assets;
    private Skin skin;

    private int score;
    private int design = 1;
    private int programming = 0;
    private int gameDesign = 2;
    private int exp = 3;
    private int lvl = 1;

    public NumberActor(Game game) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.assets = game.getAssets();
        this.skin = assets.getSkin();

        Reward reward = game.reward;

        score = reward.getScore();
        design = reward.getDesign();
        programming = reward.getProgramming();
        gameDesign = reward.getGameDesign();
        exp = reward.getExp();
        lvl = reward.getLvl();

        createUIElements();
    }

    private void createUIElements(){
        Drawable scoreIcon = new TextureRegionDrawable(new Texture("stats/score.png"));
        Drawable expIcon = new TextureRegionDrawable(new Texture("stats/exp.png"));
        Drawable lvlIcon = new TextureRegionDrawable(new Texture("stats/lvl.png"));
        Drawable designIcon = new TextureRegionDrawable(new Texture("stats/design.png"));
        Drawable programingIcon = new TextureRegionDrawable(new Texture("stats/programing.png"));
        Drawable gameDesignIcon = new TextureRegionDrawable(new Texture("stats/game_design.png"));

        String labelStyle100 = "white_100";
        String labelStyle32 = "white_32";

        ScoreItem scoreItem = new ScoreItem(skin, scoreIcon, score, labelStyle100);
        ScoreItem designItem = new ScoreItem(skin, designIcon, design, labelStyle32);
        ScoreItem programmingItem = new ScoreItem(skin, programingIcon, programming, labelStyle32);
        ScoreItem gameDesignItem = new ScoreItem(skin, gameDesignIcon, gameDesign, labelStyle32);
        ScoreItem expItem = new ScoreItem(skin, expIcon, exp, labelStyle32);
        ScoreItem lvlItem = new ScoreItem(skin, lvlIcon, lvl, labelStyle32);

        if(game.stats.getLevel() < lvl){
            lvlItem.setLabelStyle("green_32");
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

        game.reward.setNewValues();
    }
}
