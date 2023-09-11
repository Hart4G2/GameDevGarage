package com.mygdx.gamedevgarage.utils.constraints;

public enum Currency {

    DESIGN("design"),
    PROGRAMMING("programing"),
    GAME_DESIGN("game_design"),
    MONEY("money"),
    LEVEL("lvl"),
    EXPERIENCE("exp");

    private final String drawableName;

    Currency(String drawableName) {
        this.drawableName = drawableName;
    }

    public String getDrawableName() {
        return drawableName;
    }

    public static Currency fromString(String name){
        switch (name){
            case "DESIGN": return DESIGN;
            case "PROGRAMMING": return PROGRAMMING;
            case "GAME_DESIGN": return GAME_DESIGN;
            case "MONEY": return MONEY;
            case "LEVEL": return LEVEL;
            case "EXPERIENCE": return EXPERIENCE;
            default: return null;
        }
    }
}
