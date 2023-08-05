package com.mygdx.gamedevgarage.mini_games.selection_actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CheckListItem extends Group {

    private final Skin skin;
    private Label label;
    private Image image;
    private Image backgroundImage;
    private String text;
    private boolean isSelected;
    private Table mainTable;
    private Drawable bgSelected;
    private Drawable bgUnselected;
    private Drawable imageFrameUnselected;
    private Drawable imageFrameSelected;
    private Animation<TextureRegionDrawable> animation;
    private float stateTime;
    private TextureRegionDrawable frame1;
    private TextureRegionDrawable frame2;

    public CheckListItem(String text, TextureRegionDrawable frame1, TextureRegionDrawable frame2,
                         Drawable bgUnselected, Drawable bgSelected, Drawable imageFrameUnselected,
                         Drawable imageFrameSelected, Skin skin) {
        this.skin = skin;
        this.text = text;
        this.bgSelected = bgSelected;
        this.bgUnselected = bgUnselected;
        this.imageFrameUnselected = imageFrameUnselected;
        this.imageFrameSelected = imageFrameSelected;
        this.frame1 = frame1;
        this.frame2 = frame2;

        setSize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 6f);

        initAnimation();
        initUIElements();
    }

    private void initAnimation(){
        float frameDuration = 0.5f;

        animation = new Animation<>(frameDuration, frame1, frame2);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        stateTime = 0f;
        image = new Image(animation.getKeyFrame(stateTime));
        image.setPosition(0, 14);
    }

    private void initUIElements(){
        label = new Label(text, skin, "default_18");

        backgroundImage = new Image(imageFrameUnselected);
        backgroundImage.setPosition(-5, 9);

        Group imageGroup = new Group();
        imageGroup.addActor(backgroundImage);
        imageGroup.addActor(image);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setBackground(bgUnselected);
        mainTable.add(label).left().width(getWidth() - Gdx.graphics.getWidth() / 4f);
        mainTable.add(imageGroup).right().padTop(130);

        addActor(mainTable);
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setUnselected() {
        isSelected = false;
        mainTable.setBackground(bgUnselected);
        backgroundImage.setDrawable(imageFrameUnselected);
        label.setStyle(skin.get("default_18", Label.LabelStyle.class));
    }

    public void setSelected() {
        isSelected = true;
        mainTable.setBackground(bgSelected);
        backgroundImage.setDrawable(imageFrameSelected);
        label.setStyle(skin.get("white_18", Label.LabelStyle.class));
    }

    public void render(float delta) {
        stateTime += delta;

        TextureRegionDrawable currentFrame = animation.getKeyFrame(stateTime);

        image.setDrawable(currentFrame);
    }
}
