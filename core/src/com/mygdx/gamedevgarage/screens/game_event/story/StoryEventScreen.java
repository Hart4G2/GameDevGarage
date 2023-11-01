package com.mygdx.gamedevgarage.screens.game_event.story;

import static com.mygdx.gamedevgarage.utils.Utils.createBgStack;
import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createScalingImage;
import static com.mygdx.gamedevgarage.utils.Utils.createTextButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.gamedevgarage.screens.StatsGameScreen;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.data.events.ComicEvent;

import java.util.ArrayList;
import java.util.List;

public class StoryEventScreen extends StatsGameScreen {

    private ComicEvent comicEvent;
    private List<TextButton> nextButtons;
    private List<TextButton> backButtons;
    private Table[] tables;

    @Override
    protected void showInitialise() {
        super.showInitialise();
        game.stopAllThreads();
        game.setStoryScreenOpened(true);

        comicEvent = StoryEventObserver.getStoryEvent().getComicEvent();
    }

    @Override
    protected void createUIElements() {
        initStatsTable();

        int pageSize = comicEvent.getTexts().length;

        tables = new Table[pageSize];
        nextButtons = new ArrayList<>();
        backButtons = new ArrayList<>();

        for(int i = 0; i < pageSize; i++){
            Drawable drawable = comicEvent.getDrawables()[i];
            String text = comicEvent.getTexts()[i];
            boolean isLast = i == pageSize - 1;

            Table table = createTable(drawable, text, i, isLast);
            table.setVisible(i == 0);

            System.out.println(i == 0);

            tables[i] = table;
        }

        Stack stack = createBgStack("window_darkblue", tables);

        stage.addActor(stack);
        stage.addActor(statsTable);
    }

    private Table createTable(Drawable drawable, String text, int i, boolean isLast){
        Image image = createScalingImage(drawable, Scaling.fit);
        Label label = createLabel(text, "white_18", true);

        String buttonText = isLast ? "Finish" : "Next";
        String buttonName = isLast ? "last" : i + "";

        TextButton nextButton = createTextButton(buttonText, "white_18", buttonName);
        nextButtons.add(nextButton);

        Table labelContainer = Utils.createTable(skin);
        labelContainer.setBackground("story_text_bg");
        labelContainer.add(label).width(getWidthPercent(.7f)).height(getHeightPercent(.19f));

        Table table = Utils.createTable(skin, true);
        table.add(image).width(getHeightPercent(.4f)).height(getHeightPercent(.4f))
                .padTop(getHeightPercent(.1f)).padBottom(getHeightPercent(.1f))
                .colspan(2).center().row();
        table.add(labelContainer).width(getWidthPercent(.85f)).height(getHeightPercent(.2f))
                .padBottom(getHeightPercent(.03f))
                .colspan(2).center().row();

        if(i != 0) {
            TextButton backButton = createTextButton("Back", "white_18", i + "");
            backButtons.add(backButton);

            table.add(backButton).width(getWidthPercent(.4f)).height(getHeightPercent(.08f))
                    .padBottom(getHeightPercent(.03f))
                    .left();
        }

        table.add(nextButton).width(getWidthPercent(.4f)).height(getHeightPercent(.08f))
                .padBottom(getHeightPercent(.03f))
                .right();

        return table;
    }

    @Override
    protected void setupUIListeners() {
        for(final TextButton button : nextButtons){
            if(!button.getName().equals("last")) {
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        int tableIndex = Integer.parseInt(button.getName()) + 1;

                        for (int i = 0; i < tables.length; i++) {
                            tables[i].setVisible(i == tableIndex);
                        }

                        playSound(comicEvent.getSounds()[tableIndex]);
                    }
                });
            } else {
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        StoryEventObserver.setNextStoryEvent();
                        game.setMainScreen();
                    }
                });
            }
        }

        for(final TextButton button : backButtons){
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int tableIndex = Integer.parseInt(button.getName()) - 1;

                    for (int i = 0; i < tables.length; i++) {
                        tables[i].setVisible(i == tableIndex);
                    }

                    playSound(comicEvent.getSounds()[tableIndex]);
                }
            });
        }

        playSound(comicEvent.getSounds()[0]);
    }

    @Override
    public void hide() {
        super.hide();
        game.resumeAllThreads();
        game.setStoryScreenOpened(false);
    }

    private void playSound(String sound){
        if(!sound.equals("")){
            assets.setInterruptingSound(sound);
        }
    }
}
