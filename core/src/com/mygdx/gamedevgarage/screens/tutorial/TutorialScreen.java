package com.mygdx.gamedevgarage.screens.tutorial;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createScalingImage;
import static com.mygdx.gamedevgarage.utils.Utils.createTable;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;
import static com.mygdx.gamedevgarage.utils.Utils.randomInt;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.gamedevgarage.screens.GameScreen;

public class TutorialScreen extends GameScreen {

    private TextButton backButton;

    @Override
    protected void createUIElements(){
        Label header = createLabel(bundle.get("How_to_play"), "white_32", false, Align.center);

        Label propertiesHeader = createLabel(bundle.get("Properties_header"), "white_24", false, Align.center);
        Label moneyText = createLabel(bundle.get("money_text"), "white_18", true);
        Label lvlText = createLabel(bundle.get("lvl_text"), "white_18", true);
        Label designText = createLabel(bundle.get("design_text"), "white_18", true);
        Label programingText = createLabel(bundle.get("programing_text"), "white_18", true);
        Label gameDesignText = createLabel(bundle.get("game_design_text"), "white_18", true);
        Label energyText = createLabel(bundle.get("energy_text"), "white_18", true);

        Label createGameHeader = createLabel(bundle.get("Create_game_header"), "white_24", false, Align.center);
        Label createGameText = createLabel(bundle.get("Create_game_text"), "white_18", true);
        Label timeText = createLabel(bundle.get("Time_text"), "white_18", true);
        Label genreThemeText = createLabel(bundle.get("Genre_theme_text"), "white_18", true);
        Label coverText = createLabel(bundle.get("Cover_text"), "white_18", true);
        Label technologiesText = createLabel(bundle.get("Technologies_text"), "white_18", true);
        Label mechanicsText = createLabel(bundle.get("Mechanics_text"), "white_18", true);
        Label platformText = createLabel(bundle.get("Platform_text"), "white_18", true);
        Label endGameText = createLabel(bundle.get("End_game_text"), "white_18", true);
        Label sellingText = createLabel(bundle.get("Selling_text"), "white_18", true);
        Label upgradeText = createLabel(bundle.get("Upgrade_text"), "white_18", true);
        Label almostForgotText = createLabel(bundle.get("Almost_forgot"), "white_18", true);

        Image moneyImage = createScalingImage(skin.getDrawable("money"), Scaling.fit);
        Image lvlImage = createScalingImage(skin.getDrawable("lvl"), Scaling.fit);
        Image designImage = createScalingImage(skin.getDrawable("design"), Scaling.fit);
        Image programingImage = createScalingImage(skin.getDrawable("programing"), Scaling.fit);
        Image gameDesignImage = createScalingImage(skin.getDrawable("game_design"), Scaling.fit);
        Image energyImage = createScalingImage(skin.getDrawable("energy"), Scaling.fit);

        TextureAtlas tutorialAtlas = assets.tutorialAtlas;

        Image createGameImage = createScalingImage(tutorialAtlas.findRegion("create_game_tutorial"), Scaling.fit);
        Image timeImage = createScalingImage(tutorialAtlas.findRegion("time_tutorial"), Scaling.fit);
        Image genreThemeImage = createScalingImage(tutorialAtlas.findRegion("genre_theme_tutorial"), Scaling.fit);
        Image coverImage = createScalingImage(tutorialAtlas.findRegion("cover_tutorial"), Scaling.fit);
        Image technologyImage = createScalingImage(tutorialAtlas.findRegion("technologies_tutorial"), Scaling.fit);
        Image mechanicImage = createScalingImage(tutorialAtlas.findRegion("mechanics_tutorial"), Scaling.fit);
        Image platformImage = createScalingImage(tutorialAtlas.findRegion("platform_tutorial"), Scaling.fit);
        Image endGameImage = createScalingImage(tutorialAtlas.findRegion("score_tutorial"), Scaling.fit);
        Image sellingImage = createScalingImage(tutorialAtlas.findRegion("selling_tutorial"), Scaling.fit);
        Image upgradeImage = createScalingImage(tutorialAtlas.findRegion("upgrade_tutorial"), Scaling.fit);
        Image almostForgotImage = createScalingImage(tutorialAtlas.findRegion("almost_forgot_tutorial"), Scaling.fit);

        backButton = createTextButton(bundle.get("Back"), "white_18");

        Table table = createTable(skin);
        table.add(header).width(getWidthPercent(.95f)).height(getHeightPercent(.1f))
                .padBottom(getHeightPercent(.02f))
                .colspan(2).center()
                .row();
        table.add(propertiesHeader).width(getWidthPercent(.95f)).height(getHeightPercent(.1f))
                .padBottom(getHeightPercent(.02f))
                .colspan(2).center()
                .row();
        table.add(moneyText).width(getWidthPercent(.3f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(moneyImage).width(getWidthPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(lvlImage).width(getWidthPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(lvlText).width(getWidthPercent(.3f)).height(getHeightPercent(.15f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(designText).width(getWidthPercent(.3f)).height(getHeightPercent(.15f))
                .padBottom(getHeightPercent(.01f))
                .colspan(1);
        table.add(designImage).width(getWidthPercent(.2f))
                .padBottom(getHeightPercent(.01f))
                .colspan(1)
                .row();
        table.add(programingImage).width(getWidthPercent(.2f))
                .padBottom(getHeightPercent(.01f))
                .colspan(1);
        table.add(programingText).width(getWidthPercent(.3f)).height(getHeightPercent(.15f))
                .padBottom(getHeightPercent(.01f))
                .colspan(1)
                .row();
        table.add(gameDesignText).width(getWidthPercent(.3f)).height(getHeightPercent(.15f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(gameDesignImage).width(getWidthPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(energyImage).width(getWidthPercent(.2f))
                .padBottom(getHeightPercent(.2f))
                .colspan(1);
        table.add(energyText).width(getWidthPercent(.3f)).height(getHeightPercent(.15f))
                .padBottom(getHeightPercent(.2f))
                .colspan(1)
                .row();

        table.add(createGameHeader).width(getWidthPercent(.95f)).height(getHeightPercent(.01f))
                .padBottom(getHeightPercent(.02f))
                .colspan(2).center()
                .row();
        table.add(createGameImage).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(createGameText).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(timeImage).width(getWidthPercent(.4f)).height(getHeightPercent(.4f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(timeText).width(getWidthPercent(.4f)).height(getHeightPercent(.4f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(genreThemeText).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(genreThemeImage).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1).right()
                .row();
        table.add(coverImage).width(getWidthPercent(.4f)).height(getHeightPercent(.4f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(coverText).width(getWidthPercent(.4f)).height(getHeightPercent(.4f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(technologiesText).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(technologyImage).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(mechanicImage).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(mechanicsText).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(platformText).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(platformImage).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(endGameImage).width(getWidthPercent(.4f)).height(getHeightPercent(.3f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(endGameText).width(getWidthPercent(.4f)).height(getHeightPercent(.3f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(sellingText).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(sellingImage).width(getWidthPercent(.4f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(upgradeImage).width(getWidthPercent(.4f)).height(getHeightPercent(.4f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(upgradeText).width(getWidthPercent(.4f)).height(getHeightPercent(.4f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();
        table.add(almostForgotImage).width(getWidthPercent(.4f)).height(getHeightPercent(.3f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1);
        table.add(almostForgotText).width(getWidthPercent(.4f)).height(getHeightPercent(.3f))
                .padBottom(getHeightPercent(.02f))
                .colspan(1)
                .row();

        ScrollPane scrollPane = new ScrollPane(table, skin);

        Table mainTable = createTable(skin, true);
        mainTable.add(scrollPane)
                .width(getWidthPercent(1f)).height(getHeightPercent(.8f))
                .padTop(getHeightPercent(.01f)).padBottom(getHeightPercent(.02f))
                .row();
        mainTable.add(backButton).width(getWidthPercent(.35f)).height(getHeightPercent(.07f));

        stage.addActor(mainTable);

        createLabelAnimation(header);
        createLabelAnimation(propertiesHeader);
        createLabelAnimation(moneyText);
        createLabelAnimation(lvlText);
        createLabelAnimation(createGameHeader);
        createLabelAnimation(timeText);
        createLabelAnimation(genreThemeText);
        createLabelAnimation(coverText);
        createLabelAnimation(technologiesText);
        createLabelAnimation(mechanicsText);
        createLabelAnimation(platformText);
        createLabelAnimation(endGameText);
        createLabelAnimation(sellingText);
    }

    @Override
    protected void setupUIListeners() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMenuScreen();
            }
        });
    }

    private void createLabelAnimation(final Label label){
        final String finishText = String.valueOf(label.getText());
        final StringBuilder animText = new StringBuilder();

        final float[] delay = {.02f};

        label.setText(animText);

        label.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        int r = randomInt(2);

                                        delay[0] = r == 0 ? .01f : 02f;

                                        if(animText.length() < finishText.length()){
                                            animText.append(finishText.charAt(animText.length()));
                                            label.setText(animText);
                                        } else {
                                            label.clearActions();
                                        }
                                    }
                                }),
                                Actions.delay(delay[0])
                        )
                )
        );
    }
}
