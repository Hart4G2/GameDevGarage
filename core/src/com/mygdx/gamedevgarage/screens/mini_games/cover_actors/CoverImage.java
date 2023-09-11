package com.mygdx.gamedevgarage.screens.mini_games.cover_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;

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

    public CoverImage() {
        Assets assets = Assets.getInstance();

        this.transparent = assets.transparent;
        this.frame = assets.frame;

        createUIElements();

        addActor(colorImage);
        addActor(objectImage);
        addActor(frameImage);
    }

    private void createUIElements() {
        colorImage = new Image(transparent);
        objectImage = new Image(transparent);
        frameImage = new Image(frame);

        float frameSize = getHeightPercent(.35f);
        float colorPad = frameSize * .02f;
        float colorSize = frameSize - (colorPad * 2);
        float objectPad = frameSize * .1f;
        float objectSize = frameSize - (objectPad * 2);

        colorImage.setBounds(colorPad, colorPad,
                colorSize, colorSize);
        objectImage.setBounds(objectPad, objectPad,
                objectSize, objectSize);
        frameImage.setSize(frameSize, frameSize);
    }

    public Image getColorImage() {
        return colorImage;
    }

    public Image getObjectImage() {
        return objectImage;
    }
}
