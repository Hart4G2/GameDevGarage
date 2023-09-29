package com.mygdx.gamedevgarage.utils;

import static com.mygdx.gamedevgarage.utils.Utils.createDialog;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createSelectBox;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.createTextField;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;

public class DialogFactory {

    public static Dialog createMakeGameDialog() {
        I18NBundle bundle = Assets.getInstance().myBundle;
        Skin skin = Assets.getInstance().getSkin();
        final Dialog dialog = createDialog();

        // content table
        String[] genres = DataArrayFactory.getGenresAsStringArray();
        String[] themes = DataArrayFactory.getThemesAsStringArray();

        final TextField nameTextField = createTextField(null, "default",
                Align.center, "nameTextField");

        final SelectBox<String> genreSelectBox = createSelectBox("default", genres, Align.center, "genreSelectBox", true);
        final SelectBox<String> themesSelectBox = createSelectBox("default", themes, Align.center, "themesSelectBox", true);

        Label headerLabel = createLabel(bundle.get("Create_game"), "black_32", false);
        Label nameLabel = createLabel(bundle.get("Name"), "black_24", false);
        Label genreLabel = createLabel(bundle.get("Genre"), "black_24", false);
        Label platformLabel = createLabel(bundle.get("Theme"), "black_24", false);

        float labelWidth = getWidthPercent(.2f);
        float labelHeight = getHeightPercent(.05f);
        float labelPad = getWidthPercent(.05f);

        float contentWidth = getWidthPercent(.5f);
        float contentHeight = getHeightPercent(.05f);
        float contentPad = getWidthPercent(.05f);

        genreSelectBox.setSize(contentWidth, contentHeight);
        themesSelectBox.setSize(contentWidth, contentHeight);

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

        TextButton okButton = createTextButton(bundle.get("ok"), "white_18", "okButton");
        TextButton cancelButton = createTextButton(bundle.get("Cancel"), "white_18", "cancelButton");

        Table buttonTable = dialog.getButtonTable();

        buttonTable.add(okButton).width(buttonWidth).height(buttonHeight)
                .pad(buttonPad).padBottom(getHeightPercent(.1f));
        buttonTable.add(cancelButton).width(buttonWidth).height(buttonHeight)
                .pad(buttonPad).padBottom(getHeightPercent(.1f));

        return dialog;
    }
}
