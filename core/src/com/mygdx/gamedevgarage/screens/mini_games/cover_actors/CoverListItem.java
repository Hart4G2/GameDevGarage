package com.mygdx.gamedevgarage.screens.mini_games.cover_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.data.CoverObject;

public class CoverListItem extends Group {

    private Skin skin;
    private final CoverObject coverObject;
    private Image image;
    private Table table;
    private Drawable background;

    public CoverListItem(CoverObject coverObject, Drawable background, Drawable imageBackground) {
        this.skin = Assets.getInstance().getSkin();
        this.coverObject = coverObject;
        this.background = background;

        initColorUIElements(imageBackground);
    }

    public CoverListItem(CoverObject coverObject) {
        this.coverObject = coverObject;
        initObjectUIElements();
    }

    private void initColorUIElements(Drawable imageBackground){
        Label headerLabel = Utils.createLabel(coverObject.getName(), "black_18", true);
        image = new Image(coverObject.getItem());

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
        table.add(headerLabel).left().width(getWidthPercent(.4f)).height(getHeightPercent(.15f));
        table.add(imageGroup).right().width(frameSize).height(frameSize);

        addActor(table);
    }

    private void initObjectUIElements(){
        image = new Image(coverObject.getItem());

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

    public void setBackground(Drawable background) {
        this.background = background;
        table.setBackground(background);
    }

    public CoverObject getCoverObject() {
        return coverObject;
    }
}
