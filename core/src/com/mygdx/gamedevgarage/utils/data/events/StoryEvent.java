package com.mygdx.gamedevgarage.utils.data.events;

public class StoryEvent {

    private final Event[] events;
    private int condition;

    public StoryEvent(Event... events) {
        this.events = events;
    }

    public Event[] getEvents() {
        return events;
    }

    public int getCondition() {
        return condition;
    }
}
