package com.mygdx.gamedevgarage.mini_games.selection_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.mini_games.MiniGameScreen;

public class CheckList extends Table {
    private final Array<CheckListItem> items;
    private final MiniGameScreen parent;

    public CheckList(Array<CheckListItem> items, final MiniGameScreen parent, final Assets assets) {
        super(assets.getSkin());

        this.items = items;
        this.parent = parent;

        addItems();
    }

    private void addItems() {
        for (CheckListItem item : items) {
            if(item.isPurchased()) {
                add(item).width(getWidthPercent(.8f)).height(getHeightPercent(.15f)).row();
                addClickListener(item);
            }
        }
    }

    private void addClickListener(final CheckListItem item) {
        item.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                itemClicked(item);
            }
        });
    }

    private void itemClicked(CheckListItem item){
        if(item.isSelected()){
            item.setUnselected();
            parent.removeItem(item.getText());
        } else {
            item.setSelected();
            parent.addItem(item.getText());
        }
    }

    public void render(float delta){
        for(CheckListItem item : items){
            item.render(delta);
        }
    }
}
