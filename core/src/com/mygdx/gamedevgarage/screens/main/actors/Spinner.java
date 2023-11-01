package com.mygdx.gamedevgarage.screens.main.actors;

import static com.mygdx.gamedevgarage.utils.Utils.createScalingImage;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.gamedevgarage.screens.TableActor;

public class Spinner extends TableActor {

    private Image image;
    private Animation<TextureRegionDrawable> animation;
    private float stateTime = 0;
    private boolean animationPaused;

    public Spinner() {
        initAnimation();
        createUIElements();
    }

    private void initAnimation(){
        TextureAtlas textureAtlas = assets.progressBarAtlas;

        Array<TextureRegionDrawable> frames = new Array<>();

        for(int i = 1; i <= textureAtlas.getRegions().size; i++) {
            frames.add(new TextureRegionDrawable(textureAtlas.findRegion(String.valueOf(i))));
        }

        animation = new Animation<>(.09f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    protected void createUIElements() {
        image = createScalingImage(animation.getKeyFrame(stateTime), Scaling.fit);

        float size = getWidthPercent(.1f);
        add(image).width(size).height(size);
    }

    public void render(float delta) {
        if(!animationPaused) {
            stateTime += delta;

            TextureRegionDrawable currentFrame = animation.getKeyFrame(stateTime);
            image.setDrawable(currentFrame);
        }
    }

    public void start(){
        stateTime = 0;
        animationPaused = false;
        image.setVisible(true);
    }

    public void stop(){
        animationPaused = true;
        image.setVisible(false);
    }
}
