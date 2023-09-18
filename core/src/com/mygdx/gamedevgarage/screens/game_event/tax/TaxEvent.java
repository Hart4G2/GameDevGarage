package com.mygdx.gamedevgarage.screens.game_event.tax;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.screens.MainScreen;
import com.mygdx.gamedevgarage.screens.collection.CollectionScreen;
import com.mygdx.gamedevgarage.screens.game_event.GameEvent;
import com.mygdx.gamedevgarage.screens.upgrade.UpgradeScreen;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.List;

public class TaxEvent implements GameEvent {

    public static Timer eventTimer = new Timer();
    public static float delaySeconds = 20;

    public static void scheduleNextEvent(final TaxEvent event){
        eventTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Game.getInstance().eventPublisher.triggerEvent(event);
            }
        }, delaySeconds);
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

        List<StatsTable> tables = StatsTable.tables;

        if(tables != null && !tables.isEmpty()) {
            for (StatsTable table : tables) {
                table.getProperty("money").setHint("Pay taxes", "red_16");
            }
        }

        scheduleNextEvent(this);
    }

    public void setGameOverScreen(){
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Game game = Game.getInstance();

                if((game.getScreen() instanceof MainScreen && !game.getMainScreen().isDialogOpened())
                        || game.getScreen() instanceof UpgradeScreen || game.getScreen() instanceof CollectionScreen){
                    game.setGameOverScreen();
                } else {
                    setGameOverScreen();
                }
            }
        }, 1f);
    }
}
