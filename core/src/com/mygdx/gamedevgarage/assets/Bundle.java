package com.mygdx.gamedevgarage.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.utils.DataManager;

import java.util.Locale;

public class Bundle {

    private final String[] languages = new String[]{
            "English",
            "Русский",
            "Español",
            "Deutsch",
            "Français",
            "Беларускі",
            "Português"
    };
    private String language;
    private I18NBundle bundle;

    public Bundle() {
        language = DataManager.getInstance().getLanguage();
        setBundle();
    }

    private void setBundle(){
        Locale locale;

        switch (language) {
            case "Русский":
                locale = new Locale("ru");
                break;
            case "Español":
                locale = new Locale("es");
                break;
            case "Deutsch":
                locale = new Locale("de");
                break;
            case "Français":
                locale = new Locale("fr");
                break;
            case "Беларускі":
                locale = new Locale("by");
                break;
            case "Português":
                locale = new Locale("pt");
                break;
            default:
                locale = Locale.ENGLISH;
        }

        bundle = I18NBundle.createBundle(Gdx.files.internal("localization/indie"), locale);
    }

    public void setLanguage(String language) {
        this.language = language;
        setBundle();
    }

    public String getLanguage() {
        return language;
    }

    public I18NBundle getBundle() {
        return bundle;
    }

    public String[] getLanguages() {
        return languages;
    }
}
