package com.mygdx.gamedevgarage.utils.stats;

import com.mygdx.gamedevgarage.utils.constraints.Currency;

public class Cost {

    Currency[] costNames;
    int[] costs;

    public Cost(Currency[] costNames, int[] costs) {
        this.costNames = costNames;
        this.costs = costs;
    }

    public Currency[] getCostNames(){
        return costNames;
    }

    public int[] getCosts() {
        return costs;
    }
}
