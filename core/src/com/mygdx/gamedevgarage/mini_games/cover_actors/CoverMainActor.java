package com.mygdx.gamedevgarage.mini_games.cover_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.DesignScreen;

public class CoverMainActor extends Table {

    private final Game game;
    private final DesignScreen screen;

    private CoverImage coverImage;
    private CoverList colorList;
    private CoverList objectList;
    private ScrollPane colorScrollPane;
    private ScrollPane objectScrollPane;
    private Button backButton;

    private String selectedColor;

    public CoverMainActor(Game game, DesignScreen screen) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.screen = screen;

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements() {
        backButton = createButton(getSkin(), "back_button");
        backButton.setSize(getWidthPercent(.18f), getWidthPercent(.18f));
        backButton.setVisible(false);

        coverImage = new CoverImage(game.getAssets());

        Image colorImage = coverImage.getColorImage();
        Image objectImage = coverImage.getObjectImage();

        colorList = new CoverList(game, colorImage, true, this);
        objectList = new CoverList(game, objectImage, false, this);

        colorScrollPane = new ScrollPane(colorList, getSkin());
        colorScrollPane.setFillParent(true);
        colorScrollPane.setScrollbarsVisible(true);

        objectScrollPane = new ScrollPane(objectList, getSkin());
        objectScrollPane.setFillParent(true);
        objectScrollPane.setVisible(false);
        objectScrollPane.setScrollbarsVisible(true);

        Group group = new Group();
        group.addActor(objectScrollPane);
        group.addActor(colorScrollPane);
        group.addActor(backButton);

        add(coverImage).width(getHeightPercent(.35f)).height(getHeightPercent(.35f))
                .pad(getHeightPercent(.01f))
                .center().row();
        add(group).width(getWidthPercent(.95f)).height(getHeightPercent(.4f))
                .center().row();
    }

    private void setupUIListeners() {
        colorList.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                colorScrollPane.setVisible(false);
                objectScrollPane.setVisible(true);
                backButton.setVisible(true);
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                colorScrollPane.setVisible(true);
                objectScrollPane.setVisible(false);
                backButton.setVisible(false);
            }
        });
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setSelectedObject(String selectedObject) {
        screen.setSelectedCoverItems(selectedColor, selectedObject);
    }
}
