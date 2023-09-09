package com.mygdx.gamedevgarage.stats;

import com.mygdx.gamedevgarage.utils.DataManager;

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
            HashMap<String, Integer> statsMap = DataManager.getInstance().getStats();

            if(statsMap.isEmpty()){
                instance = new Stats(1, 49, 10, 10, 10, 1);
            } else {
                instance = new Stats(statsMap.get("level"), statsMap.get("exp"),
                        statsMap.get("design"), statsMap.get("programming"),
                        statsMap.get("gameDesign"), statsMap.get("money"));
            }
        }
        return instance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.min(level, 9);
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience, int requiredExp) {
        this.experience = experience - requiredExp;
    }

    public int getDesign() {
        return design;
    }

    public void setDesign(int design) {
        this.design = Math.min(design, 100);
    }

    public int getProgramming() {
        return programming;
    }

    public void setProgramming(int programming) {
        this.programming = Math.min(programming, 100);
    }

    public int getGameDesign() {
        return gameDesign;
    }

    public void setGameDesign(int gameDesign) {
        this.gameDesign = Math.min(gameDesign, 100);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = Math.min(money, 100);
    }
}
