package com.mygdx.gamedevgarage.screens.collection.actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.GameObject;

public class GameItem extends Table {

    private final GameObject gameObject;

    private Label nameLabel;
    private Label scoreLabel;
    private Label priceLabel;
    private Image objectImage;
    private Image priceImage;
    private Image scoreImage;

    public GameItem(GameObject gameObject) {
        super(Assets.getInstance().getSkin());
        this.gameObject = gameObject;

        setName(String.valueOf(gameObject.getId()));

        createUIElements();
    }

    private void createUIElements(){
        Skin skin = getSkin();
        String name = validName(gameObject.getName());

        String score = String.valueOf(gameObject.getScore());
        String price = String.valueOf(gameObject.getProfitMoney());
        String colorName = gameObject.getColor();
        String objectName = gameObject.getObject();

        Drawable object = DataArrayFactory.getObject(objectName);
        Drawable priceIcon = skin.getDrawable("money");
        Drawable scoreIcon = skin.getDrawable("score");

        String headerStyle = "white_24";
        String labelStyle = "white_20";

        if(!Utils.isColorDark(colorName)){
            headerStyle = "black_24";
            labelStyle = "black_20";
        }

        nameLabel = createLabel(name, headerStyle, true);
        scoreLabel = createLabel(score, labelStyle, false);
        priceLabel = createLabel(price, labelStyle, false);
        priceImage = new Image(priceIcon);
        scoreImage = new Image(scoreIcon);
        objectImage = new Image(object);

        float objectSize = getHeightPercent(.15f);
        float iconSize = getHeightPercent(.04f);

        Table table = new Table();
        table.add(nameLabel).width(getWidthPercent(.5f))
                .padBottom(getHeightPercent(.05f))
                .colspan(4).left().top().row();
        table.add(priceImage).width(iconSize).height(iconSize)
                .padRight(getWidthPercent(.02f));
        table.add(priceLabel).width(getWidthPercent(.1f))
                .padRight(getWidthPercent(.05f));
        table.add(scoreImage).width(iconSize).height(iconSize)
                .padRight(getWidthPercent(.02f));
        table.add(scoreLabel);

        add(table).padRight(getWidthPercent(.1f));
        add(objectImage).width(objectSize).height(objectSize);

        setBackground(createBackground());

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

    private String validName(String name){
        if(name.isEmpty()){
            name = "game" + gameObject.getId();
        }

        if(name.length() > 15){
            name = name.substring(0, 15).trim() + "...";
        }
        return name;
    }

    private NinePatchDrawable createBackground(){
        int backgroundWidth = (int) getWidthPercent(.8f);
        int backgroundHeight = (int) getHeightPercent(.2f);
        int cornerRadius = (int) getWidthPercent(.05f);

        Pixmap pixmap = new Pixmap(backgroundWidth, backgroundHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(Utils.convertColor(gameObject.getColor()));
        pixmap.fillRectangle(0, cornerRadius, backgroundWidth, backgroundHeight - 2 * cornerRadius);
        pixmap.fillRectangle(cornerRadius, 0, backgroundWidth - 2 * cornerRadius, backgroundHeight);
        pixmap.fillCircle(cornerRadius, cornerRadius, cornerRadius);
        pixmap.fillCircle(backgroundWidth - cornerRadius, cornerRadius, cornerRadius);
        pixmap.fillCircle(cornerRadius, backgroundHeight - cornerRadius, cornerRadius);
        pixmap.fillCircle(backgroundWidth - cornerRadius, backgroundHeight - cornerRadius, cornerRadius);

        NinePatch ninePatch = new NinePatch(new Texture(pixmap), cornerRadius, cornerRadius, cornerRadius, cornerRadius);

        return new NinePatchDrawable(ninePatch);
    }
}
