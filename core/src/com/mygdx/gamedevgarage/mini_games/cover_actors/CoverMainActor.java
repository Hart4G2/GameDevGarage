package com.mygdx.gamedevgarage.mini_games.cover_actors;

import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.DesignScreen;

public class CoverMainActor extends Table {

    private final Game game;
    private final Assets assets;
    private final DesignScreen screen;
    private final Skin skin;

    private CoverImage coverImage;
    private CoverList colorList;
    private CoverList objectList;
    private ScrollPane colorScrollPane;
    private ScrollPane objectScrollPane;
    private Button backButton;

    private String selectedColor;

    public CoverMainActor(Game game, DesignScreen screen) {
        this.game = game;
        this.screen = screen;
        this.assets = game.getAssets();
        this.skin = assets.getSkin();

        setSize(getWidthPercent(1), getHeightPercent(.8f));

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements() {
        backButton = createButton(skin, "back_button");
        backButton.setSize(getWidthPercent(.18f), getWidthPercent(.18f));
        backButton.setVisible(false);

        coverImage = new CoverImage(assets);
        coverImage.setSize(getWidthPercent(1f), getWidthPercent(1f));

        Image colorImage = coverImage.getColorImage();
        Image objectImage = coverImage.getObjectImage();

        colorList = new CoverList(game, colorImage, true, this, assets);
        objectList = new CoverList(game, objectImage, false, this,  assets);

        colorScrollPane = new ScrollPane(colorList, skin);
        colorScrollPane.setFillParent(true);
        colorScrollPane.setScrollbarsVisible(true);

        objectScrollPane = new ScrollPane(objectList, skin);
        objectScrollPane.setFillParent(true);
        objectScrollPane.setVisible(false);
        objectScrollPane.setScrollbarsVisible(true);

        Group group = new Group();
        group.addActor(objectScrollPane);
        group.addActor(colorScrollPane);
        group.addActor(backButton);

        add(coverImage).width(getHeightPercent(.4f)).height(getHeightPercent(.4f))
                .pad(getHeightPercent(.01f)).row();
        add(group).width(getWidthPercent(.95f)).height(getHeightPercent(.4f))
                .row();
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
