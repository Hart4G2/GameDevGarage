package com.mygdx.gamedevgarage.screens.mini_games.selection_actors;

import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.utils.data.CheckObject;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends Table {

    private final List<CheckListItem> items;
    private final List<CheckObject> selectedItems;

    public CheckList(List<CheckListItem> items) {
        super(Assets.getInstance().getSkin());
        this.items = items;
        selectedItems = new ArrayList<>();

        addItems();
    }

    private void addItems() {
        for (CheckListItem item : items) {
            if(item.getCheckObject().isPurchased()) {
                add(item).width(getWidthPercent(.95f)).height(getHeightPercent(.2f)).row();
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
            removeItem(item.getCheckObject());
        } else {
            if(selectedItems.size() < 3) {
                item.setSelected();
                addItem(item.getCheckObject());
            }
        }
    }

    public void render(float delta){
        for(CheckListItem item : items){
            item.render(delta);
        }
    }

    public List<CheckObject> getSelectedItems() {
        return selectedItems;
    }

    private void removeItem(CheckObject item){
        if(selectedItems.contains(item)){
            selectedItems.remove(item);
            playSound("item_unchecked");
        }
    }

    private void addItem(CheckObject item){
        selectedItems.add(item);
        playSound("item_checked");
    }

    private void playSound(String name){
        Assets.getInstance().setSound(name);
    }
}
