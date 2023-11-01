package com.mygdx.gamedevgarage.screens.game_event.story;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.collection.CollectionScreen;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.main.MainScreen;
import com.mygdx.gamedevgarage.screens.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.data.events.ComicEvent;
import com.mygdx.gamedevgarage.utils.data.events.ConditionObject;
import com.mygdx.gamedevgarage.utils.stats.Cost;

public class StoryEvent implements GameEvent {

    private void openStoryScreen(){
        final Timer storyTimer = new Timer();
        storyTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Game game = Game.getInstance();

                Screen openedScreen = game.getScreen();

                if ((openedScreen instanceof MainScreen && game.getMainScreen().isDialogClosed())
                        || openedScreen instanceof UpgradeScreen || openedScreen instanceof CollectionScreen) {
                    game.setStoryEventScreen();
                    storyTimer.clear();
                }
            }
        }, 1, 1);
    }

    private final ComicEvent comicEvent;

    public StoryEvent(ComicEvent comicEvent) {
        this.comicEvent = comicEvent;
    }

    @Override
    public void start() {
        openStoryScreen();
    }

    public ComicEvent getComicEvent() {
        return comicEvent;
    }

    public ConditionObject[] getCondition() {
        return comicEvent.getConditions();
    }

    public Cost getCost(){
        return comicEvent.getCost();
    }
}
