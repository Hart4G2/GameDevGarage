package com.mygdx.gamedevgarage.utils.data.events;

public enum Condition {

    GAME_WITH_SCORE,
    GAME,
    EVENT;

    public static Condition fromString(String name){
        switch (name){
            case "GAME_WITH_SCORE": return GAME_WITH_SCORE;
            case "GAME": return GAME;
            case "EVENT": return EVENT;
            default: return null;
        }
    }

    @Override
    public String toString() {
        switch (this){
            case GAME_WITH_SCORE: return "GAME_WITH_SCORE";
            case GAME: return "GAME";
            default: return "EVENT";
        }
    }
}
