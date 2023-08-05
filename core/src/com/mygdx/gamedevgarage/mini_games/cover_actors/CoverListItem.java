package com.mygdx.gamedevgarage.mini_games.cover_actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class CoverListItem extends Group {

    private Skin skin;
    private final Drawable item;
    private Image image;
    private final String text;

    public CoverListItem(String text, Drawable item, Drawable background, Drawable imageBackground, Skin skin) {
        this.skin = skin;
        this.text = text;
        this.item = item;

        setSize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 7f);

        initColorUIElements(background, imageBackground);
    }

    public CoverListItem(String text, Drawable item) {
        this.text = text;
        this.item = item;

        setSize(100, 100);

        initObjectUIElements();
    }

    private void initColorUIElements(Drawable background, Drawable imageBackground){
        Label label = new Label(text, skin);
        image = new Image(item);
        image.setBounds(5, 5, 100, 100);

        Image backgroundImage = new Image(imageBackground);

        Group imageGroup = new Group();
        imageGroup.addActor(backgroundImage);
        imageGroup.addActor(image);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(background);
        table.add(label).left().width(getWidth() - Gdx.graphics.getWidth() / 4f);
        table.add(imageGroup).right().padTop(110);

        addActor(table);
    }

    private void initObjectUIElements(){
        image = new Image(item);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(image).padRight(5);

        addActor(mainTable);
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
