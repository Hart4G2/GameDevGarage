package com.mygdx.gamedevgarage.utils.data;

import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.reward.Reward;

import java.util.HashMap;
import java.util.HashSet;
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

    public static GameObject createGameObject() {
        Reward reward = Reward.getInstance();

        int id = calculateId();

        return new GameObject(id, name, color, object, technologies, mechanics, platform,
                reward.getScore(), reward.getProfitMoney(), reward.getSellTime(),
                false, 0, 0);
    }

    private static int calculateId() {
        HashSet<GameObject> games = Game.getInstance().getGames();
        int id = 0;

        for(GameObject game : games){
            if(game.getId() >= id) {
                id = game.getId() + 1;
            }
        }
        return id;
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
