package com.mygdx.gamedevgarage.stats;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.ArrayList;
import java.util.List;

public class StatsTable extends Table {

    private final Stats stats;

    private Property lvl;
    private Property exp;
    private Property money;
    private Property design;
    private Property programming;
    private Property gameDesign;

    public static List<StatsTable> tables;

    public StatsTable() {
        super(Assets.getInstance().getSkin());
        this.stats = Stats.getInstance();

        createUIElements();

        if(tables == null){
            tables = new ArrayList<>();
        }

        tables.add(this);
    }

    private void createUIElements(){
        lvl = new Property("lvl", stats.getStat(Currency.LEVEL));
        exp = new Property("exp", stats.getStat(Currency.EXPERIENCE));
        design = new Property("design", stats.getStat(Currency.DESIGN));
        programming = new Property("programing", stats.getStat(Currency.PROGRAMMING));
        gameDesign = new Property("game design", stats.getStat(Currency.GAME_DESIGN));
        money = new Property("money", stats.getStat(Currency.MONEY));

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

    public void setValueLabelsStyle(String style){
        lvl.setValueLabelStyle(style);
        exp.setValueLabelStyle(style);
        design.setValueLabelStyle(style);
        programming.setValueLabelStyle(style);
        gameDesign.setValueLabelStyle(style);
        money.setValueLabelStyle(style);
    }

    public void setHintLabelsStyle(String style){
        lvl.setHintLabelStyle(style);
        exp.setHintLabelStyle(style);
        design.setHintLabelStyle(style);
        programming.setHintLabelStyle(style);
        gameDesign.setHintLabelStyle(style);
        money.setHintLabelStyle(style);
    }

    public void update() {
        lvl.setValue(stats.getStat(Currency.LEVEL));
        exp.setValue(stats.getStat(Currency.EXPERIENCE));
        money.setValue(stats.getStat(Currency.MONEY));
        design.setValue(stats.getStat(Currency.DESIGN));
        programming.setValue(stats.getStat(Currency.PROGRAMMING));
        gameDesign.setValue(stats.getStat(Currency.GAME_DESIGN));
    }

    public Property getProperty(String name) {
        switch (name) {
            case "lvl":
                return lvl;
            case "exp":
                return exp;
            case "money":
                return money;
            case "design":
                return design;
            case "programing":
                return programming;
            case "gameDesign":
                return gameDesign;
            default:
                return null;
        }
    }
}
