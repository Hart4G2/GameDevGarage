package com.mygdx.gamedevgarage.stats;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.gamedevgarage.Assets;

public class Property extends Table {

    private final Assets assets;
    private final Skin skin;
    private String text;
    private int value;
    private Drawable icon;
    private Image image;
    private Label valueLabel;
    private Label hintLabel;

    public Property(String text, int value, Assets assets) {
        super(assets.getSkin());
        this.assets = assets;
        this.skin = assets.getSkin();
        this.value = value;
        this.text = text;

        String iconName = text.replace(" ", "_");
        icon = new TextureRegionDrawable(new Texture("stats/" + iconName + ".png"));

        createUIElements();
        setupUIListeners();
    }

    private void createUIElements(){
        image = new Image(icon);
        valueLabel = createLabel(String.valueOf(value), skin, "white_18");
        valueLabel.setSize(getWidthPercent(.016f), getHeightPercent(.01f));

        hintLabel = createLabel(text, skin, "white_16");
        hintLabel.setVisible(false);

        add(image).width(getWidthPercent(.06f)).height(getWidthPercent(.06f))
                .pad(getHeightPercent(.025f), 0, getHeightPercent(.01f), getWidthPercent(.01f));
        add(valueLabel).width(getWidthPercent(.078f))
                .pad(getHeightPercent(.025f), 0, getHeightPercent(.01f), 0)
                .row();
        add(hintLabel).width(getWidthPercent(.158f))
                .colspan(2);
    }

    private boolean isPressed;

    private void setupUIListeners(){
        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(isPressed) return;

                isPressed = true;
                hintLabel.addAction(Actions.sequence(
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                hintLabel.addAction(Actions.alpha(0f, .001f));
                                hintLabel.setVisible(true);
                            }
                        }),
                        Actions.delay(.01f),
                        Actions.fadeIn(.2f),
                        Actions.delay(1.5f),
                        Actions.fadeOut(.3f),
                        Actions.delay(.1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                hintLabel.setVisible(false);
                                isPressed = false;
                            }
                        })
                ));
            }
        };

        image.addListener(listener);
        valueLabel.addListener(listener);
    }

    public void setValue(int value) {
        this.value = value;
        valueLabel.setText(String.valueOf(value));
    }

    public void setLabelStyle(String style){
        valueLabel.setStyle(skin.get(style, Label.LabelStyle.class));
    }
}
