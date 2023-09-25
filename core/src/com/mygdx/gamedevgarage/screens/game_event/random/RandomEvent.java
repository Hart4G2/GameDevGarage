package com.mygdx.gamedevgarage.screens.game_event.random;

import static com.mygdx.gamedevgarage.utils.data.DataArrayFactory.randomEvents;
import static com.mygdx.gamedevgarage.utils.data.DataArrayFactory.shownRandomEvents;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.MainScreen;
import com.mygdx.gamedevgarage.screens.collection.CollectionScreen;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.events.Event;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;

import java.util.Random;

public class RandomEvent implements GameEvent  {

    public static Timer eventTimer = new Timer();
    private static int percent = 0;
    public static long delaySeconds = 20;

    public static void scheduleNextEvent(final RandomEvent event){
        eventTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Game game = Game.getInstance();

                if(game.isRandomEventShownFromLastOpen) {
                    game.isRandomEventShownFromLastOpen = false;
                    openEventScreen(event);
                    return;
                }

                openEventScreenWithChance(event);
            }
        }, delaySeconds);
    }

    private static void openEventScreen(RandomEvent event){
        Game game = Game.getInstance();

        if ((game.getScreen() instanceof MainScreen && game.getMainScreen().isDialogClosed())
                || game.getScreen() instanceof UpgradeScreen || game.getScreen() instanceof CollectionScreen) {
            delaySeconds = 20;
            game.eventPublisher.triggerEvent(event);
        } else {
            delaySeconds = 1;
            scheduleNextEvent(event);
        }
    }

    private static void openEventScreenWithChance(RandomEvent event){
        Game game = Game.getInstance();

        if ((game.getScreen() instanceof MainScreen && game.getMainScreen().isDialogClosed())
                || game.getScreen() instanceof UpgradeScreen || game.getScreen() instanceof CollectionScreen) {
            int r = new Random().nextInt(11);
            percent += r;
            System.out.println(percent);

            if (r > 7 || percent >= 30) {
                percent = 0;
                delaySeconds = 20;
                game.eventPublisher.triggerEvent(event);
            } else {
                delaySeconds = 20;
                scheduleNextEvent(event);
            }
        } else {
            delaySeconds = 1;
            scheduleNextEvent(event);
        }
    }

    private Event event;

    @Override
    public void start() {
        event = generateEvent();

        Game.getInstance().setRandomEventScreen(this);
        scheduleNextEvent(this);
    }

    public void confirm(){
        Stats.getInstance().pay(event.getConfirmCost());
        StatsTable.setHint(Currency.MONEY, "Confirmed", "default");
        StatsTable.setHint(Currency.ENERGY, "Confirmed", "default");
    }

    public void reject(){
        Stats.getInstance().pay(event.getRejectCost());
        StatsTable.setHint(Currency.ENERGY, "Rejected", "red_16");
    }

    public Event getEvent() {
        return event;
    }

    private Event generateEvent() {
        if(shownRandomEvents.size() == randomEvents.size()) {
            shownRandomEvents.clear();
        }

        int r = new Random().nextInt(randomEvents.size());
        Event event = randomEvents.get(r);

        while(shownRandomEvents.contains(event)) {
            r = new Random().nextInt(randomEvents.size());
            event = randomEvents.get(r);
        }

        shownRandomEvents.add(event);
        return event;
    }
}
