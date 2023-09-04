package com.mygdx.gamedevgarage.collection.actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;

public class GameItem extends Table {

    private final Game game;
    private final Assets assets;
    private final GameObject gameObject;

    private Label nameLabel;
    private Label scoreLabel;
    private Label priceLabel;
    private Image objectImage;
    private Image priceImage;
    private Image scoreImage;

    public GameItem(Game game, GameObject gameObject) {
        super(game.getAssets().getSkin());
        this.game = game;
        this.assets = game.getAssets();
        this.gameObject = gameObject;

        setName(String.valueOf(gameObject.getId()));

        createUIElements();
    }

    private void createUIElements(){
        Skin skin = getSkin();

        String name = gameObject.getName();
        if(name.isEmpty()){
            name = "Game" + gameObject.getId();
        }

        String score = String.valueOf(gameObject.getScore());
        String price = String.valueOf(gameObject.getProfitMoney());
        String colorName = gameObject.getColor();
        String objectName = gameObject.getObject();

        Drawable colorDrawable = DataArrayFactory.getColor(assets, colorName);
        Drawable object = DataArrayFactory.getObject(assets, objectName);
        Drawable priceIcon = skin.getDrawable("money");
        Drawable scoreIcon = skin.getDrawable("score");

        nameLabel = createLabel(name, skin, "white_24");
        scoreLabel = createLabel(score, skin, "white_20");
        priceLabel = createLabel(price, skin, "white_20");
        priceImage = new Image(priceIcon);
        scoreImage = new Image(scoreIcon);

        objectImage = new Image(object);

        float objectSize = getHeightPercent(.15f);
        float iconSize = getHeightPercent(.04f);

        Table table = new Table();
        table.add(nameLabel)
                .padBottom(getHeightPercent(.05f))
                .colspan(4).left().top().row();
        table.add(priceImage).width(iconSize).height(iconSize)
                .padRight(getWidthPercent(.02f));
        table.add(priceLabel).width(getWidthPercent(.1f))
                .padRight(getWidthPercent(.05f));
        table.add(scoreImage).width(iconSize).height(iconSize)
                .padRight(getWidthPercent(.02f));
        table.add(scoreLabel);

        add(table).padRight(getWidthPercent(.2f));
        add(objectImage).width(objectSize).height(objectSize);

        setBackground(colorDrawable);

        if(!gameObject.isSold()){
            startAnimation();
        }
    }

    public void startAnimation(){
        priceLabel.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        priceLabel.setText("selling");
                                        if(gameObject.isSold()){
                                            priceLabel.setText(gameObject.getProfitMoney());
                                            priceLabel.clearActions();
                                        }
                                    }
                                }),
                                dotSequenceAction(),
                                dotSequenceAction(),
                                dotSequenceAction()
                        )
                )
        );
    }

    private SequenceAction dotSequenceAction(){
        return Actions.sequence(
                Actions.delay(.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        priceLabel.setText(priceLabel.getText() + ".");
                    }
                })
        );
    }
}
