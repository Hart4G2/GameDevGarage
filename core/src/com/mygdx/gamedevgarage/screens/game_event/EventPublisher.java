package com.mygdx.gamedevgarage.screens.game_event;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameEvent event) {
        for (Observer observer : observers) {
            observer.onEventReceived(event);
        }
    }

    public void triggerEvent(GameEvent event) {
        notifyObservers(event);
    }
}
