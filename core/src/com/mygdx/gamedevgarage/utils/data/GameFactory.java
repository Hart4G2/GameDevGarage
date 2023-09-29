package com.mygdx.gamedevgarage.utils.data;

import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.reward.Reward;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GameFactory {

    public static String name;
    public static TranslatableObject theme;
    public static TranslatableObject genre;
    public static String previousTheme;
    public static String previousGenre;
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
        if(theme != null && genre != null) {
            data.put("theme", theme.getBundleKey());
            data.put("genre", genre.getBundleKey());
        }
        data.put("previousTheme", previousTheme);
        data.put("previousGenre", previousGenre);
        data.put("color", color);
        data.put("object", object);
        data.put("technologies", String.valueOf(technologies));
        data.put("mechanics", String.valueOf(mechanics));
        data.put("platform", platform);

        if(Game.getInstance().gameState == GameState.END  && Game.getInstance().isScreenShowed){
            Reward reward = Reward.getInstance();

            data.put("rewardScore", String.valueOf(reward.getScore()));
            data.put("rewardDesign", String.valueOf(reward.getDesign()));
            data.put("rewardProgramming", String.valueOf(reward.getProgramming()));
            data.put("rewardGameDesign", String.valueOf(reward.getGameDesign()));
            data.put("rewardEnergy", String.valueOf(reward.getEnergy()));
            data.put("rewardExp", String.valueOf(reward.getExp()));
            data.put("rewardLvl", String.valueOf(reward.getLvl()));
            data.put("rewardSellTime", String.valueOf(reward.getSellTime()));
            data.put("rewardProfitMoney", String.valueOf(reward.getProfitMoney()));
        }

        return data;
    }
}
