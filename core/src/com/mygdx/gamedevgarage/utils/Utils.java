package com.mygdx.gamedevgarage.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.stats.StatsTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static Dialog createDialog(){
        Dialog dialog = new Dialog("", Assets.getInstance().getSkin());
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.setFillParent(true);
        return dialog;
    }

    public static ProgressBar createProgressBar(int min, int max){
        return new ProgressBar(min, max, 1,
                false, Assets.getInstance().getSkin(), "default-horizontal");
    }

    public static Label createLabel(String text, String styleText){
        return new Label(text, Assets.getInstance().getSkin(), styleText);
    }

    public static SelectBox<String> createSelectBox(String styleName, String[] items,
                                                    int align, String actorName, boolean hasScroll){
        SelectBox<String> selectBox = new SelectBox<>(Assets.getInstance().getSkin(), styleName);
        selectBox.setMaxListCount(10);
        selectBox.setAlignment(align);
        selectBox.setItems(items);
        selectBox.setName(actorName);
        selectBox.setScrollingDisabled(!hasScroll);
        selectBox.getList().setAlignment(align);
        return selectBox;
    }

    public static TextField createTextField(String text, String styleName, int align){
        TextField textField = new TextField(text, Assets.getInstance().getSkin(), styleName);
        textField.setAlignment(align);
        return textField;
    }

    public static TextField createTextField(String text, String styleName, int align, String actorName){
        TextField textField = new TextField(text, Assets.getInstance().getSkin(), styleName);
        textField.setAlignment(align);
        textField.setName(actorName);
        return textField;
    }

    public static TextButton createTextButton(String text, String styleName, String actorName){
        TextButton textButton = new TextButton(text, Assets.getInstance().getSkin(), styleName);
        textButton.setName(actorName);
        createClickListener(Game.getInstance(), textButton, "button_click");
        return textButton;
    }

    public static TextButton createTextButton(String text, String styleName){
        TextButton textButton = new TextButton(text, Assets.getInstance().getSkin(), styleName);
        createClickListener(Game.getInstance(), textButton, "button_click");
        return textButton;
    }

    public static TextButton createBuyButton(String text, String name){
        TextButton button = new TextButton(text, Assets.getInstance().getSkin(), "buy_button");
        button.setName(name);
        return button;
    }

    public static Button createButton(String styleName){
        Button button = new Button(Assets.getInstance().getSkin(), styleName);
        createClickListener(Game.getInstance(), button, "button_click");
        return button;
    }

    private static void createClickListener(final Game game, final Button button, final String soundName) {
        button.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               if(!button.isDisabled()){
                   Assets.getInstance().setSound(soundName);
               }
               else {
                   Assets.getInstance().setSound("platform_unavailable");
               }
           }
       });
    }

    public static boolean isInArray(String[] words, String word){
        for(String w : words){
            if(w.equals(word)){
                return true;
            }
        }
        return false;
    }

    public static StatsTable createStatsTable(){
        return new StatsTable();
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

    public static Color convertColor(String colorName) {
        switch (colorName) {
            case "Light Blue":
                return Color.valueOf("c4c8ff");
            case "Dark Blue":
                return Color.valueOf("1b2172");
            case "Blue":
                return Color.valueOf("747dff");
            case "Red":
                return Color.valueOf("ff5757");
            case "Light Red":
                return Color.valueOf("ffa1a1");
            case "Dark Red":
                return Color.valueOf("ab2121");
            case "Yellow":
                return Color.valueOf("ffd856");
            case "Dark Yellow":
                return Color.valueOf("c79900");
            case "Sandy":
                return Color.valueOf("f6e5ae");
            case "Orange":
                return Color.valueOf("ff8400");
            case "Light Purple":
                return Color.valueOf("d6a0ff");
            case "Dark Purple":
                return Color.valueOf("8520d2");
            case "Purple":
                return Color.valueOf("a938ff");
            case "Light Pink":
                return Color.valueOf("ff95ef");
            case "Pink":
                return Color.valueOf("ff4de4");
            case "Light Brown":
                return Color.valueOf("a64a4a");
            case "Dark Brown":
                return Color.valueOf("722121");
            case "Brown":
                return Color.valueOf("a52a2a");
            case "Black":
                return Color.valueOf("000000");
            case "White":
                return Color.valueOf("ffffff");
            case "Light Grey":
                return Color.valueOf("c5c5c5");
            case "Dark Grey":
                return Color.valueOf("4c4c4c");
            case "Grey":
                return Color.valueOf("8d8d8d");
            case "Dark Green":
                return Color.valueOf("236c15");
            case "Light Green":
                return Color.valueOf("72da5e");
            default:
                return Color.valueOf("48ad34");
        }
    }

    public static boolean isColorDark(String colorName) {
        List<String> darkColors = new ArrayList<>(Arrays.asList(
                "Dark Blue", "Dark Red", "Dark Yellow", "Dark Purple", "Dark Brown", "Purple",
                "Light Brown", "Brown", "Black", "Dark Grey", "Grey", "Dark Green", "Green"));

        return darkColors.contains(colorName);
    }
}
