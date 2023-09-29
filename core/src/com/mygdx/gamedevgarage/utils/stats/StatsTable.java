package com.mygdx.gamedevgarage.utils.stats;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;
import static com.mygdx.gamedevgarage.utils.constraints.Currency.DESIGN;
import static com.mygdx.gamedevgarage.utils.constraints.Currency.ENERGY;
import static com.mygdx.gamedevgarage.utils.constraints.Currency.EXPERIENCE;
import static com.mygdx.gamedevgarage.utils.constraints.Currency.GAME_DESIGN;
import static com.mygdx.gamedevgarage.utils.constraints.Currency.LEVEL;
import static com.mygdx.gamedevgarage.utils.constraints.Currency.MONEY;
import static com.mygdx.gamedevgarage.utils.constraints.Currency.PROGRAMMING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

public class StatsTable extends Table {

    private final I18NBundle bundle;
    private Property lvl;
    private Property exp;
    private Property money;
    private Property design;
    private Property programming;
    private Property gameDesign;
    private Property energy;

    private static StatsTable instance;

    public static StatsTable getInstance(){
        if(instance == null){
            instance = new StatsTable();
        }
        return instance;
    }

    private StatsTable() {
        super(Assets.getInstance().getSkin());
        bundle = Assets.getInstance().myBundle;

        createUIElements();
    }

    private void createUIElements(){
        Stats stats = Stats.getInstance();
        Skin skin = Assets.getInstance().getSkin();

        lvl = new Property(bundle.get("lvl"), skin.getDrawable("lvl"), stats.getStat(LEVEL));
        exp = new Property(bundle.get("exp"), skin.getDrawable("exp"), stats.getStat(EXPERIENCE));
        design = new Property(bundle.get("design"), skin.getDrawable("design"), stats.getStat(DESIGN));
        programming = new Property(bundle.get("programing"), skin.getDrawable("programing"), stats.getStat(PROGRAMMING));
        gameDesign = new Property(bundle.get("game_design"), skin.getDrawable("game_design"), stats.getStat(GAME_DESIGN));
        money = new Property(bundle.get("money"), skin.getDrawable("money"), stats.getStat(MONEY));
        energy = new Property(bundle.get("energy"), skin.getDrawable("energy"), stats.getStat(ENERGY));

        top().padTop(getHeightPercent(.025f));
        add(lvl).expand();
        add(exp).expand();
        add(programming).expand();
        add(design).expand();
        add(gameDesign).expand();
        add(money).expand();
        add(energy).expand();

        setSize(getWidthPercent(.96f), getHeightPercent(.03f));
        setPosition(getWidthPercent(.04f), Gdx.graphics.getHeight());
    }

    public static void setHint(Currency property, String hint, String labelStyle){
        StatsTable table = getInstance();
        table.getProperty(property).setHint(hint, labelStyle);
    }

    public void update() {
        Stats stats = Stats.getInstance();

        lvl.setValue(stats.getStat(LEVEL));
        exp.setValue(stats.getStat(EXPERIENCE));
        design.setValue(stats.getStat(DESIGN));
        programming.setValue(stats.getStat(PROGRAMMING));
        gameDesign.setValue(stats.getStat(GAME_DESIGN));
        money.setValue(stats.getStat(MONEY));
        energy.setValue(stats.getStat(ENERGY));
    }

    public void setValueLabelsStyle(String style){
        lvl.setValueLabelStyle(style);
        exp.setValueLabelStyle(style);
        design.setValueLabelStyle(style);
        programming.setValueLabelStyle(style);
        gameDesign.setValueLabelStyle(style);
        money.setValueLabelStyle(style);
        energy.setValueLabelStyle(style);
    }

    public void setHintLabelsStyle(String style){
        lvl.setHintLabelStyle(style);
        exp.setHintLabelStyle(style);
        design.setHintLabelStyle(style);
        programming.setHintLabelStyle(style);
        gameDesign.setHintLabelStyle(style);
        money.setHintLabelStyle(style);
        energy.setHintLabelStyle(style);
    }

    public Property getProperty(Currency currency) {
        switch (currency) {
            case LEVEL:
                return lvl;
            case EXPERIENCE:
                return exp;
            case MONEY:
                return money;
            case DESIGN:
                return design;
            case PROGRAMMING:
                return programming;
            case GAME_DESIGN:
                return gameDesign;
            case ENERGY:
                return energy;
            default:
                return null;
        }
    }
}