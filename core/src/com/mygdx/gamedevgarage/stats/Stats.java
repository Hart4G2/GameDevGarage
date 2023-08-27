package com.mygdx.gamedevgarage.stats;

public class Stats {

    private int level = 1111;
    private int experience = 1000;
    private int design = 1000;
    private int programming = 1011;
    private int gameDesign = 1011;
    private int money = 1000;

    public Stats(int level, int experience, int design, int programming, int gameDesign, int money) {
        this.level = level;
        this.experience = experience;
        this.design = design;
        this.programming = programming;
        this.gameDesign = gameDesign;
        this.money = money;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getDesign() {
        return design;
    }

    public void setDesign(int design) {
        if(this.design <= 100){
            this.design = design;
        }
    }

    public int getProgramming() {
        return programming;
    }

    public void setProgramming(int programming) {
        if(this.programming <= 100){
            this.programming = programming;
        }
    }

    public int getGameDesign() {
        return gameDesign;
    }

    public void setGameDesign(int gameDesign) {
        if(this.gameDesign <= 100){
            this.gameDesign = gameDesign;
        }
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }


}
