package com.mygdx.gamedevgarage.mini_games.cover_actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.gamedevgarage.Assets;

public class CoverImage extends Group {

    private final TextureRegionDrawable transparent;
    private final TextureRegionDrawable frame;

    private Image colorImage;
    private Image objectImage;
    private Image frameImage;

    public CoverImage(Assets assets) {
        this.transparent = assets.transparent;
        this.frame = assets.frame;

        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3f * 1.2f);

        createUIElements();

        addActor(colorImage);
        addActor(objectImage);
        addActor(frameImage);
    }

    private void createUIElements() {
        colorImage = new Image(transparent);
        objectImage = new Image(transparent);
        frameImage = new Image(frame);

        float leftPad = getWidth() / 5f;
        float topColorPad = getHeight() / 30f;

        float leftObjectPad = getWidth() / 4f;
        float topObjectPad = getHeight() / 15f;

        float frameTopPad = getHeight() / 36f;

        colorImage.setBounds(leftPad, topColorPad,
                getWidth() - (leftPad * 2), getHeight() - (topColorPad * 2));
        objectImage.setBounds(leftObjectPad, topObjectPad,
                getWidth() - (leftObjectPad * 2), getHeight() - (topObjectPad * 2));
        frameImage.setBounds(leftPad, frameTopPad,
                getWidth() - (leftPad * 2), getHeight() - (frameTopPad * 2));
    }

    public Image getColorImage() {
        return colorImage;
    }

    public Image getObjectImage() {
        return objectImage;
    }
}
