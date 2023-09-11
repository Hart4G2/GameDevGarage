package com.mygdx.gamedevgarage.screens.game_event;

import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.stats.StatsTable;
import com.mygdx.gamedevgarage.utils.Cost;
import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.List;

public class TaxEvent implements GameEvent {

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

        for (StatsTable table : tables) {
            table.getProperty("money").setHint("Pay taxes", "red_16");
        }
    }

    public void setGameOverScreen(){
        Game.getInstance().setGameOverScreen();
    }
}
