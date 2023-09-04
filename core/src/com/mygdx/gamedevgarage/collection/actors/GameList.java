package com.mygdx.gamedevgarage.collection.actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.data.GameObject;

import java.util.HashSet;

public class GameList extends Table {

    private final Game game;

    private final HashSet<GameObject> gameObjects;

    public GameList(Game game) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.gameObjects = game.getGames();

        addItems();
    }

    private void addItems() {
        for (GameObject gameObject : gameObjects) {
            GameItem gameItem = new GameItem(game, gameObject);
            add(gameItem).width(getWidthPercent(.8f)).height(getHeightPercent(.2f))
                    .pad(getHeightPercent(.01f)).row();
        }
    }
}
