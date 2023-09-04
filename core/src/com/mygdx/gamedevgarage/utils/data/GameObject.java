package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.gamedevgarage.Game;

import java.util.List;
import java.util.Objects;

public class GameObject implements Json.Serializable{

    private Game game;

    private int id;
    private String name;
    private String color;
    private String object;
    private List<String> technologies;
    private List<String> mechanics;
    private String platform;
    private int score;

    private boolean isSold;
    private int profitMoney;
    private int soldMoney;
    private float sellTime;
    private float soldTime;

    public GameObject() {}

    public GameObject(Game game, int id, String name, String color, String object,
                      List<String> technologies, List<String> mechanics, String platform,
                      int score, int profitMoney, float sellTime, boolean isSold, float soldTime, int soldMoney) {
        this.game = game;
        this.id = id;
        this.name = name;
        this.color = color;
        this.object = object;
        this.technologies = technologies;
        this.mechanics = mechanics;
        this.platform = platform;
        this.score = score;
        this.profitMoney = profitMoney;
        this.sellTime = sellTime;
        this.isSold = isSold;
        this.soldTime = soldTime;
        this.soldMoney = soldMoney;
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

    public int getScore() {
        return score;
    }

    public int getProfitMoney() {
        return profitMoney;
    }

    public String getPlatform() {
        return platform;
    }

    public int getSoldMoney() {
        return soldMoney;
    }

    public void setSoldMoney(int soldMoney) {
        this.soldMoney = soldMoney;
    }

    public float getSellTime() {
        return sellTime;
    }

    public float getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(float soldTime) {
        this.soldTime = soldTime;
    }

    public void startSelling(){
        game.getMainScreen().startSellGame(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void write(Json json) {
        json.writeValue("id", id);
        json.writeValue("name", name);
        json.writeValue("color", color);
        json.writeValue("object", object);
        json.writeValue("technologies", technologies);
        json.writeValue("mechanics", mechanics);
        json.writeValue("platform", platform);
        json.writeValue("score", score);
        json.writeValue("isSold", isSold);
        json.writeValue("profitMoney", profitMoney);
        json.writeValue("soldMoney", soldMoney);
        json.writeValue("sellTime", sellTime);
        json.writeValue("soldTime", soldTime);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        id = json.readValue("id", Integer.class, jsonData);
        name = json.readValue("name", String.class, jsonData);
        color = json.readValue("color", String.class, jsonData);
        object = json.readValue("object", String.class, jsonData);
        technologies = json.readValue("technologies", List.class, jsonData);
        mechanics = json.readValue("mechanics", List.class, jsonData);
        platform = json.readValue("platform", String.class, jsonData);
        score = json.readValue("score", Integer.class, jsonData);
        isSold = json.readValue("isSold", Boolean.class, jsonData);
        profitMoney = json.readValue("profitMoney", Integer.class, jsonData);
        soldMoney = json.readValue("soldMoney", Integer.class, jsonData);
        sellTime = json.readValue("sellTime", Float.class, jsonData);
        soldTime = json.readValue("soldTime", Float.class, jsonData);
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "game=" + game +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", object='" + object + '\'' +
                ", technologies=" + technologies +
                ", mechanics=" + mechanics +
                ", platform='" + platform + '\'' +
                ", score=" + score +
                ", isSold=" + isSold +
                ", profitMoney=" + profitMoney +
                ", sellTime=" + sellTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
