package com.mygdx.gamedevgarage.stats;

import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.HashMap;

public class Stats {

    private int level = 1111;
    private int experience = 1000;
    private int design = 1000;
    private int programming = 1011;
    private int gameDesign = 1011;
    private int money = 1000;

    private static Stats instance;

    public Stats(int level, int experience, int design, int programming, int gameDesign, int money) {
        this.level = level;
        this.experience = experience;
        this.design = design;
        this.programming = programming;
        this.gameDesign = gameDesign;
        this.money = money;
    }

    public static Stats getInstance(){
        if(instance == null){
//            HashMap<String, Integer> statsMap = DataManager.getInstance().getStats();
            HashMap<String, Integer> statsMap = new HashMap<>();

            if(statsMap.isEmpty()){
                instance = new Stats(6, 49, 30, 30, 30, 100);
            } else {
                instance = new Stats(statsMap.get("level"), statsMap.get("exp"),
                        statsMap.get("design"), statsMap.get("programming"),
                        statsMap.get("gameDesign"), statsMap.get("money"));
            }
        }
        return instance;
    }

    public static void setInstance(Stats stats){
        instance = stats;
    }

    public void setLevel(int level) {
        this.level = Math.min(level, 9);
    }

    public void setExperience(int experience, int requiredExp) {
        this.experience = experience - requiredExp;
    }

    public void setDesign(int design) {
        this.design = Math.min(design, 100);
    }

    public void setProgramming(int programming) {
        this.programming = Math.min(programming, 100);
    }

    public void setGameDesign(int gameDesign) {
        this.gameDesign = Math.min(gameDesign, 100);
    }

    public void setMoney(int money) {
        this.money = Math.min(money, 100);
    }

    public boolean isEnough(Cost cost){
        Currency[] costNames = cost.getCostNames();
        int[] costs = cost.getCosts();

        for(int i = 0; i < costNames.length; i++){
            if(this.getStat(costNames[i]) < costs[i]){
                return false;
            }
        }

        return true;
    }

    public void pay(Cost cost){
        Currency[] costNames = cost.getCostNames();
        int[] costs = cost.getCosts();

        for(int i = 0; i < costNames.length; i++){
            int costValue = costs[i];
            Currency currency = costNames[i];

            if(currency == Currency.LEVEL || currency == Currency.EXPERIENCE){
                continue;
            }

            setStat(currency, getStat(currency) - costValue);
        }
    }

    public void setStat(Currency currency, int stat){
        switch (currency){
            case MONEY:
                setMoney(stat);
                break;
            case DESIGN:
                setDesign(stat);
                break;
            case PROGRAMMING:
                setProgramming(stat);
                break;
            case GAME_DESIGN:
                setGameDesign(stat);
                break;
        }
    }

    public int getStat(Currency currency){
        switch (currency){
            case MONEY:
                return money;
            case DESIGN:
                return design;
            case PROGRAMMING:
                return programming;
            case GAME_DESIGN:
                return gameDesign;
            case LEVEL:
                return level;
            case EXPERIENCE:
                return experience;
            default:
                return 0;
        }
    }
}
