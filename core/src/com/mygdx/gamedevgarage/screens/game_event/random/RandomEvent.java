package com.mygdx.gamedevgarage.screens.game_event.random;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.MainScreen;
import com.mygdx.gamedevgarage.screens.collection.CollectionScreen;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.upgrade.UpgradeScreen;

import java.util.Random;

public class RandomEvent implements GameEvent {

    public static Timer eventTimer = new Timer();
    private static int percent = 0;
    public static long delaySeconds = 20;

    public static void scheduleNextEvent(final RandomEvent event){
        eventTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Game game = Game.getInstance();

                if((game.getScreen() instanceof MainScreen && !game.getMainScreen().isDialogOpened())
                        || game.getScreen() instanceof UpgradeScreen || game.getScreen() instanceof CollectionScreen) {
                    int r = new Random().nextInt(10);
                    percent += r;

                    if (r > 7 || percent >= 100) {
                        percent = 0;
                        delaySeconds = 20;
                        game.eventPublisher.triggerEvent(event);
                    }
                } else {
                    delaySeconds = 1;
                    scheduleNextEvent(event);
                }
            }
        }, delaySeconds);
    }

    private String text;

    public RandomEvent(String text) {
        this.text = text;
    }

    @Override
    public void start() {
        Game.getInstance().setRandomEventScreen(this);
        scheduleNextEvent(this);
    }

    public void confirm(){
        System.out.println("confirm");
    }

    public void reject(){
        System.out.println("reject");
    }

    public String getText() {
        return text;
    }
}
