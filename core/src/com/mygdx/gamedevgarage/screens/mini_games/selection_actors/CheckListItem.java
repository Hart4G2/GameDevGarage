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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
    private final Drawable bgSelected;
    private final Drawable bgUnselected;
    private final Drawable imageFrameUnselected;
    private final Drawable imageFrameSelected;
    private float stateTime;
    private Animation<TextureRegionDrawable> animation;

    public CheckListItem(CheckObject checkObject) {
        this.skin = Assets.getInstance().getSkin();
        this.checkObject = checkObject;

        bgUnselected = skin.getDrawable("programming_item");
        bgSelected = skin.getDrawable("programming_item_selected");
        imageFrameUnselected = skin.getDrawable("item_image_bg");
        imageFrameSelected = skin.getDrawable("item_image_bg_selected");

        initAnimation();
        initUIElements();
    }

    private void initAnimation(){
        animation = checkObject.getAnimation();

        stateTime = 0f;

        float imagePosition = getHeightPercent(.003f);
        float imageSize = getHeightPercent(0.1f) - (imagePosition * 2);

        image = new Image(animation.getKeyFrame(stateTime));
        image.setPosition(imagePosition, imagePosition);
        image.setSize(imageSize, imageSize);
    }

    private void initUIElements(){
        headerLabel = createLabel(checkObject.getName(), "black_18");
        descriptionLabel = createLabel(checkObject.getDescription(), "default");

        float imageSize = getHeightPercent(0.1f);

        backgroundImage = new Image(imageFrameUnselected);
        backgroundImage.setFillParent(true);

        Group imageGroup = new Group();
        imageGroup.addActor(image);
        imageGroup.addActor(backgroundImage);

        Table textTable = new Table();
        textTable.add(headerLabel)
                .colspan(2).center().row();
        textTable.add(descriptionLabel);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setBackground(bgUnselected);
        mainTable.add(textTable).left().width(getWidthPercent(.32f)).height(getHeightPercent(.15f))
                .padRight(getWidthPercent(.04f)).padLeft(getWidthPercent(.08f));
        mainTable.add(imageGroup).right().width(imageSize).height(imageSize);

        addActor(mainTable);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setUnselected() {
        isSelected = false;
        mainTable.setBackground(bgUnselected);
        backgroundImage.setDrawable(imageFrameUnselected);
        headerLabel.setStyle(skin.get("black_18", Label.LabelStyle.class));
        descriptionLabel.setStyle(skin.get("default", Label.LabelStyle.class));
    }

    public void setSelected() {
        isSelected = true;
        mainTable.setBackground(bgSelected);
        backgroundImage.setDrawable(imageFrameSelected);
        headerLabel.setStyle(skin.get("white_18", Label.LabelStyle.class));
        descriptionLabel.setStyle(skin.get("white_16", Label.LabelStyle.class));
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
