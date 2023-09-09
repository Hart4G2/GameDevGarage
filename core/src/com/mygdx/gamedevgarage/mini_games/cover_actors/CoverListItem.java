package com.mygdx.gamedevgarage.mini_games.cover_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.Assets;

public class CoverListItem extends Group {

    private Skin skin;
    private final Drawable item;
    private Image image;
    private Table table;
    private final String text;
    private boolean isPurchased;
    private Drawable background;

    public CoverListItem(String text, Drawable item, Drawable background, Drawable imageBackground) {
        this.skin = Assets.getInstance().getSkin();
        this.text = text;
        this.item = item;
        this.background = background;

        initColorUIElements(imageBackground);
    }

    public CoverListItem(String text, Drawable item) {
        this.text = text;
        this.item = item;

        initObjectUIElements();
    }

    private void initColorUIElements(Drawable imageBackground){
        Label label = new Label(text, skin);
        image = new Image(item);

        Image backgroundImage = new Image(imageBackground);

        float frameSize = getHeightPercent(.13f);
        float colorPad = frameSize * .03f;
        float colorSize = frameSize - (colorPad * 2);

        image.setBounds(colorPad, colorPad,
                colorSize, colorSize);
        backgroundImage.setSize(frameSize, frameSize);

        Group imageGroup = new Group();
        imageGroup.addActor(backgroundImage);
        imageGroup.addActor(image);

        table = new Table();
        table.setFillParent(true);
        table.setBackground(background);
        table.add(label).left().width(getWidthPercent(.4f)).height(getHeightPercent(.15f));
        table.add(imageGroup).right().width(frameSize).height(frameSize);

        addActor(table);
    }

    private void initObjectUIElements(){
        image = new Image(item);

        float size = getWidthPercent(.21f);

        image.setSize(size, size);

        table = new Table();
        table.setFillParent(true);
        table.add(image).width(size).height(size)
                .pad(10);

        addActor(table);
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public void setBackground(Drawable background) {
        this.background = background;
        table.setBackground(background);
    }
}
