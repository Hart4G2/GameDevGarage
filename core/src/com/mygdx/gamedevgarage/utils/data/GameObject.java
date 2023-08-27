package com.mygdx.gamedevgarage.utils.data;

import com.mygdx.gamedevgarage.Game;

import java.util.List;

public class GameObject {

    private int id;
    private String name;
    private String color;
    private String object;
    private String[] technologies;
    private String[] mechanics;
    private String platform;
    private int score;

    private boolean isSold;
    private int profitMoney;
    private float sellTime;

    public GameObject(Game game, String name, String color, String object,
                      String[] technologies, String[] mechanics, String platform,
                      int score, int profitMoney, float sellTime) {
        this.name = name;
        this.color = color;
        this.object = object;
        this.technologies = technologies;
        this.mechanics = mechanics;
        this.platform = platform;
        this.score = score;
        this.profitMoney = profitMoney;
        this.sellTime = sellTime;

        initId(game.getGames());
    }

    private void initId(List<GameObject> games) {
        id = 0;
        if(games.isEmpty())
            return;

        for(GameObject game : games){
            if(game.getId() > id) {
                id = game.getId() + 1;
            }
        }
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getObject() {
        return object;
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public String[] getMechanics() {
        return mechanics;
    }

    public int getScore() {
        return score;
    }

    public int getProfitMoney() {
        return profitMoney;
    }

    public float getSellTime() {
        return sellTime;
    }
}
