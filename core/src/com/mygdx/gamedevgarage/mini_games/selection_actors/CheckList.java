package com.mygdx.gamedevgarage.mini_games.selection_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;

public class CheckList extends Table {

    private final Array<CheckListItem> items;
    private final Array<CheckListItem> selectedItems;

    public CheckList(Array<CheckListItem> items) {
        super(Assets.getInstance().getSkin());
        this.items = items;
        selectedItems = new Array<>();

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
            removeItem(item);
        } else {
            item.setSelected();
            addItem(item);
        }
    }

    public void render(float delta){
        for(CheckListItem item : items){
            item.render(delta);
        }
    }

    public Array<CheckListItem> getSelectedItems() {
        return selectedItems;
    }

    private void removeItem(CheckListItem item){
        if(selectedItems.contains(item, true)){
            selectedItems.removeValue(item, true);
            playSound("item_unchecked");
        }
    }

    private void addItem(CheckListItem item){
        selectedItems.add(item);
        playSound("item_checked");
    }

    private void playSound(String name){
        Assets.getInstance().setSound(name);
    }
}
