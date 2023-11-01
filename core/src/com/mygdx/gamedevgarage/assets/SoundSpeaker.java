package com.mygdx.gamedevgarage.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundSpeaker implements Speaker{

    private final String[] soundNames = new String[]{
            "button_click", "buying_cover", "buying_mech",
            "buying_tech", "end_screen", "screen_end",
            "buying_platform", "platform_unavailable",
            "game_sold", "lvl_up", "item_checked",
            "item_unchecked", "love_quit", "bam",
            "car", "disappointment", "fear_of_cancer",
            "hospital_noise", "keyboard", "liza",
            "love_loud", "new_mail", "scroll",
            "car_accident", "angry", "backspace",
            "before_diagnosis", "click", "comment_1",
            "comment_2", "coughing", "damn",
            "hi", "hm", "it_nice", "jane_laugh",
            "jane_night", "liza_in_cafe", "liza_laugh",
            "liza_lesson", "liza_waiting", "park",
            "thats_great", "whispering", "yes_finally",
            "eating", "not_eating"
    };

    private final HashMap<String, Sound> sounds = new HashMap<>();

    public SoundSpeaker() {
        for (String soundName : soundNames) {
            sounds.put(soundName, Gdx.audio.newSound(Gdx.files.internal("sounds/" + soundName + ".mp3")));
        }
    }

    @Override
    public void play(String name) {
        sounds.get(name).play(Assets.getInstance().getVolume());
    }

    public void playInterrupted(String sound) {
        for (String name : sounds.keySet()) {
            if(!name.equals(sound))
                sounds.get(name).stop();
        }
        sounds.get(sound).play(Assets.getInstance().getVolume());
    }

    @Override
    public void dispose() {
        for (String name : soundNames) {
            sounds.get(name).dispose();
        }
    }
}
