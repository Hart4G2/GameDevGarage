package com.mygdx.gamedevgarage.stats;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.gamedevgarage.Assets;

public class StatsTable extends Table {

    private Assets assets;
    private Skin skin;
    private Stats stats;

    private Property lvl;
    private Property exp;
    private Property money;
    private Property design;
    private Property programming;
    private Property gameDesign;

    public StatsTable(Assets assets, Stats stats) {
        super(assets.getSkin());
        this.assets = assets;
        this.skin = assets.getSkin();
        this.stats = stats;

        createUIElements();
    }

    private void createUIElements(){
        lvl = new Property("lvl", stats.getLevel(), assets);
        exp = new Property("exp", stats.getExperience(), assets);
        design = new Property("design", stats.getDesign(), assets);
        programming = new Property("programing", stats.getProgramming(), assets);
        gameDesign = new Property("game design", stats.getGameDesign(), assets);
        money = new Property("money", stats.getMoney(), assets);

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

    public void setLvl(int value) {
        lvl.setValue(value);
        stats.setLevel(value);
    }

    public void setExp(int value) {
        exp.setValue(value);
        stats.setExperience(value);
    }

    public void setMoney(int value) {
        money.setValue(value);
        stats.setMoney(value);
    }

    public void setDesign(int value) {
        design.setValue(value);
        stats.setDesign(value);
    }

    public void setProgramming(int value) {
        programming.setValue(value);
        stats.setProgramming(value);
    }

    public void setGameDesign(int value) {
        gameDesign.setValue(value);
        stats.setGameDesign(value);
    }
}
