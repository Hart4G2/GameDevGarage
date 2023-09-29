package com.mygdx.gamedevgarage.utils.data;

import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.gamedevgarage.Assets;

public class TranslatableObject {

    private String text;
    private String bundleKey;

    public TranslatableObject(String bundleKey) {
        this.bundleKey = bundleKey;

        I18NBundle bundle = Assets.getInstance().myBundle;
        this.text = bundle.get(bundleKey);
    }

    public String getText() {
        return text;
    }

    public String getBundleKey() {
        return bundleKey;
    }
}
