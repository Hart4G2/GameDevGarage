package com.mygdx.gamedevgarage.mini_games.selection_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

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
    private boolean isPurchased;

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

        initAnimation();
        initUIElements();
    }

    private void initAnimation(){
        float frameDuration = 0.5f;

        animation = new Animation<>(frameDuration, frame1, frame2);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        stateTime = 0f;

        float imagePosition = getHeightPercent(.0025f);
        float imageSize = getHeightPercent(0.0975f);

        image = new Image(animation.getKeyFrame(stateTime));
        image.setPosition(imagePosition, imagePosition);
        image.setSize(imageSize, imageSize);
    }

    private void initUIElements(){
        label = createLabel(text, skin, "default");

        float imageSize = getHeightPercent(0.1f);

        backgroundImage = new Image(imageFrameUnselected);
        backgroundImage.setSize(imageSize, imageSize);

        Group imageGroup = new Group();
        imageGroup.addActor(image);
        imageGroup.addActor(backgroundImage);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setBackground(bgUnselected);
        mainTable.add(label).left().width(getWidthPercent(.32f)).height(getHeightPercent(.15f))
                .padRight(getWidthPercent(.04f)).padLeft(getWidthPercent(.08f));
        mainTable.add(imageGroup).right().width(imageSize).height(imageSize);

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
        label.setStyle(skin.get("black_18", Label.LabelStyle.class));
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

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
