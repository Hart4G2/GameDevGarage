package com.mygdx.gamedevgarage.utils;

import com.mygdx.gamedevgarage.utils.constraints.Currency;

import java.util.Arrays;

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

    public int getCost(Currency name){
        int i = 0;

        for(Currency cost : costNames){
            if(cost.equals(name)){
                return costs[i];
            }
            i++;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "costNames=" + Arrays.toString(costNames) +
                ", costs=" + Arrays.toString(costs) +
                '}';
    }
}
