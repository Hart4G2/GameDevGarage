package com.mygdx.gamedevgarage.screens.main.actors;

import static com.mygdx.gamedevgarage.utils.Utils.createLabel;
import static com.mygdx.gamedevgarage.utils.Utils.createTable;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.gamedevgarage.screens.TableActor;
import com.mygdx.gamedevgarage.screens.game_event.story.StoryEventObserver;
import com.mygdx.gamedevgarage.utils.data.events.Condition;
import com.mygdx.gamedevgarage.utils.data.events.ConditionObject;

import java.util.HashSet;
import java.util.Set;

public class TaskActor extends TableActor {

    private ConditionObject[] values;
    private ConditionObject[] tasks;

    private Label gameLabel;
    private Label gameWithScoreLabel;
    private Label eventLabel;

    private Table headerTable;
    private Table gameTable;
    private Table gameWithScoreTable;
    private Table eventTable;

    public TaskActor() {
        createUIElements();
        update();
    }

    @Override
    protected void createUIElements() {
        Label headerLabel = createLabel(i18NBundle.get("tasks"), "black_italic_16", true, Align.center);
        gameLabel = createLabel(i18NBundle.get("game_task"), "black_italic_16", true, Align.center);
        gameWithScoreLabel = createLabel(i18NBundle.get("game_with_score_task"), "black_italic_16", true, Align.center);
        eventLabel = createLabel(i18NBundle.get("event_task"), "black_italic_16", true, Align.center);

        headerTable = createTable(skin);
        headerTable.setBackground("task_bg");
        headerTable.add(headerLabel).width(getWidthPercent(.2f)).height(getHeightPercent(.05f));

        gameTable = createTable(skin);
        gameTable.setBackground("task_bg");
        gameTable.add(gameLabel).width(getWidthPercent(.3f)).height(getHeightPercent(.07f));

        gameWithScoreTable = createTable(skin);
        gameWithScoreTable.setBackground("task_bg");
        gameWithScoreTable.add(gameWithScoreLabel).width(getWidthPercent(.3f)).height(getHeightPercent(.07f));

        eventTable = createTable(skin);
        eventTable.setBackground("task_bg");
        eventTable.add(eventLabel).width(getWidthPercent(.3f)).height(getHeightPercent(.07f));
    }

    public void update(){
        if(!isVisible()) return;

        values = StoryEventObserver.getValues();
        tasks = StoryEventObserver.getStoryEvent().getCondition();

        Set<Condition> conditionSet = new HashSet<>();

        for (ConditionObject task : tasks) {
            Condition condition = task.getCondition();
            conditionSet.add(condition);

            String text = " " + task.getValue() + " / " + getCurrentValue(condition);

            if(condition == Condition.EVENT){
                eventLabel.setText(i18NBundle.get("event_task") + text);
            }
            if(condition == Condition.GAME_WITH_SCORE){
                gameWithScoreLabel.setText(i18NBundle.get("game_with_score_task") + text);
            }
            if(condition == Condition.GAME){
                gameLabel.setText(i18NBundle.get("game_task") + text);
            }
        }

        updateUIElements(conditionSet);
    }

    private void updateUIElements(Set<Condition> conditionSet){
        clearChildren();

        addTable(headerTable);

        if(conditionSet.contains(Condition.GAME)){
            addTable(gameTable);
        }
        if(conditionSet.contains(Condition.GAME_WITH_SCORE)){
            addTable(gameWithScoreTable);
        }
        if(conditionSet.contains(Condition.EVENT)){
            addTable(eventTable);
        }
    }

    private void addTable(Table table){
        add(table).width(getWidthPercent(.32f)).height(getHeightPercent(.07f))
                .padBottom(getHeightPercent(.01f))
                .row();
    }

    private int getCurrentValue(Condition condition){
        for (ConditionObject value : values) {
            if(value.getCondition() == condition){
                return value.getValue();
            }
        }
        return 0;
    }
}
