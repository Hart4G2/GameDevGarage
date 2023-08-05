package com.mygdx.gamedevgarage.mini_games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.DialogThread;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckList;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckListItem;

public class ProgrammingMiniGameScreen implements Screen, MiniGameScreen {

    private Game game;
    private Assets assets;
    private Skin skin;
    private Stage stage;

    private Button okButton;
    private Array<CheckListItem> technologies;
    private CheckList techList;

    private Array<String> selectedTechnologies;

    public ProgrammingMiniGameScreen(Game game) {
        this.game = game;
        this.assets = game.getAssets();
        this.skin = assets.getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUIElements();
        setupUIListeners();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        techList.render(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Действия при приостановке экрана (например, когда игра переходит в фоновый режим)
    }

    @Override
    public void resume() {
        // Действия при возобновлении экрана (например, когда игра возвращается из фонового режима)
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void addItem(String item) {
        selectedTechnologies.add(item);
        okButton.setDisabled(false);
    }

    @Override
    public void removeItem(String item) {
        if(selectedTechnologies.contains(item, true)){
            selectedTechnologies.removeValue(item, true);
            if(selectedTechnologies.size == 0){
                okButton.setDisabled(true);
            }
        }
    }

    private void createUIElements(){

        selectedTechnologies = new Array<>();

        technologies = initTechnologies();
        techList = new CheckList(technologies, this, assets);

        ScrollPane scrollPane = new ScrollPane(techList, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollbarsVisible(true);

        okButton = new TextButton("OK", skin, "white_18");
        okButton.setDisabled(true);

        Group group = new Group();
        group.addActor(scrollPane);
        group.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 1.15f);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.add(group).padTop(30).padBottom(20).row();
        table.add(okButton).colspan(2).center().row();

        stage.addActor(table);

        table.setBackground("window_drawable_blue");
    }

    private void setupUIListeners(){
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!okButton.isDisabled()){
                    for(String item : selectedTechnologies){
                        System.out.printf("%s\t", item);
                    }
                    System.out.println();

                    game.setMainScreen();
                    DialogThread.getProgrammingThread().cancel();
                    new Timer().scheduleTask(DialogThread.getGameDesignThread(), DialogThread.getProgrammingTime());
                }
            }
        });
    }

    public Array<CheckListItem> initTechnologies(){
        Array<CheckListItem> objects = new Array<>();

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
                "hunting", "life", "medieval", "music", "ninja", "pirates",  "prison", "race",
                "romantic", "rhythm", "school", "space", "sport", "superheros", "time_travel",
                "transport", "vampires", "virtual_animal", "war", "westwood", "zombie"
        };

        TextureRegionDrawable bgUnselected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("programming_item.png")));

        TextureRegionDrawable bgSelected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("programming_item_selected.png")));

        TextureRegionDrawable imageFrameUnselected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg.png")));

        TextureRegionDrawable imageFrameSelected = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("item_image_bg_selected.png")));

        TextureRegionDrawable frame1Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame1.png")));
        TextureRegionDrawable  frame2Texture = new TextureRegionDrawable(new Texture(Gdx.files.internal("frame2.png")));

//        objects.add(new TechListItem("aliens", frame1Texture, frame2Texture, bgUnselected, bgSelected,
//                imageFrameUnselected, imageFrameSelected, skin));

//        for (int i = 0; i < objectNames.length; i++) {
//            TextureRegionDrawable image1 = new TextureRegionDrawable(
//                    assets.designObjects100Atlas.findRegion(objectRegions[i], 1));
//            TextureRegionDrawable image2 = new TextureRegionDrawable(
//                    assets.designObjects100Atlas.findRegion(objectRegions[i], 2));
//            objects.add(new TechListItem(objectNames[i] + "_1", image1, bgUnselected, bgSelected,
//                    imageFrameUnselected, imageFrameSelected, skin));
//            objects.add(new TechListItem(objectNames[i] + "_2", image2, bgUnselected, bgSelected,
//                    imageFrameUnselected, imageFrameSelected, skin));
//        }

        for (int i = 0; i < objectNames.length; i++) {
            objects.add(new CheckListItem(objectNames[i] + "_1", frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected, skin));
            objects.add(new CheckListItem(objectNames[i] + "_2", frame1Texture, frame2Texture, bgUnselected, bgSelected,
                    imageFrameUnselected, imageFrameSelected, skin));
        }

        return objects;
    }
}
