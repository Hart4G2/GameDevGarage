package com.mygdx.gamedevgarage.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.stats.StatsTable;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static TextField createTextField(String text, Skin skin, String styleName, int align){
        TextField textField = new TextField(text, skin, styleName);
        textField.setAlignment(align);
        return textField;
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

    public static TextButton createTextButton(String text, Skin skin, String styleName){
        return new TextButton(text, skin, styleName);
    }

    public static TextButton createBuyButton(String text, Skin skin, String name){
        TextButton button = new TextButton(text, skin, "buy_button");
        button.setName(name);
        return button;
    }

    public static Button createButton(Skin skin, String styleName){
        return new Button(skin, styleName);
    }

    public static boolean isInArray(String[] words, String word){
        for(String w : words){
            if(w.equals(word)){
                return true;
            }
        }
        return false;
    }

    public static StatsTable createStatsTable(Game game){
        return new StatsTable(game.getAssets().getSkin(), game.stats);
    }

    public static float getWidthPercent(float percent) {
        return Gdx.graphics.getWidth() * percent;
    }

    public static float getHeightPercent(float percent) {
        return Gdx.graphics.getHeight() * percent;
    }

    public static ArrayList<String> convertStringToList(String input) {
        String cleanedInput = input.substring(1, input.length() - 1).trim();

        String[] elements = cleanedInput.split(",");
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].trim();
        }

        return new ArrayList<>(Arrays.asList(elements));
    }
}
