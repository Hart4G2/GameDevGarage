package com.mygdx.gamedevgarage.mini_games.cover_actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;

public class CoverList extends Table {
    private final Array<CoverListItem> items;
    private final Image img;
    private final CoverMainActor parent;

    public CoverList(Array<CoverListItem> items, final Image img, boolean isColorList, final CoverMainActor parent, final Assets assets) {
        super(assets.getSkin());

        this.items = items;
        this.img = img;
        this.parent = parent;

        if(isColorList){
            addColorItems();
        } else {
            addObjectItems(assets.designObjectsAtlas);
        }
    }

    private void addColorItems() {
        for (CoverListItem item : items) {
            add(item.getLabel()).pad(5);
            add(item.getImage()).pad(5).row();
            addClickListener(item);
        }
    }

    private void addObjectItems(TextureAtlas objectsAtlas) {
        final Array<CoverListItem> objects = parent.initObjects(objectsAtlas);
        final int itemsPerRow = 28;

        for (int i = 0; i < items.size; i++) {
            final CoverListItem item = items.get(i);
            add(item.getImage()).padRight(20).padTop(10).padBottom(10);

            if (i % itemsPerRow == itemsPerRow - 1) {
                row();
            }

            addClickListenerForObjects(item, objects);
        }
    }

    private void addClickListener(final CoverListItem item) {
        item.getLabel().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                img.setDrawable(item.getImage().getDrawable());
                parent.setSelectedColor(item.getText());
            }
        });

        item.getImage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                img.setDrawable(item.getImage().getDrawable());
                parent.setSelectedColor(item.getText());
            }
        });
    }

    private void addClickListenerForObjects(final CoverListItem item, final Array<CoverListItem> objects) {
        item.getImage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                item.getImage().clearActions();

                float duration = 0.11f;

                Action clickAnimation = Actions.sequence(
                        Actions.sequence(
                                Actions.alpha(0.1f, duration),
                                Actions.alpha(1f, duration)
                        )
                );

                item.getImage().addAction(clickAnimation);

                String imageName = item.getText();

                for (CoverListItem objItem : objects) {
                    if (objItem.getText().equals(imageName)) {
                        img.setDrawable(objItem.getImage().getDrawable());
                        parent.setSelectedObject(objItem.getText());
                        break;
                    }
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);

                float duration = 0.1f;
                float scaleAmount = 1.2f;
                float alphaAmount = 0.8f;

                Action clickAnimation = Actions.sequence(
                        Actions.parallel(
                                Actions.scaleTo(scaleAmount, scaleAmount, duration),
                                Actions.alpha(alphaAmount, duration)
                        )
                );

                item.getImage().addAction(clickAnimation);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);

                float duration = 0.1f;

                Action clickAnimation = Actions.sequence(
                        Actions.parallel(
                                Actions.scaleTo(1f, 1f, duration),
                                Actions.alpha(1f, duration)
                        )
                );

                item.getImage().addAction(clickAnimation);
            }
        });
    }
}
