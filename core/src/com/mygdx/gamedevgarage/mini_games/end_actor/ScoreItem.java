package com.mygdx.gamedevgarage.mini_games.end_actor;


import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Random;

public class ScoreItem extends Table {

    private String labelStyle;

    private Drawable icon;
    private int score;
    private Image iconImage;
    private Label scoreLabel;

    public ScoreItem(Skin skin, Drawable drawable, int score, String labelStyle) {
        super(skin);
        icon = drawable;
        this.score = score;
        this.labelStyle = labelStyle;

        createUIElements();
    }

    private void createUIElements(){
        iconImage = new Image(icon);
        iconImage.setVisible(false);
        scoreLabel = createLabel(String.valueOf(this.score), getSkin(), labelStyle);

        float imageSize = getWidthPercent(.15f);
        float pad = getWidthPercent(.05f);
        float labelHeight = getHeightPercent(.15f);

        Table imageTable = new Table();
        imageTable.add(iconImage).width(imageSize).height(imageSize);

        add(imageTable)
                .padRight(pad);
        add(scoreLabel).height(labelHeight);
    }

    public void startAnimation() {
        final Label label = scoreLabel;
        final Image image = iconImage;
        final int targetScore = score;
        float animationDuration = 40f;
        final int[] value = {targetScore};

        label.addAction(Actions.sequence(
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        label.setText(new Random().nextInt(10));
                    }
                }),
                Actions.repeat((int) (animationDuration),
                        Actions.sequence(
                                Actions.delay(0.05f),
                                Actions.run(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                if(value[0] == 0){
                                                    value[0]++;
                                                } else if (value[0] == 10) {
                                                    value[0]--;
                                                } else {
                                                    int r = new Random().nextInt(10);
                                                    if(r > 5){
                                                        value[0]++;
                                                    } else {
                                                        value[0]--;
                                                    }
                                                }
                                                label.setText(value[0]);
                                            }
                                        }
                                )
                        )
                ),
                Actions.run(
                        new Runnable() {
                            @Override
                            public void run() {
                                label.setText(targetScore);
                            }
                        }
                )
        ));

        image.setScale(.9f);

        iconImage.addAction(Actions.sequence(
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        image.setVisible(true);
                    }
                }),
                Actions.alpha(0f, 0f),
                Actions.moveBy(100, 0),
                Actions.delay(.5f),
                Actions.parallel(
                        Actions.fadeIn(1.2f),
                        Actions.moveBy(-100, 0, 1.5f),
                        Actions.scaleTo(1f, 1f, 1.5f, Interpolation.slowFast)
                )
        ));
    }

    public void setLabelStyle(String labelStyle){
        this.labelStyle = labelStyle;
        scoreLabel.setStyle(getSkin().get(labelStyle, Label.LabelStyle.class));
    }
}
