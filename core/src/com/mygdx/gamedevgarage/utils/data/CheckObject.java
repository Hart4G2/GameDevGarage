package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.gamedevgarage.utils.stats.Cost;

import java.util.Arrays;

public class CheckObject extends Object {

    private final String description;
    private final TextureRegionDrawable frame1;
    private final TextureRegionDrawable frame2;
    private Animation<TextureRegionDrawable> animation;

    public CheckObject(String name, String bundleKey, String description, TextureRegionDrawable frame1, TextureRegionDrawable frame2, Cost cost) {
        super(name, bundleKey, cost);

        this.description = description;
        this.frame1 = frame1;
        this.frame2 = frame2;

        initAnimation();
    }

    private void initAnimation(){
        float frameDuration = 1f;

        animation = new Animation<>(frameDuration, frame1, frame2);
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public String getDescription() {
        return description;
    }

    public Animation<TextureRegionDrawable> getAnimation() {
        return animation;
    }

    public String toJson() {
        return "{" +
                "\"name\": \"" + getName() + "\"," +
                "\"description\": \"" + getDescription() + "\"," +
                "\"frame1\": \"" + getName().replace(" ", "_").toLowerCase() + "_1" + "\"," +
                "\"frame2\": \"" + getName().replace(" ", "_").toLowerCase() + "_2" + "\"," +
                "\"cost\": {" +
                "\"value\": " + Arrays.toString(getCost().getCosts()) + "," +
                "\"currency\": \"" + Arrays.toString(getCost().getCostNames()) + "\"" +
                "}" +
                "}";
    }
}
