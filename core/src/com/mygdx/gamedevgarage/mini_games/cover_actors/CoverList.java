package com.mygdx.gamedevgarage.mini_games.cover_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;

public class CoverList extends Table {

    private final Array<CoverListItem> items;
    private final Image coverImage;
    private final CoverMainActor parent;

    public CoverList(Game game, Image coverImage, boolean isColorList, CoverMainActor parent, Assets assets) {
        super(assets.getSkin());

        this.coverImage = coverImage;
        this.parent = parent;

        if(isColorList){
            items = DataArrayFactory.initColors(assets);
            addColorItems();
        } else {
            items = DataArrayFactory.createCoverObjects(game);
            addObjectItems();
        }
    }

    private void addColorItems() {
        for (CoverListItem item : items) {
            add(item).width(getWidthPercent(.85f)).height(getHeightPercent(.2f))
                    .row();
            addClickListener(item);
        }
    }

    private void addObjectItems() {
        int itemsPerRow = 41;

        for (int i = 0; i < items.size; i++) {
            CoverListItem item = items.get(i);
            if(item.isPurchased()) {
                add(item).pad(5).width(item.getImage().getWidth()).height(item.getImage().getHeight());

                if (i % itemsPerRow == itemsPerRow - 1) {
                    row();
                }
                addClickListenerForObjects(item);
            }
        }
    }

    private void addClickListener(final CoverListItem item) {
        item.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                coverImage.setDrawable(item.getImage().getDrawable());
                parent.setSelectedColor(item.getText());
            }
        });
    }

    private void addClickListenerForObjects(final CoverListItem item) {
        item.addListener(new ClickListener() {
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

                coverImage.setDrawable(item.getImage().getDrawable());
                parent.setSelectedObject(item.getText());
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
