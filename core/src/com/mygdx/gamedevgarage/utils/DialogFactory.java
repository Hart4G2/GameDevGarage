package com.mygdx.gamedevgarage.utils;

import static com.mygdx.gamedevgarage.utils.Utils.createDialog;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createSelectBox;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.createTextField;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

public class DialogFactory {

    public static Dialog createMakeGameDialog(Skin skin) {
        final Dialog dialog = createDialog(skin);

        // content table
        String[] genres = new String[]{
                "Shooter", "Strategy", "Arcade", "RPG", "Racing", "Sports", "Misc",
                "Action", "Adventure", "Simulation", "Platformer", "Card Game", "Board Game"
        };

        String[] platforms = new String[]{
                "PC", "PlayStation", "Xbox", "Nintendo Switch", "Mobile"
        };

        String[] levels = new String[]{
                "Flash game", "AA game", "AAA game"
        };

        final TextField nameTextField = createTextField(null, skin, "white_24",
                Align.center, "nameTextField");

        final SelectBox<String> genreSelectBox = createSelectBox(skin, "default",
                genres, Align.center, "genreSelectBox", true);
        final SelectBox<String> platformSelectBox = createSelectBox(skin, "default",
                platforms, Align.center, "platformSelectBox", true);
        final SelectBox<String> levelSelectBox = createSelectBox(skin, "default",
                levels, Align.center, "levelSelectBox", false);

        Label headerLabel = createLabel("Create Game", skin, "default_32");
        Label nameLabel = createLabel("Name:", skin, "default_24");
        Label genreLabel = createLabel("Genre:", skin, "default_24");
        Label platformLabel = createLabel("Platform:", skin, "default_24");
        Label levelLabel = createLabel("Level:", skin, "default_24");

        Table table = new Table(skin);
        table.add(nameLabel).pad(100, 10, 40, 10);
        table.add(nameTextField).pad(100, 10, 40, 10).width(genreSelectBox.getWidth()).height(genreSelectBox.getHeight()).row();
        table.add(genreLabel).pad(10, 10, 40, 10);
        table.add(genreSelectBox).pad(10, 10, 40, 10).row();
        table.add(platformLabel).pad(10, 10, 40, 10);
        table.add(platformSelectBox).pad(10, 10, 40, 10).row();
        table.add(levelLabel).pad(10, 10, 40, 10);
        table.add(levelSelectBox).pad(10, 10, 40, 10);

        Table contentTable = new Table(skin);
        contentTable.setFillParent(true);
        contentTable.add(headerLabel).pad(150, 200, 50, 200).row();
        contentTable.add(table).row();

        dialog.getContentTable().add(contentTable);

        // button table
        TextButton okButton = createTextButton("OK", skin, "white_18", "okButton");
        TextButton cancelButton = createTextButton("Cancel", skin, "white_18", "cancelButton");

        float buttonWidth = Gdx.graphics.getWidth() / 6f;
        float buttonHeight = Gdx.graphics.getHeight() / 30f;

        okButton.setSize(buttonWidth, buttonHeight);
        cancelButton.setSize(buttonWidth, buttonHeight);

        dialog.getButtonTable().add(okButton);
        dialog.getButtonTable().add(cancelButton);

        return dialog;
    }
}
