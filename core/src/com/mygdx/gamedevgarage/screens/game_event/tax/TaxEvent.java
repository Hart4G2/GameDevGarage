package com.mygdx.gamedevgarage.screens.game_event.tax;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.MainScreen;
import com.mygdx.gamedevgarage.screens.collection.CollectionScreen;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.utils.stats.Stats;
import com.mygdx.gamedevgarage.utils.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

public class TaxEvent implements GameEvent {

    public static Timer eventTimer = new Timer();
    public static float delaySeconds = 30;

    public static void scheduleEvent(final TaxEvent event){
        eventTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Game.getInstance().eventPublisher.triggerEvent(event);
            }
        }, delaySeconds, delaySeconds, -1);
    }

    @Override
    public void start() {
        Stats stats = Stats.getInstance();
        Cost cost = new Cost(new Currency[]{Currency.MONEY} , new int[]{10});

        if(stats.isEnough(cost)){
            stats.pay(cost);
        } else {
            setGameOverScreen();
        }

        StatsTable.setHint(Currency.MONEY, "Pay taxes", "red_16");
    }

    private float gameOverDelay = 0;

    public void setGameOverScreen(){
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Game game = Game.getInstance();

                if((game.getScreen() instanceof MainScreen && game.getMainScreen().isDialogClosed())
                        || game.getScreen() instanceof UpgradeScreen || game.getScreen() instanceof CollectionScreen){
                    gameOverDelay = 0f;
                    game.setGameOverScreen();
                } else {
                    gameOverDelay = 1f;
                    setGameOverScreen();
                }
            }
        }, gameOverDelay);
    }
}