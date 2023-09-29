package com.mygdx.gamedevgarage.utils.stats;

import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.HashMap;

public class Stats {

    private int level = 1111;
    private int experience = 1000;
    private int design = 1000;
    private int programming = 1011;
    private int gameDesign = 1011;
    private int money = 1000;
    private int energy = 10;

    private static Stats instance;

    public Stats(int level, int experience, int design, int programming, int gameDesign, int money, int energy) {
        this.level = level;
        this.experience = experience;
        this.design = design;
        this.programming = programming;
        this.gameDesign = gameDesign;
        this.money = money;
        this.energy = energy;
    }

    public static Stats getInstance(){
        if(instance == null){
            HashMap<String, Integer> statsMap = DataManager.getInstance().getStats();

            if(statsMap.isEmpty()){
                instance = new Stats(6, 49, 30, 30, 30, 10, 10);
            } else {
                instance = new Stats(statsMap.get("level"), statsMap.get("exp"),
                        statsMap.get("design"), statsMap.get("programming"),
                        statsMap.get("gameDesign"), statsMap.get("money"), statsMap.get("energy"));
            }
        }
        return instance;
    }

    public static void setInstance(Stats stats){
        instance = stats;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, Math.min(level, 9));
    }

    public void setExperience(int experience, int requiredExp) {
        this.experience = Math.max(0, experience - requiredExp);
    }

    public void setDesign(int design) {
        this.design = Math.max(0, Math.min(design, 999));
    }

    public void setProgramming(int programming) {
        this.programming = Math.max(0, Math.min(programming, 999));
    }

    public void setGameDesign(int gameDesign) {
        this.gameDesign = Math.max(0, Math.min(gameDesign, 999));
    }

    public void setMoney(int money) {
        this.money = Math.min(money, 999);
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(energy, 10));
    }

    public boolean isEnough(Cost cost){
        Currency[] costNames = cost.getCostNames();
        int[] costs = cost.getCosts();

        for(int i = 0; i < costNames.length; i++){
            if(getStat(costNames[i]) < costs[i]){
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
            case ENERGY:
                setEnergy(stat);
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
            case ENERGY:
                return energy;
            default:
                return 0;
        }
    }
}