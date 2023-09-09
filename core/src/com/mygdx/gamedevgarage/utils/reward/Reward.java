package com.mygdx.gamedevgarage.utils.reward;

import static com.mygdx.gamedevgarage.utils.data.GameFactory.genre;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.object;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.theme;

import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.utils.Utils;

import java.util.Random;

public class Reward {



    private final Stats stats;
    private static Reward instance;

    public Reward() {
        stats = Stats.getInstance();
    }

    public static Reward getInstance(){
        if(instance == null){
            instance = new Reward();
        }
        return instance;
    }

    private int design;
    private int programming;
    private int gameDesign;
    private int score;
    private int exp;
    private int requiredExp;
    private int lvl;
    private int profitMoney;
    private int sellTime;

    public void calculateScores(){
        design = calculateDesignScore();
        programming = calculateTechScore();
        gameDesign = calculateMechanicScore();
        score = calculateScore();
        exp = calculateExperienceScore();
        calculateLevel();
        calculateSellParams();
    }

    private void calculateSellParams() {
        profitMoney = (score + new Random().nextInt(2)) * 15;
        sellTime = score * 5;
    }

    private int calculateTechScore() {
        // TODO

        return 3;
    }

    private int calculateMechanicScore() {
        // TODO

        return 3;
    }

    private int calculateDesignScore() {
        int random = new Random().nextInt(10);

        if(isFirstDesign()){
            return random > 6 ? 3 : 2;
        }
        if (isSecondaryDesign()){
            return random > 6 ? 2 : 1;
        }
        return 0;
    }

    private boolean isFirstDesign(){
        String design1 = theme + "_1";
        String design2 = theme + "_2";

        return object.equals(design1) || object.equals(design2);
    }

    private boolean isSecondaryDesign(){
        String[] designs;

        switch (theme) {
            case "Aliens":{
                designs = new String[]{
                        "cyberpunk_1", "cyberpunk_2", "ninja_2",
                        "fantasy_2", "space_1", "space_2"
                };
                break;
            }
            case "Aviation":{
                designs = new String[]{
                        "criminal_1", "aliens_1", "space_1",
                        "transport_2"
                };
                break;
            }
            case "Business":{
                designs = new String[]{
                        "city_1", "city_2", "farm_1",
                        "farm_2", "fashion_1", "fashion_2",
                        "virtual_animal_1", "westwood_2", "transport_1",
                        "transport_2", "construction_1", "construction_2"
                };
                break;
            }
            case "Cinema":{
                designs = new String[]{
                        "fantasy_1", "fantasy_2", "aliens_1",
                        "aliens_2", "space_2", "life_2",
                        "medieval_1", "war_2", "zombie_2",
                        "cyberpunk_2", "criminal_2", "comedy_1",
                        "aviation_1"
                };
                break;
            }
            case "City":{
                designs = new String[]{
                        "business_1", "business_2", "government_1",
                        "government_2", "prison_2", "construction_1",
                        "construction_2"
                };
                break;
            }
            case "Comedy":{
                designs = new String[]{
                        "aviation_2", "detective_2", "government_1",
                        "music_1", "school_2"
                };
                break;
            }
            case "Construction":{
                designs = new String[]{
                        "business_1", "business_2", "government_1",
                        "government_2", "medieval_2", "city_1",
                        "city_2"
                };
                break;
            }
            case "Cooking":{
                designs = new String[]{
                        "medieval_2", "romantic_1", "business_2"
                };
                break;
            }
            case "Criminal":{
                designs = new String[]{
                        "aviation_2", "business_1", "detective_1",
                        "detective_2", "hacker_1", "hacker_2",
                        "horror_2", "hunting_1", "hunting_2",
                        "prison_1", "prison_2", "transport_1"
                };
                break;
            }
            case "Cyberpunk":{
                designs = new String[]{
                        "aliens_1", "aliens_2", "hacker_1",
                        "hacker_2", "war_2"
                };
                break;
            }
            case "Dance":{
                designs = new String[]{
                        "fashion_2", "rhythm_1", "rhythm_2",
                        "zombie_2"
                };
                break;
            }
            case "Detective":{
                designs = new String[]{
                        "prison_1", "prison_2", "westwood_1",
                        "westwood_2", "criminal_1", "criminal_2",
                        "business_1"
                };
                break;
            }
            case "Game development":{
                designs = new String[]{
                        "hacker_1", "hacker_2", "cyberpunk_2",
                        "business_1", "business_2", "cinema_1",
                        "cinema_2"
                };
                break;
            }
            case "Government":{
                designs = new String[]{
                        "business_1", "business_2", "construction_1",
                        "construction_2", "city_1", "city_2",
                        "westwood_2"
                };
                break;
            }
            case "Fantasy":{
                designs = new String[]{
                        "medieval_1", "medieval_2", "horror_1"
                };
                break;
            }
            case "Farm":{
                designs = new String[]{
                        "virtual_animal_1", "virtual_animal_2", "business_2"
                };
                break;
            }
            case "Fashion":{
                designs = new String[]{
                        "cyberpunk_2", "dance_1", "detective_2",
                        "school_2",
                };
                break;
            }
            case "Hacker":{
                designs = new String[]{
                        "cyberpunk_1", "cyberpunk_2", "detective_1"
                };
                break;
            }
            case "Hospital":{
                designs = new String[]{
                        "virtual_animal_1", "horror_1", "government_1"
                };
                break;
            }
            case "Horror":{
                designs = new String[]{
                        "vampires_1", "vampires_2", "zombie_1",
                        "zombie_2"
                };
                break;
            }
            case "Hunting":{
                designs = new String[]{
                        "horror_1", "farm_2", "aliens_2",
                        "fantasy_2", "fashion_2"
                };
                break;
            }
            case "Life":{
                designs = new String[]{
                        "school_1", "school_2", "romantic_2",
                        "music_1", "music_2", "dance_1",
                        "comedy_2"
                };
                break;
            }
            case "Medieval":{
                designs = new String[]{
                        "fantasy_1", "fantasy_2", "pirates_1",
                        "pirates_2"
                };
                break;
            }
            case "Music":{
                designs = new String[]{
                        "dance_1", "dance_2", "rhythm_1",
                        "rhythm_2"
                };
                break;
            }
            case "Ninja":{
                designs = new String[]{
                        "horror_2", "criminal_1", "criminal_2",
                        "detective_2", "medieval_1", "westwood_2"
                };
                break;
            }
            case "Prison":{
                designs = new String[]{
                        "criminal_1", "criminal_2", "cyberpunk_1"
                };
                break;
            }
            case "Pirates":{
                designs = new String[]{
                        "hacker_1", "hacker_2", "prison_1",
                        "westwood_2", "criminal_1", "criminal_2"
                };
                break;
            }
            case "Race":{
                designs = new String[]{
                        "transport_1", "transport_2", "time_travel_1",
                        "time_travel_2", "pirates_1"
                };
                break;
            }
            case "Romantic":{
                designs = new String[]{
                        "music_1", "music_2", "school_2",
                        "hospital_1"
                };
                break;
            }
            case "Rhythm":{
                designs = new String[]{
                        "dance_1", "dance_2", "music_1",
                        "music_2"
                };
                break;
            }
            case "School":{
                designs = new String[]{
                        "business_1", "business_2", "game_development_1",
                        "game_development_2", "sport_2"
                };
                break;
            }
            case "Space":{
                designs = new String[]{
                        "aliens_1", "aliens_2", "aviation_1",
                        "aviation_2", "cyberpunk_1", "cyberpunk_2"
                };
                break;
            }
            case "Sport":{
                designs = new String[]{
                        "race_1", "race_2", "business_2"
                };
                break;
            }
            case "Superheros":{
                designs = new String[]{
                        "life_1", "life_2", "criminal_1",
                        "criminal_2", "detective_1", "detective_2",
                        "ninja_2", "hospital_1"
                };
                break;
            }
            case "Time traveling":{
                designs = new String[]{
                        "space_2", "war_1", "war_2",
                        "cyberpunk_1", "cyberpunk_2", "detective_1"
                };
                break;
            }
            case "Transport":{
                designs = new String[]{
                        "race_1", "race_2", "space_1",
                        "aviation_1", "pirates_1"
                };
                break;
            }
            case "Vampires":{
                designs = new String[]{
                        "zombie_1", "zombie_2", "horror_1",
                        "horror_2", "hunting_1"
                };
                break;
            }
            case "Virtual animals":{
                designs = new String[]{
                        "farm_1", "farm_2", "race_2",
                        "aliens_2"
                };
                break;
            }
            case "War":{
                designs = new String[]{
                        "medieval_1", "ninja_1", "ninja_2",
                        "westwood_1", "zombie_1", "zombie_2",
                        "aviation_2", "pirates_1", "pirates_2"
                };
                break;
            }
            case "Wild west":{
                designs = new String[]{
                        "war_2", "farm_1", "criminal_1",
                        "criminal_2", "business_1"
                };
                break;
            }
            default:{ // case "Zombie":
                designs = new String[]{
                        "war_1", "war_2", "government_1",
                        "criminal_1"
                };
                break;
            }
        }

        return Utils.isInArray(designs, genre);
    }

    private int calculateScore() {
        return getGenreThemeCompatibility() + design + programming + gameDesign;
    }

    private int getGenreThemeCompatibility(){
        String[] themes;

        switch (genre) {
            case "Shooter":{
                themes = new String[]{
                        "Aliens", "Aviation", "Criminal", "Cyberpunk", "Detective",
                        "Hunting", "Pirates", "School", "Space", "Superheros",
                        "Vampires", "War", "Wild west", "Zombie"
                };
                break;
            }
            case "Arcade":{
                themes = new String[]{
                        "Comedy", "Construction", "Cooking", "Dance", "Detective",
                        "Fantasy", "Farm", "Game development", "Government", "Music",
                        "Ninja", "Race", "Rhythm", "Romantic", "Space",
                        "Time traveling", "Virtual animals"
                };
                break;
            }
            case "Strategy":{
                themes = new String[]{
                        "Aliens", "Aviation", "Business", "Cinema", "City",
                        "Construction", "Fashion", "Game development", "Government",
                        "Hospital", "Life", "Medieval", "Romantic", "School", "Space",
                        "Sport", "Transport", "War"
                };
                break;
            }
            case "RPG":{
                themes = new String[]{
                        "Aliens", "Criminal", "Cyberpunk", "Detective", "Fantasy",
                        "Hacker", "Life", "Medieval", "Ninja", "Pirates", "Space",
                        "Sport", "Vampires", "War", "Zombie"
                };
                break;
            }
            case "Platform":{
                themes = new String[]{
                        "Aliens", "City", "Criminal", "Cyberpunk", "Fantasy", "Hacker",
                        "Medieval", "Ninja", "Prison", "Space", "Superheros",
                        "Time traveling", "Transport", "Vampires", "War", "Zombie"
                };
                break;
            }
            case "Stealth":{
                themes = new String[]{
                        "Aliens", "Criminal", "Detective", "Fantasy", "Hacker",
                        "Horror", "Hunting", "Medieval", "Ninja", "Prison", "Time traveling",
                        "Vampires", "War", "Wild west", "Zombie"
                };
                break;
            }
            case "Survival":{
                themes = new String[]{
                        "Aliens", "Comedy", "Cyberpunk", "Fantasy", "Fashion",
                        "Game development", "Government", "Horror", "Hospital",
                        "Hunting", "Life", "Medieval", "Pirates", "Prison", "Space",
                        "War", "Zombie"
                };
                break;
            }
            case "Action":{
                themes = new String[]{
                        "Aliens", "Aviation", "Cinema", "Criminal", "Cyberpunk",
                        "Detective", "Hacker", "Horror", "Hunting", "Life",
                        "Medieval", "Ninja", "Pirates", "Prison", "Space",
                        "Superheros", "Vampires", "War", "Wild west", "Zombie"
                };
                break;
            }
            default:{   // case "Quest":
                themes = new String[]{
                        "Aliens", "Cinema", "Comedy", "Criminal", "Cyberpunk",
                        "Detective", "Fantasy", "Farm", "Fashion", "Hacker",
                        "Hospital", "Medieval", "Prison", "Romantic", "School",
                        "Time traveling", "Transport"
                };
                break;
            }
        }

        return Utils.isInArray(themes, theme) ? 1 : 0;
    }

    private int calculateExperienceScore() {
        return score > 7 ?
                3 : score > 3 ?
                2 : 1;
    }

    private void calculateLevel() {
        requiredExp = 0;
        int oldLvl = stats.getLevel();

        switch (oldLvl) {
            case 1:
                requiredExp = 50;
                break;
            case 2:
                requiredExp = 75;
                break;
            case 3:
                requiredExp = 100;
                break;
            case 4:
                requiredExp = 150;
                break;
            case 5:
                requiredExp = 175;
                break;
            default:
                requiredExp = 200;
                break;
        }

        int newExp = stats.getExperience() + exp;

        if (newExp >= requiredExp) {
            lvl = stats.getLevel() + 1;
        } else {
            lvl = stats.getLevel();
            requiredExp = 0;
        }
    }

    public int getDesign() {
        return design;
    }

    public int getProgramming() {
        return programming;
    }

    public int getGameDesign() {
        return gameDesign;
    }

    public int getScore() {
        return score;
    }

    public int getExp() {
        return exp;
    }

    public int getLvl() {
        return lvl;
    }

    public int getProfitMoney() {
        return profitMoney;
    }

    public int getSellTime() {
        return sellTime;
    }

    public void setNewValues(){
        stats.setDesign(stats.getDesign() + design);
        stats.setProgramming(stats.getProgramming() + programming);
        stats.setGameDesign(stats.getGameDesign() + gameDesign);
        stats.setLevel(lvl);
        stats.setExperience(stats.getExperience() + exp, requiredExp);
    }
}
