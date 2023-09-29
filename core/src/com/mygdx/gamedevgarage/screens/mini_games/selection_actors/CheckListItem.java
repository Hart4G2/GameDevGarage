package com.mygdx.gamedevgarage.screens.mini_games.selection_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.data.CheckObject;

public class CheckListItem extends Group {

    private final Skin skin;
    private final CheckObject checkObject;
    private Label headerLabel;
    private Label descriptionLabel;
    private Image image;
    private Image backgroundImage;
    private boolean isSelected;
    private Table mainTable;

    private final String bgSelected;
    private final String bgUnselected;
    private final String imageFrameUnselected;
    private final String imageFrameSelected;
    private float stateTime;
    private Animation<TextureRegionDrawable> animation;

    public CheckListItem(CheckObject checkObject, boolean isTech) {
        this.skin = Assets.getInstance().getSkin();
        this.checkObject = checkObject;

        if(isTech){
            bgUnselected = "programming_item";
            bgSelected = "programming_item_selected";
        } else {
            bgUnselected = "game_design_item";
            bgSelected = "game_design_item_selected";
        }

        imageFrameUnselected = "item_image_bg";
        imageFrameSelected = "item_image_bg_selected";

        initAnimation();
        initUIElements();
    }

    private void initAnimation(){
        animation = checkObject.getAnimation();

        stateTime = 0f;

        float imagePosition = getHeightPercent(.006f);
        float imageSize = getHeightPercent(0.14f) - (imagePosition * 2);

        image = new Image(animation.getKeyFrame(stateTime));
        image.setPosition(imagePosition, imagePosition);
        image.setSize(imageSize, imageSize);
    }

    private void initUIElements(){
        headerLabel = createLabel(checkObject.getName(), "default", true);
        descriptionLabel = createLabel(checkObject.getDescription(), "black_italic_14", true);

        float imageSize = getHeightPercent(.14f);

        backgroundImage = new Image(skin.getDrawable(imageFrameUnselected));
        backgroundImage.setFillParent(true);

        Group imageGroup = new Group();
        imageGroup.addActor(image);
        imageGroup.addActor(backgroundImage);

        float labelSize = checkObject.isPurchased() ? getWidthPercent(.6f) : getWidthPercent(.4f);

        Table textTable = new Table();
        textTable.add(headerLabel).width(labelSize)
                .colspan(2).center().row();
        textTable.add(descriptionLabel).width(labelSize)
                .colspan(2).left();

        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.setBackground(bgUnselected);
        mainTable.add(textTable).width(labelSize).height(getHeightPercent(.2f))
                .pad(getHeightPercent(.001f), getWidthPercent(.03f), 0, getWidthPercent(.01f))
                .left();
        mainTable.add(imageGroup).width(imageSize).height(imageSize)
                .right();

        addActor(mainTable);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setUnselected() {
        isSelected = false;
        mainTable.setBackground(bgUnselected);
        backgroundImage.setDrawable(skin, imageFrameUnselected);
        headerLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        descriptionLabel.setStyle(skin.get("black_italic_14", Label.LabelStyle.class));
    }

    public void setSelected() {
        isSelected = true;
        mainTable.setBackground(bgSelected);
        backgroundImage.setDrawable(skin, imageFrameSelected);
        headerLabel.setStyle(skin.get("white_16", Label.LabelStyle.class));
        descriptionLabel.setStyle(skin.get("white_italic_14", Label.LabelStyle.class));
    }

    public void render(float delta) {
        stateTime += delta;

        TextureRegionDrawable currentFrame = animation.getKeyFrame(stateTime);

        image.setDrawable(currentFrame);
    }

    public CheckObject getCheckObject() {
        return checkObject;
    }
}
