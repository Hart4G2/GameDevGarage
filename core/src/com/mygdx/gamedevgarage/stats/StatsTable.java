package com.mygdx.gamedevgarage.stats;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StatsTable extends Table {

    private final Stats stats;

    private Property lvl;
    private Property exp;
    private Property money;
    private Property design;
    private Property programming;
    private Property gameDesign;

    public StatsTable(Skin skin, Stats stats) {
        super(skin);
        this.stats = stats;

        createUIElements();
    }

    private void createUIElements(){
        Skin skin = getSkin();

        lvl = new Property("lvl", stats.getLevel(), skin);
        exp = new Property("exp", stats.getExperience(), skin);
        design = new Property("design", stats.getDesign(), skin);
        programming = new Property("programing", stats.getProgramming(), skin);
        gameDesign = new Property("game design", stats.getGameDesign(), skin);
        money = new Property("money", stats.getMoney(), skin);

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
