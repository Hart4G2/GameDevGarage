package com.mygdx.gamedevgarage.utils.reward;

import static com.mygdx.gamedevgarage.utils.data.DataArrayFactory.platformNames;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.genre;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.mechanics;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.object;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.previousGenre;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.previousTheme;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.technologies;
import static com.mygdx.gamedevgarage.utils.data.GameFactory.theme;

import com.mygdx.gamedevgarage.utils.stats.Cost;
import com.mygdx.gamedevgarage.utils.Utils;
import com.mygdx.gamedevgarage.utils.constraints.Currency;
import com.mygdx.gamedevgarage.utils.data.GameFactory;
import com.mygdx.gamedevgarage.utils.stats.Stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Reward {

    private final Stats stats;
    private static Reward instance;

    private Reward() {
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
    private int energy;
    private int profitMoney;
    private int sellTime;
    private List<String> possibleHints;

    public void calculateScores(){
        possibleHints = new ArrayList<>();

        design = calculateDesignScore();
        programming = calculateTechScore();
        gameDesign = calculateMechanicScore();
        score = calculateScore();
        exp = calculateExperienceScore();
        calculateLevel();
        energy = calculateEnergy();
        calculateSellParams();
    }

    private void calculateSellParams() {
        Random r = new Random();

        int randomBonus = r.nextInt(3);
        int platformBonus = 0;

        for(int i = 0; i < platformNames.length; i++){
            if(platformNames[i].equals(GameFactory.platform)){
                platformBonus = i;
            }
        }

        if(platformBonus == 0) {
            possibleHints.add("platform_increase_money");
        }

        profitMoney = (score + randomBonus) * (5 + platformBonus);
        sellTime = score * 5;
    }

    private int calculateTechScore() {
        int result = 0;
        for(String tech : technologies){
            result += calculateTechnologyScore(tech);
        }

        result = result / technologies.size();

        int diff = 3 - technologies.size();
        result = Math.max(0, result - diff);

        if(result == 0)
            possibleHints.add("low_tech_score");

        return result;
    }

    private int calculateTechnologyScore(String technology) {
        String[] primaryTechnologies;
        String[] secondaryTechnologies;

        switch (genre.getBundleKey()) {
            case "Shooter":{
                primaryTechnologies = new String[]{
                        "Physics_of_motion", "Surround_sound", "Interactive_sound", "Multiplayer",
                        "Procedural_level_generation", "Realistic_destruction_physics",
                };
                secondaryTechnologies = new String[]{
                        "Virtual_reality", "Volumetric_Effects"
                };
                break;
            }
            case "Arcade":{
                primaryTechnologies = new String[]{
                        "Virtual_reality", "Gesture_control_system", "Reactive_environment", "Loading_screens",
                        "Procedural_level_generation",
                };
                secondaryTechnologies = new String[]{
                        "Multiplayer", "Realistic destruction_physics", "Interactive_sound"
                };
                break;
            }
            case "Strategy":{
                primaryTechnologies = new String[]{
                        "Reactive_environment", "Artificial_intelligence", "Realistic_illumination",
                        "Procedural_level_generation", "Photorealism", "Loading_screens"
                };
                secondaryTechnologies = new String[]{
                        "Multiplayer", "Volumetric_Effects", "Surround_sound", "Procedural_animation"
                };
                break;
            }
            case "RPG":{
                primaryTechnologies = new String[]{
                        "Physics_of_motion", "Procedural_level_generation",
                        "Volumetric_Effects", "Integration_with_cloud_services",
                        "Reactive_environment", "Dynamic_change_of_time_of_day", "Multiplayer"
                };
                secondaryTechnologies = new String[]{
                        "Volumetric_Effects", "Interactive_sound", "Loading_screens", "Surround_sound"
                };
                break;
            }
            case "Platform":{
                primaryTechnologies = new String[]{
                        "Procedural_level_generation", "Realistic_destruction_physics", "Interactive_sound",
                        "Gesture_control_system", "Reactive environment", "Dynamic_change_of_time_of_day"
                };
                secondaryTechnologies = new String[]{
                        "Physics_of_motion", "Volumetric_Effects", "Loading_screens"
                };
                break;
            }
            case "Stealth":{
                primaryTechnologies = new String[]{
                        "Physics_of_motion", "Artificial_intelligence", "Surround_sound",
                        "Photorealism", "Virtual_reality", "Animated_videos",
                        "Interactive_sound", "Procedural_animation",
                        "Reactive_environment"
                };
                secondaryTechnologies = new String[]{
                        "Multiplayer", "Physics_of_motion", "Volumetric_Effects", "Add gamepad_vibration"
                };
                break;
            }
            case "Survival":{
                primaryTechnologies = new String[]{
                        "Physics_of_motion", "Photorealism", "Procedural_level_generation",
                        "Realistic_illumination", "Volumetric_Effects", "Interactive_sound",
                        "Reactive_environment", "Dynamic_change_of_time of day", "Photomode"
                };
                secondaryTechnologies = new String[]{
                        "Multiplayer", "Physics_of_motion", "Procedural_animation",
                        "Add_gamepad_vibration", "Virtual_reality", "Surround_sound"
                };
                break;
            }
            case "Action":{
                primaryTechnologies = new String[]{
                        "Physics_of_motion", "Surround_sound", "Photorealism",
                        "Virtual_reality", "Multiplayer", "Interactive_sound",
                        "Procedural_animation"
                };
                secondaryTechnologies = new String[]{
                        "Multiplayer", "Procedural_level_generation", "Artificial_intelligence",
                        "Photomode", "Add_gamepad_vibration"
                };
                break;
            }
            default:{   // case "Quest":
                primaryTechnologies = new String[]{
                        "Procedural_level_generation", "Gesture_control_system",
                        "Reactive_environment", "Physics_of_motion"
                };
                secondaryTechnologies = new String[]{
                        "Virtual_reality", "Multiplayer", "Artificial_intelligence",
                        "Surround_sound"
                };
                break;
            }
        }

        int r = new Random().nextInt(11) > 6 ? 1 : 0;

        if(Utils.isInArray(primaryTechnologies, technology)) {
            return 2 + r;
        } else if(Utils.isInArray(secondaryTechnologies, technology)) {
            return 1 + r;
        } else {
            return r;
        }
    }

    HashMap<String, String> exceptions = new HashMap<String, String>() {{
        put("Online_multiplayer", "Multiplayer");
        put("Complete_destruction_of_the_environment", "Realistic_destruction_physics");
        put("Environmental_influence", "Reactive_environment");
    }};

    private int calculateMechanicScore() {
        int result = 0;

        boolean containsException = false;
        for(String mech : mechanics){
            result += calculateMechanicScore(mech);

            if(exceptions.containsKey(mech) &&
                    technologies.contains(exceptions.get(mech))) {
                result++;
                containsException = true;
            }
        }

        if(!containsException) {
            possibleHints.add("tech_bonus");
        }

        result = result / mechanics.size();

        int diff = 3 - mechanics.size();
        result = Math.max(0, result - diff);

        if(result == 0)
            possibleHints.add("low_mech_score");

        return result;
    }

    private int calculateMechanicScore(String mechanic) {
        String[] primaryMechanics;
        String[] secondaryMechanics;

        switch (genre.getBundleKey()) {
            case "Shooter":{
                primaryMechanics = new String[]{
                        "Free_movement_on_the_map", "First_person_camera_control", "Dodging_and_blocking",
                        "Squad_formation", "Change_of_perspective", "Character_evolution", "Complete_destruction_of_the_environment"
                };
                secondaryMechanics = new String[]{
                        "Time_slows_down", "Nonlinear_plot", "Stealth/Invisibility", "Online_multiplayer",
                        "Lots_of_playable_characters", "Base_leveling", "Identity substitution", "Time_attack"
                };
                break;
            }
            case "Arcade":{
                primaryMechanics = new String[]{
                        "Time_slows_down", "Multiplayer_on_one_screen", "Split-dresser_mode", "Time_attack",
                        "Online_multiplayer"
                };
                secondaryMechanics = new String[]{
                        "First_person_camera_control", "Dodging_and_blocking", "Stealth/Invisibility",
                        "Lots_of_playable_characters", "Environmental_influence", "Change_of_perspective",
                        "Creation_of_unique_game_elements_by_the_player"
                };
                break;
            }
            case "Strategy":{
                primaryMechanics = new String[]{
                        "Free_movement_on_the_map", "Lots_of_playable_characters",
                        "Squad_formation", "Change_of_perspective", "Base_leveling",
                        "Character_evolution", "Online_multiplayer"
                };
                secondaryMechanics = new String[]{
                        "Time_slows_down", "Environmental influence", "Split-dresser_mode",
                        "Multiplayer_on_one_screen", "Complete_destruction_of_the_environment"
                };
                break;
            }
            case "RPG":{
                primaryMechanics = new String[]{
                        "Free_movement_on_the_map", "First_person_camera_control", "Nonlinear_plot",
                        "Dodging_and_blocking", "Dialogue_selection_system", "Stealth/Invisibility",
                        "Lots_of_playable_characters", "Squad_formation", "Character_evolution",
                        "Online_multiplayer"
                };
                secondaryMechanics = new String[]{
                        "Time_slows_down", "Environmental_influence", "Squad_formation",
                        "Creation_of_unique game_elements_by_the_player"
                };
                break;
            }
            case "Platform":{
                primaryMechanics = new String[]{
                        "Time_slows_down", "Dodging_and_blocking", "Multiplayer_on_one_screen",
                        "Lots_of_playable_characters", "Split-dresser_mode"
                };
                secondaryMechanics = new String[]{
                        "Free_movement_on_the_map", "Environmental_influence", "Change_of_perspective",
                        "Character_evolution", "Online_multiplayer",
                };
                break;
            }
            case "Stealth":{
                primaryMechanics = new String[]{
                        "Free_movement_on_the_map", "Time_slows_down", "First_person_camera_control",
                        "Nonlinear_plot", "Dodging_and_blocking", "Dialogue_selection_system",
                        "Stealth/Invisibility", "Identity_substitution", "Time attack"
                };
                secondaryMechanics = new String[]{
                        "Lots_of_playable_characters", "Environmental_influence", "Change_of_perspective",
                        "Character_evolution", "Online_multiplayer", "Complete destruction_of_the_environment"
                };
                break;
            }
            case "Survival":{
                primaryMechanics = new String[]{
                        "Free_movement_on_the_map", "First_person_camera_control", "Lots_of_playable_characters",
                        "Environmental_influence", "Base_leveling", "Character_evolution",
                        "Creation_of_unique_game_elements_by_the player", "Complete_destruction_of_the_environment"
                };
                secondaryMechanics = new String[]{
                        "Dodging_and_blocking", "Multiplayer_on_one_screen", "Squad_formation",
                        "Change_of_perspective", "Online_multiplayer"
                };
                break;
            }
            case "Action":{
                primaryMechanics = new String[]{
                        "Free_movement_on_the_map", "First_person_camera_control",
                        "Dodging_and_blocking", "Lots_of_playable_characters",
                        "Change_of_perspective",
                };
                secondaryMechanics = new String[]{
                        "Time_slows_down", "Dialogue_selection_system", "Nonlinear_plot",
                        "Stealth/Invisibility", "Squad_formation", "Identity_substitution",
                        "Time_attack", "Character_evolution", "Online_multiplayer"
                };
                break;
            }
            default:{   // case "Quest":
                primaryMechanics = new String[]{
                        "Free_movement_on_the_map", "Time_slows_down", "First_person_camera control",
                        "Nonlinear_plot", "Dialogue_selection_system", "Environmental_influence",
                        "Change_of_perspective", "Time_attack", "Creation_of_unique_game_elements_by_the_player",
                        "Complete_destruction_of_the_environment"
                };
                secondaryMechanics = new String[]{
                        "Dodging_and_blocking", "Multiplayer_on_one_screen", "Stealth/Invisibility",
                        "Lots_of_playable characters", "Identity_substitution", "Online_multiplayer",
                };
                break;
            }
        }

        int r = new Random().nextInt(11) > 6 ? 1 : 0;

        if(Utils.isInArray(primaryMechanics, mechanic)) {
            return 2 + r;
        } else if(Utils.isInArray(secondaryMechanics, mechanic)) {
            return 1 + r;
        } else {
            return r;
        }
    }

    private int calculateDesignScore() {
        int random = new Random().nextInt(11);

        if(isFirstDesign()){
            return random > 6 ? 3 : 2;
        }
        if (isSecondaryDesign()){
            return random > 6 ? 2 : 1;
        }
        possibleHints.add("low_design_score");
        return 0;
    }

    private boolean isFirstDesign(){
        String themeName = theme.getBundleKey();

        String design1 = themeName + "_1";
        String design2 = themeName + "_2";

        return object.equals(design1) || object.equals(design2);
    }

    private boolean isSecondaryDesign(){
        String[] designs;

        switch (theme.getBundleKey()) {
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

        return Utils.isInArray(designs, genre.getBundleKey());
    }

    private int calculateScore() {
        int energy = stats.getStat(Currency.ENERGY);

        int score = getGenreThemeCompatibility() + design + programming + gameDesign;

        if(energy <= 5)
            possibleHints.add("low_energy");

        if(energy > 9) {
            score = Math.min(10, score + 1);
        } else if(energy > 7){
            score = Math.max(1, score - 1);
        } else if(energy > 5){
            score = Math.max(1, score - 2);
        } else if(energy > 3){
            score = Math.max(1, score - 3);
        } else if(energy > 1){
            score = Math.max(1, score - 4);
        } else {
            score = Math.max(1, score - 5);
        }

        if(previousGenre != null && previousTheme != null) {
            if (previousGenre.equals(genre.getBundleKey()) && previousTheme.equals(theme.getBundleKey())) {
                int r = new Random().nextInt(2) + 1;
                score = Math.max(1, score - r);
                possibleHints.add("same_game");
            }
        }

        return score;
    }

    private int calculateEnergy() {
        int r = new Random().nextInt(2);

        if(score > 9){
            return r + 2;
        } else if(score > 7){
            return r + 1;
        } else if(score > 3) {
            return r;
        } else {
            return 0;
        }
    }

    private int getGenreThemeCompatibility(){
        String[] themes;

        switch (genre.getBundleKey()) {
            case "Shooter":{
                themes = new String[]{
                        "Aliens", "Aviation", "Criminal", "Cyberpunk", "Detective",
                        "Hunting", "Pirates", "School", "Space", "Superheros",
                        "Vampires", "War", "Wild_west", "Zombie"
                };
                break;
            }
            case "Arcade":{
                themes = new String[]{
                        "Comedy", "Construction", "Cooking", "Dance", "Detective",
                        "Fantasy", "Farm", "Game_development", "Government", "Music",
                        "Ninja", "Race", "Rhythm", "Romantic", "Space",
                        "Time_traveling", "Virtual_animals"
                };
                break;
            }
            case "Strategy":{
                themes = new String[]{
                        "Aliens", "Aviation", "Business", "Cinema", "City",
                        "Construction", "Fashion", "Game_development", "Government",
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
                        "Time_traveling", "Transport", "Vampires", "War", "Zombie"
                };
                break;
            }
            case "Stealth":{
                themes = new String[]{
                        "Aliens", "Criminal", "Detective", "Fantasy", "Hacker",
                        "Horror", "Hunting", "Medieval", "Ninja", "Prison", "Time_traveling",
                        "Vampires", "War", "Wild_west", "Zombie"
                };
                break;
            }
            case "Survival":{
                themes = new String[]{
                        "Aliens", "Comedy", "Cyberpunk", "Fantasy", "Fashion",
                        "Game_development", "Government", "Horror", "Hospital",
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
                        "Superheros", "Vampires", "War", "Wild_west", "Zombie"
                };
                break;
            }
            default:{   // case "Quest":
                themes = new String[]{
                        "Aliens", "Cinema", "Comedy", "Criminal", "Cyberpunk",
                        "Detective", "Fantasy", "Farm", "Fashion", "Hacker",
                        "Hospital", "Medieval", "Prison", "Romantic", "School",
                        "Time_traveling", "Transport"
                };
                break;
            }
        }

        if(!Utils.isInArray(themes, theme.getBundleKey())){
            possibleHints.add("genre_theme_compatibility");
            return 0;
        } else {
            return 1;
        }
    }

    private int calculateExperienceScore() {
        return score > 7 ?
                3 : score > 3 ?
                2 : 1;
    }

    private void calculateLevel() {
        requiredExp = 0;
        int oldLvl = stats.getStat(Currency.LEVEL);

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

        int newExp = stats.getStat(Currency.EXPERIENCE) + exp;

        if (newExp >= requiredExp) {
            lvl = oldLvl + 1;
        } else {
            lvl = oldLvl;
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

    public int getEnergy() {
        return energy;
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

    public void setDesign(int design) {
        this.design = design;
    }

    public void setProgramming(int programming) {
        this.programming = programming;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setGameDesign(int gameDesign) {
        this.gameDesign = gameDesign;
    }

    public void setProfitMoney(int profitMoney) {
        this.profitMoney = profitMoney;
    }

    public void setSellTime(int sellTime) {
        this.sellTime = sellTime;
    }

    public List<String> getHints(){
        return possibleHints;
    }

    public void setNewValues(){
        Random r = new Random();

        int design = r.nextInt(Math.max(1, this.design));
        int programming = r.nextInt(Math.max(1, this.programming));
        int gameDesign = r.nextInt(Math.max(1, this.gameDesign));
        int energy = r.nextInt(Math.max(1, this.energy));

        Cost cost = new Cost(new Currency[]{Currency.DESIGN, Currency.PROGRAMMING, Currency.GAME_DESIGN, Currency.ENERGY},
                new int[]{-design, -programming, -gameDesign, -energy});

        stats.pay(cost);
        stats.setLevel(lvl);
        stats.setExperience(stats.getStat(Currency.EXPERIENCE) + exp, requiredExp);
    }
}
