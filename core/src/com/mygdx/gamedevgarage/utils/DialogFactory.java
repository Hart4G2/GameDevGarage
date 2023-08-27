package com.mygdx.gamedevgarage.utils;

import static com.mygdx.gamedevgarage.utils.Utils.*;
import static com.mygdx.gamedevgarage.utils.Utils.createDialog;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createSelectBox;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.createTextField;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;

public class DialogFactory {

    public static Dialog createMakeGameDialog(Game game) {
        Assets assets = game.getAssets();
        Skin skin = assets.getSkin();

        final Dialog dialog = createDialog(skin);

        // content table
        String[] genres = game.reward.genres;

        String[] themes = game.reward.themes;

        String[] gameLevels = game.reward.gameLevels;

        final TextField nameTextField = createTextField(null, skin, "white_24",
                Align.center, "nameTextField");

        final SelectBox<String> genreSelectBox = createSelectBox(skin, "default",
                genres, Align.center, "genreSelectBox", true);
        final SelectBox<String> themesSelectBox = createSelectBox(skin, "default",
                themes, Align.center, "themesSelectBox", true);
        final SelectBox<String> levelSelectBox = createSelectBox(skin, "default",
                gameLevels, Align.center, "levelSelectBox", false);

        Label headerLabel = createLabel("Create Game", skin, "black_32");
        Label nameLabel = createLabel("Name:", skin, "black_24");
        Label genreLabel = createLabel("Genre:", skin, "black_24");
        Label platformLabel = createLabel("Theme:", skin, "black_24");
        Label levelLabel = createLabel("Level:", skin, "black_24");

        float labelWidth = getWidthPercent(.2f);
        float labelHeight = getHeightPercent(.05f);
        float labelPad = getWidthPercent(.05f);

        float contentWidth = getWidthPercent(.5f);
        float contentHeight = getHeightPercent(.05f);
        float contentPad = getWidthPercent(.05f);

        genreSelectBox.setSize(contentWidth, contentHeight);
        themesSelectBox.setSize(contentWidth, contentHeight);
        levelSelectBox.setSize(contentWidth, contentHeight);

        Table table = new Table(skin);
        table.add(nameLabel).width(labelWidth).height(labelHeight)
                .pad(labelPad).padLeft(getWidthPercent(.1f));
        table.add(nameTextField).width(contentWidth).height(contentHeight)
                .pad(contentPad).padRight(getWidthPercent(.1f)).row();
        table.add(genreLabel).width(labelWidth).height(labelHeight)
                .pad(labelPad).padLeft(getWidthPercent(.1f));
        table.add(genreSelectBox).width(genreSelectBox.getWidth()).height(contentHeight)
                .pad(contentPad).padRight(getWidthPercent(.1f)).row();
        table.add(platformLabel).width(labelWidth).height(labelHeight)
                .pad(labelPad).padLeft(getWidthPercent(.1f));
        table.add(themesSelectBox).width(contentWidth).height(contentHeight)
                .pad(contentPad).padRight(getWidthPercent(.1f)).row();
        table.add(levelLabel).width(labelWidth).height(labelHeight)
                .pad(labelPad).padLeft(getWidthPercent(.1f));
        table.add(levelSelectBox).width(contentWidth).height(contentHeight)
                .pad(contentPad).padRight(getWidthPercent(.1f)).row();

        Table contentTable = new Table(skin);
        contentTable.setFillParent(true);
        contentTable.add(headerLabel).width(getWidthPercent(.3f)).height(getHeightPercent(.3f))
                .row();
        contentTable.add(table).row();

        dialog.getContentTable().add(contentTable);

        // button table
        float buttonWidth = getWidthPercent(.35f);
        float buttonHeight = getHeightPercent(.08f);
        float buttonPad = getWidthPercent(.03f);

        TextButton okButton = createTextButton("OK", skin, "white_18", "okButton");
        TextButton cancelButton = createTextButton("Cancel", skin, "white_18", "cancelButton");

        Table buttonTable = dialog.getButtonTable();

        buttonTable.add(okButton).width(buttonWidth).height(buttonHeight)
                .pad(buttonPad).padBottom(getHeightPercent(.1f));
        buttonTable.add(cancelButton).width(buttonWidth).height(buttonHeight)
                .pad(buttonPad).padBottom(getHeightPercent(.1f));

        return dialog;
    }
}
