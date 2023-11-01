package com.mygdx.gamedevgarage.utils.data.events;

public class ConditionObject {

    private final Condition condition;
    private int value;

    public ConditionObject(Condition condition, int value) {
        this.condition = condition;
        this.value = value;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public boolean isEnough(ConditionObject cost){
        if(cost.condition == null) return false;
        if(cost.condition != condition) return false;
        return cost.value >= value;
    }

    public static boolean isEnough(ConditionObject[] costs, ConditionObject[] values){
        for (ConditionObject cost : costs) {
            for (ConditionObject value : values) {
                if(cost.getCondition() == value.getCondition()){
                    if(!cost.isEnough(value)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void addValue(ConditionObject[] values, ConditionObject value){
        Condition condition = value.getCondition();

        for (ConditionObject object : values) {
            if(object.getCondition() == condition){
                if(condition == Condition.GAME_WITH_SCORE) {
                    object.setValue(value.getValue());
                } else {
                    object.setValue(object.getValue() + value.getValue());
                }
            }
        }
    }
}
