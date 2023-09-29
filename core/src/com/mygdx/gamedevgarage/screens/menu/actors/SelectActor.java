package com.mygdx.gamedevgarage.screens.menu.actors;

import static com.mygdx.gamedevgarage.utils.Utils.createButton;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.gamedevgarage.Assets;

public class SelectActor extends Table {

    private final Assets assets;

    private Button leftButton;
    private Button rightButton;
    private Label languageLabel;
    private Cell<Label> labelCell;

    private final String[] languages;

    public SelectActor() {
        super();
        assets = Assets.getInstance();

        languages = new String[]{
                "English",
                "Русский"
        };

        createUIEElements();
        setupUIListeners();
    }

    private void createUIEElements() {
        leftButton = createButton("triangle_left");
        rightButton = createButton("triangle_right");

        String language = assets.getLanguage();

        for(int i = 0; i < languages.length; i++){
            if(languages[i].equals(language)){
                this.i = i;
            }
        }

        languageLabel = createLabel(language, "white_18", false);
        languageLabel.setAlignment(Align.center);

        float buttonSize = getWidthPercent(.08f);

        add(leftButton).width(buttonSize).height(buttonSize);
        add(languageLabel).width(getWidthPercent(.3f)).height(getHeightPercent(.1f));
        add(rightButton).width(buttonSize).height(buttonSize);
    }

    private void setupUIListeners() {
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectPreviousLanguage();
            }
        });
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectNextLanguage();
            }
        });
    }

    private void selectPreviousLanguage() {
        String newLanguage = getPreviousLanguage();
        animateLanguageChange(newLanguage, -1);
    }

    private void selectNextLanguage() {
        String newLanguage = getNextLanguage();
        animateLanguageChange(newLanguage, 1);
    }

    private void animateLanguageChange(final String newLanguage, int side) {
        float duration = 0.1f;

        languageLabel.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.fadeOut(duration),
                        Actions.moveBy(side * getWidthPercent(.1f), 0, duration)
                ),
                Actions.delay(duration),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        languageLabel.setText(newLanguage);
                    }
                }),
                Actions.moveBy(-side * getWidthPercent(.1f), 0, 0f),
                Actions.parallel(
                        Actions.fadeIn(duration),
                        Actions.moveBy(side * getWidthPercent(.1f), 0, duration)
                )
        ));

        assets.setLanguage(newLanguage);
    }

    private int i = 0;

    private String getPreviousLanguage(){
        if(i == 0){
            i = languages.length - 1;
        } else {
            i--;
        }
        return languages[i];
    }

    private String getNextLanguage(){
        if(i == languages.length - 1){
            i = 0;
        } else {
            i++;
        }
        return languages[i];
    }
}
