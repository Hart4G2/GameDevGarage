package com.mygdx.gamedevgarage.screens.collection.actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.data.GameObject;

import java.util.HashSet;

public class GameList extends Table {

    private final HashSet<GameObject> gameObjects;

    public GameList() {
        super(Assets.getInstance().getSkin());
        this.gameObjects = Game.getInstance().getGames();

        addItems();
    }

    private void addItems() {
        for (GameObject gameObject : gameObjects) {
            GameItem gameItem = new GameItem(gameObject);
            add(gameItem).width(getWidthPercent(.9f)).height(getHeightPercent(.2f))
                    .pad(getHeightPercent(.01f)).row();
        }
    }
}
