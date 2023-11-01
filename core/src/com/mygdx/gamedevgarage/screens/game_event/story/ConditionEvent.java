package com.mygdx.gamedevgarage.screens.game_event.story;

import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.utils.data.events.Condition;
import com.mygdx.gamedevgarage.utils.data.events.ConditionObject;

public class ConditionEvent implements GameEvent {

    private final ConditionObject conditionObject;

    public ConditionEvent(Condition condition, int conditionValue) {
        this.conditionObject = new ConditionObject(condition, conditionValue);
    }

    @Override
    public void start() {
        Game.getInstance().eventPublisher.triggerEvent(this);
    }

    public ConditionObject getConditionObject() {
        return conditionObject;
    }
}
