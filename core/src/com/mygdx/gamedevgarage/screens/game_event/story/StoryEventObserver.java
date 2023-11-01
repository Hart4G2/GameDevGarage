package com.mygdx.gamedevgarage.screens.game_event.story;

import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.game_event.Observer;
import com.mygdx.gamedevgarage.utils.DataManager;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;
import com.mygdx.gamedevgarage.utils.data.events.ComicEvent;
import com.mygdx.gamedevgarage.utils.data.events.Condition;
import com.mygdx.gamedevgarage.utils.data.events.ConditionObject;
import com.mygdx.gamedevgarage.utils.stats.Cost;
import com.mygdx.gamedevgarage.utils.stats.Stats;

import java.util.HashMap;
import java.util.List;

public class StoryEventObserver implements Observer {

    private static ConditionObject[] values;
    private static StoryEvent storyEvent;

    public StoryEventObserver() {
        DataManager dataManager = DataManager.getInstance();

        storyEvent = dataManager.getStoryEvent();
        values = createValues(dataManager.getStoryValues());

        if(getStoryEventId() == 0){
            onEventReceived(storyEvent);
        }
    }

    @Override
    public void onEventReceived(GameEvent event) {
        Game game = Game.getInstance();

        if(event instanceof StoryEvent && !game.isStoryEnded()){
            values = createValues();
            event.start();
        }

        if(event instanceof ConditionEvent){
            ConditionObject newValue = ((ConditionEvent) event).getConditionObject();

            ConditionObject.addValue(values, newValue);

            game.getMainScreen().getTaskActor().update();

            if(ConditionObject.isEnough(storyEvent.getCondition(), values)){
                game.eventPublisher.triggerEvent(storyEvent);
            }
        }
    }

    public ConditionObject[] createValues(){
        ConditionObject[] values = new ConditionObject[3];

        values[0] = new ConditionObject(Condition.EVENT, 0);
        values[1] = new ConditionObject(Condition.GAME, 0);
        values[2] = new ConditionObject(Condition.GAME_WITH_SCORE, 0);

        return values;
    }

    public ConditionObject[] createValues(HashMap<String, Integer> savedValues){
        if(savedValues == null || savedValues.isEmpty()) return createValues();

        ConditionObject[] values = new ConditionObject[3];

        values[0] = new ConditionObject(Condition.EVENT, savedValues.get("EVENT"));
        values[1] = new ConditionObject(Condition.GAME, savedValues.get("GAME"));
        values[2] = new ConditionObject(Condition.GAME_WITH_SCORE, savedValues.get("GAME_WITH_SCORE"));

        return values;
    }

    public static ConditionObject[] getValues() {
        return values;
    }

    public static StoryEvent getStoryEvent() {
        return storyEvent;
    }

    public static int getStoryEventId() {
        List<ComicEvent> comicEvents = DataArrayFactory.storyEvents;

        for(int i = 0; i < comicEvents.size(); i++){
            if(comicEvents.get(i).equals(storyEvent.getComicEvent()))
                return i;
        }

        return 0;
    }

    public static void setNextStoryEvent() {
        int id = getStoryEventId();

        Stats stats = Stats.getInstance();
        Cost cost = storyEvent.getCost();

        stats.pay(cost);
        Utils.setHint(cost, "white_16");

        Game game = Game.getInstance();
        game.getMainScreen().getTaskActor().update();

        if(id < DataArrayFactory.storyEvents.size() - 1) {
            storyEvent = new StoryEvent(DataArrayFactory.storyEvents.get(id + 1));
            game.getMainScreen().getTaskActor().update();
        } else {
            game.getMainScreen().getTaskActor().setVisible(false);
            game.setStoryEnded(true);
        }
    }
}
