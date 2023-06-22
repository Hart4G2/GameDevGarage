package com.mygdx.gamedevgarage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;

public class Assets {

    public AssetManager assetManager;
    public TextureAtlas mainButtonAtlas;
    public BitmapFont font = new BitmapFont();

    public Assets() {
        assetManager = new AssetManager();
        loadAllAssets();
    }

    private void loadAllAssets() {
        AssetManager assetManager = new AssetManager();

        assetManager.load("atlases/button-atlas.atlas", TextureAtlas.class);


        while (!assetManager.update()) {
            // Render loading screen or wait for some time
        }

        mainButtonAtlas = assetManager.get("atlases/button-atlas.atlas", TextureAtlas.class);
    }
}
