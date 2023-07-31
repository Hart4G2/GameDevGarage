package com.mygdx.gamedevgarage.mini_games.cover_actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.mini_games.CoverCreationScreen;

public class CoverMainActor extends Table {

    private final Assets assets;
    private final CoverCreationScreen screen;
    private final Skin skin;

    private CoverImage coverImage;
    private CoverList colorList;
    private CoverList objectList;
    private ScrollPane colorScrollPane;
    private ScrollPane objectScrollPane;
    private Button backButton;

    private Array<CoverListItem> colors;
    private Array<CoverListItem> objects;

    private String selectedColor;
    private String selectedObject;

    public CoverMainActor(CoverCreationScreen screen, Assets assets) {
        this.assets = assets;
        this.screen = screen;
        this.skin = assets.getSkin();

        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3f * 2f);

        createUIElements();
        setupUIListeners();

        Group group = new Group();
        group.addActor(objectScrollPane);
        group.addActor(colorScrollPane);
        group.addActor(backButton);
        group.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() / 2.4f);

        add(coverImage).pad(20).row();
        add(group).pad(20).row();
    }

    private void createUIElements() {
        backButton = new TextButton("", skin, "back_button");
        backButton.setVisible(false);

        coverImage = new CoverImage(assets);
        Image colorImage = coverImage.getColorImage();
        Image objectImage = coverImage.getObjectImage();

        initLists();

        colorList = new CoverList(colors, colorImage, true, this, assets);
        objectList = new CoverList(objects, objectImage, false, this,  assets);

        colorScrollPane = new ScrollPane(colorList, skin);
        colorScrollPane.setFillParent(true);
        colorScrollPane.setScrollbarsVisible(true);

        objectScrollPane = new ScrollPane(objectList, skin);
        objectScrollPane.setFillParent(true);
        objectScrollPane.setVisible(false);
        objectScrollPane.setScrollbarsVisible(true);
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

    private void initLists(){
        colors = initColors();
        objects = initObjects(assets.designObjects100Atlas);
    }

    private Array<CoverListItem> initColors() {
        Array<CoverListItem> colors = new Array<>();

        String[] colorNames = {
                "Light Blue", "Dark Blue", "Blue", "Red", "Light Red", "Dark Red", "Yellow", "Dark Yellow", "Sandy", "Orange",
                "Light Purple", "Dark Purple", "Purple", "Light Pink", "Pink", "Light Brown", "Dark Brown", "Brown",
                "Black", "White", "Light Grey", "Dark Grey", "Grey", "Dark Green", "Light Green", "Green"
        };

        String[] colorRegions = {
                "light_blue", "dark_blue", "blue", "red", "light_red", "dark_red", "yellow", "dark_yellow", "sandy", "orange",
                "light_purple", "dark_purple", "purple", "light_pink", "pink", "light_brown", "dark_brown", "brown",
                "black", "white", "light_grey", "dark_grey", "grey", "dark_green", "light_green", "green"
        };

        TextureRegionDrawable background = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_bg.png")));

        TextureRegionDrawable imageBackground = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg.png")));

        for (int i = 0; i < colorNames.length; i++) {
            TextureRegionDrawable drawable = new TextureRegionDrawable(assets.designColorsAtlas.findRegion(colorRegions[i]));
            colors.add(new CoverListItem(colorNames[i], drawable, background, imageBackground, skin));
        }

        return colors;
    }

    public Array<CoverListItem> initObjects(TextureAtlas atlas){
        Array<CoverListItem> objects = new Array<>();

        String[] objectNames = {
                "Aliens", "Aviation", "Business", "Cinema", "City", "Comedy", "Construction",
                "Cooking", "Criminal", "Cyberpunk", "Dance", "Detective", "Fantasy", "Farm",
                "Fashion", "Game development", "Government", "Hacker", "Horror", "Hospital",
                "Hunting", "Life", "Medieval", "Music", "Ninja", "Pirates", "Prison", "Race",
                "Romantic", "Rhythm", "School", "Space", "Sport", "Superheros", "Time traveling",
                "Transport", "Vampires", "Virtual animals", "War", "Wild west", "Zombie"
        };

        String[] objectRegions = {
                "aliens", "aviation", "business", "cinema", "city", "comedy", "construction",
                "cooking", "criminal", "cyberpunk", "dance", "detective", "fantasy", "farm",
                "fashion", "game_development", "government", "hacker", "horror", "hospital",
                "hunting", "life", "medieval", "music", "ninja", "pirates", "prison", "race",
                "romantic", "rhythm", "school", "space", "sport", "superheros", "time_travel",
                "transport", "vampires", "virtual_animal", "war", "westwood", "zombie"
        };

        for (int i = 0; i < objectNames.length; i++) {
            TextureRegionDrawable drawable1 = new TextureRegionDrawable(atlas.findRegion(objectRegions[i], 1));
            TextureRegionDrawable drawable2 = new TextureRegionDrawable(atlas.findRegion(objectRegions[i], 2));
            objects.add(new CoverListItem(objectNames[i] + "_1", drawable1));
            objects.add(new CoverListItem(objectNames[i] + "_2", drawable2));
        }

        return objects;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setSelectedObject(String selectedObject) {
        this.selectedObject = selectedObject;

        screen.setSelectedCoverItems(selectedColor, selectedObject);
    }
}
