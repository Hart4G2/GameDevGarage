package com.mygdx.gamedevgarage.utils.data;

import com.mygdx.gamedevgarage.Game;

import java.util.HashMap;
import java.util.List;

public class GameFactory {

    public static String name;
    public static String theme;
    public static String genre;
    public static String color;
    public static String object;
    public static List<String> technologies;
    public static List<String> mechanics;
    public static String platform;

    public static GameObject createGameObject(Game game, int id) {
        return new GameObject(game, id, name, color, object, technologies, mechanics, platform,
                game.reward.getScore(), game.reward.getProfitMoney(), game.reward.getSellTime(),
                false, 0, 0);
    }

    public static GameObject createGameObjectDebug(Game game, int id, String name, String color,
                                                   String object, List<String> technologies,
                                                   List<String> mechanics, String platform,
                                                   int score, int profitMoney, float sellTime,
                                                   boolean isSold, float soldTime, int soldMoney
    ) {
        return new GameObject(game, id, name, color, object, technologies, mechanics, platform,
                score, profitMoney, sellTime, isSold, soldTime, soldMoney);
    }

    public static HashMap<String, String> getProcessData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("theme", theme);
        data.put("genre", genre);
        data.put("color", color);
        data.put("object", object);
        data.put("technologies", String.valueOf(technologies));
        data.put("mechanics", String.valueOf(mechanics));
        data.put("platform", platform);

        return data;
    }

    public static String getString() {
        return getProcessData().toString();
    }
}
