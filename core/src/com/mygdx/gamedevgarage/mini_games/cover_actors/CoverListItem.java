package com.mygdx.gamedevgarage.mini_games.cover_actors;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class CoverListItem extends Group {

    private Label label;
    private Image image;
    private String text;

    public CoverListItem(String text, Drawable drawable, Skin skin) {
        this.text = text;
        label = new Label(text, skin);
        image = new Image(drawable);
    }

    public CoverListItem(String text, Drawable drawable) {
        this.text = text;
        image = new Image(drawable);
    }

    public Label getLabel() {
        return label;
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
