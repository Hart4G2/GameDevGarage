package com.mygdx.gamedevgarage.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

public class DialogFactory {

    public static Dialog createMakeGameDialog(Skin skin) {
        final Dialog dialog = new Dialog("", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.setFillParent(true);

        final TextField nameTextField = new TextField("", skin, "white_24");

        final SelectBox<String> genreSelectBox = new SelectBox<>(skin);
        genreSelectBox.setItems("Shooter", "Strategy", "Arcade");

        final SelectBox<String> platformSelectBox = new SelectBox<>(skin);
        platformSelectBox.setItems("PC", "PlayStation", "Xbox", "Nintendo Switch");

        final SelectBox<String> levelSelectBox = new SelectBox<>(skin);
        levelSelectBox.setItems("Flash game", "AA game", "AAA game");

        Label headerLabel = new Label("Create Game", skin, "default_32");
        Label nameLabel = new Label("Name:", skin, "default_24");
        Label genreLabel = new Label("Genre:", skin, "default_24");
        Label platformLabel = new Label("Platform:", skin, "default_24");
        Label levelLabel = new Label("Level:", skin, "default_24");

        dialog.getContentTable().add(headerLabel).align(Align.center).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(nameLabel).padRight(10f);
        dialog.getContentTable().add(nameTextField).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(genreLabel).padRight(10f);
        dialog.getContentTable().add(genreSelectBox).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(platformLabel).padRight(10f);
        dialog.getContentTable().add(platformSelectBox).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(levelLabel).padRight(10f);
        dialog.getContentTable().add(levelSelectBox).row();
        dialog.getContentTable().row();

        TextButton okButton = new TextButton("OK", skin, "white_18");
        TextButton cancelButton = new TextButton("Cancel", skin, "white_18");

        okButton.setName("okButton");
        cancelButton.setName("cancelButton");
        nameTextField.setName("nameTextField");
        genreSelectBox.setName("genreSelectBox");
        platformSelectBox.setName("platformSelectBox");
        levelSelectBox.setName("levelSelectBox");

        okButton.setSize(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 30f);
        cancelButton.setSize(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 30f);

        dialog.getButtonTable().add(okButton);
        dialog.getButtonTable().add(cancelButton);

        return dialog;
    }

    public static Dialog createGameDesignDialog(Skin skin) {
        final Dialog dialog = new Dialog("", skin, "purple");
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.setFillParent(true);

        final SelectBox<String> mechanicSelectBox = new SelectBox<>(skin);
        mechanicSelectBox.setItems("Mechanic 1", "Mechanic 2", "Mechanic 3");

        Label headerLabel = new Label("Choose mechanic", skin, "default_32");
        Label mechanicLabel = new Label("Mechanic:", skin, "default_24");

        dialog.getContentTable().add(headerLabel).align(Align.center).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(mechanicLabel).padRight(10f);
        dialog.getContentTable().add(mechanicSelectBox).row();
        dialog.getContentTable().row();

        TextButton okButton = new TextButton("OK", skin, "white_18");

        okButton.setName("okButton");
        mechanicSelectBox.setName("mechanicSelectBox");

        okButton.setSize(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 30f);

        dialog.getButtonTable().add(okButton);

        return dialog;
    }

    public static Dialog createDesignDialog(Skin skin) {
        final Dialog dialog = new Dialog("", skin, "yellow");
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.setFillParent(true);

        final SelectBox<String> graphicSelectBox = new SelectBox<>(skin);
        graphicSelectBox.setItems("Graphic 1", "Graphic 2", "Graphic 3");

        Label headerLabel = new Label("Choose graphic", skin, "default_32");
        Label mechanicLabel = new Label("Graphic:", skin, "default_24");

        dialog.getContentTable().add(headerLabel).align(Align.center).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(mechanicLabel).padRight(10f);
        dialog.getContentTable().add(graphicSelectBox).row();
        dialog.getContentTable().row();

        TextButton okButton = new TextButton("OK", skin, "white_18");

        okButton.setName("okButton");
        graphicSelectBox.setName("graphicSelectBox");

        okButton.setSize(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 30f);

        dialog.getButtonTable().add(okButton);

//        final Dialog dialog = new DesignDialog(stage, "", skin, "yellow");
//        dialog.setModal(true);
//        dialog.setMovable(false);
//        dialog.setResizable(false);
//        dialog.setFillParent(true);

        return dialog;
    }

    public static Dialog createProgrammingDialog(Skin skin) {
        final Dialog dialog = new Dialog("", skin, "blue");
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.setFillParent(true);

        final SelectBox<String> technicSelectBox = new SelectBox<>(skin);
        technicSelectBox.setItems("Technic 1", "Technic 2", "Technic 3");

        Label headerLabel = new Label("Choose technic", skin, "default_32");
        Label mechanicLabel = new Label("Technic:", skin, "default_24");

        dialog.getContentTable().add(headerLabel).align(Align.center).row();
        dialog.getContentTable().row();
        dialog.getContentTable().add(mechanicLabel).padRight(10f);
        dialog.getContentTable().add(technicSelectBox).row();
        dialog.getContentTable().row();

        TextButton okButton = new TextButton("OK", skin, "white_18");

        okButton.setName("okButton");
        technicSelectBox.setName("technicSelectBox");

        okButton.setSize(Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 30f);

        dialog.getButtonTable().add(okButton);

        return dialog;
    }
}
