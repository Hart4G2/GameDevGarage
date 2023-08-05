package com.mygdx.gamedevgarage.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Utils {

    public static Dialog createDialog(Skin skin){
        Dialog dialog = new Dialog("", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.setFillParent(true);
        return dialog;
    }

    public static Label createLabel(String text, Skin skin, String styleText){
        return new Label(text, skin, styleText);
    }

    public static SelectBox<String> createSelectBox(Skin skin, String styleName, String[] items,
                                                    int align, String actorName, boolean hasScroll){
        SelectBox<String> selectBox = new SelectBox<>(skin, styleName);
        selectBox.setMaxListCount(10);
        selectBox.setAlignment(align);
        selectBox.setItems(items);
        selectBox.setName(actorName);
        selectBox.setScrollingDisabled(!hasScroll);
        selectBox.getList().setAlignment(align);
        return selectBox;
    }

    public static TextField createTextField(String text, Skin skin, String styleName, int align, String actorName){
        TextField textField = new TextField(text, skin, styleName);
        textField.setAlignment(align);
        textField.setName(actorName);
        return textField;
    }

    public static TextButton createTextButton(String text, Skin skin, String styleName, String actorName){
        TextButton textButton = new TextButton(text, skin, styleName);
        textButton.setName(actorName);
        return textButton;
    }
}
