package com.mygdx.gamedevgarage.stats;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.gamedevgarage.Assets;

public class StatsTable extends Table {

    private final Stats stats;

    private Property lvl;
    private Property exp;
    private Property money;
    private Property design;
    private Property programming;
    private Property gameDesign;

    public StatsTable() {
        super(Assets.getInstance().getSkin());
        this.stats = Stats.getInstance();

        createUIElements();
    }

    private void createUIElements(){
        lvl = new Property("lvl", stats.getLevel());
        exp = new Property("exp", stats.getExperience());
        design = new Property("design", stats.getDesign());
        programming = new Property("programing", stats.getProgramming());
        gameDesign = new Property("game design", stats.getGameDesign());
        money = new Property("money", stats.getMoney());

        setSize(getWidthPercent(.96f), getHeightPercent(.03f));
        setPosition(getWidthPercent(.04f), Gdx.graphics.getHeight());

        top().padTop(getHeightPercent(.025f));
        add(lvl).expand();
        add(exp).expand();
        add(programming).expand();
        add(design).expand();
        add(gameDesign).expand();
        add(money).expand();
    }

    public void setLabelsStyle(String style){
        lvl.setLabelStyle(style);
        exp.setLabelStyle(style);
        design.setLabelStyle(style);
        programming.setLabelStyle(style);
        gameDesign.setLabelStyle(style);
        money.setLabelStyle(style);
    }

    public void update() {
        lvl.setValue(stats.getLevel());
        exp.setValue(stats.getExperience());
        money.setValue(stats.getMoney());
        design.setValue(stats.getDesign());
        programming.setValue(stats.getProgramming());
        gameDesign.setValue(stats.getGameDesign());
    }
}
