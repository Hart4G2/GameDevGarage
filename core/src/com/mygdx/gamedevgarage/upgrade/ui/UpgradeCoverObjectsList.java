package com.mygdx.gamedevgarage.upgrade.ui;

import static com.mygdx.gamedevgarage.utils.Utils.createBuyButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;
import static com.mygdx.gamedevgarage.utils.constraints.Constants.UPGRADE_OBJECT_COST;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.cover_actors.CoverListItem;
import com.mygdx.gamedevgarage.stats.Stats;
import com.mygdx.gamedevgarage.utils.data.DataArrayFactory;

public class UpgradeCoverObjectsList extends Table {

    private final Game game;
    private final Stats stats;

    private final Array<CoverListItem> items;
    private final Array<TextButton> buttons;
    private final Drawable buttonIcon;

    public UpgradeCoverObjectsList() {
        super(Assets.getInstance().getSkin());
        this.game = Game.getInstance();
        this.stats = Stats.getInstance();
        this.items = DataArrayFactory.createCoverObjects();
        this.buttonIcon = getSkin().getDrawable("design");

        buttons = new Array<>();
        addItems();
    }

    private void addItems() {
        for (CoverListItem item : items) {
            if(!item.isPurchased()) {
                TextButton button = createTextButton(item.getText());

                addClickListener(button);
                buttons.add(button);

                add(item.getImage()).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                        .padRight(getWidthPercent(.1f)).padBottom(getHeightPercent(.001f));
                add(button).width(getWidthPercent(.2f)).height(getWidthPercent(.2f))
                        .padBottom(getHeightPercent(.001f)).row();
            }
        }
    }

    private TextButton createTextButton(String itemName){
        TextButton button = createBuyButton(String.valueOf(UPGRADE_OBJECT_COST), itemName);

        Image image = new Image(buttonIcon);
        float imageSize = getWidthPercent(.06f);

        button.getLabelCell().width(imageSize).height(imageSize)
                .fill(false).expand(false, false);
        button.add(image).width(imageSize).height(imageSize);

        return button;
    }

    private void addClickListener(final TextButton button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSound("buying_cover");

                int design = stats.getDesign();
                int cost = UPGRADE_OBJECT_COST;

                if(design >= cost){
                    stats.setDesign(design - cost);

                    for (int i = 0; i < items.size; i++) {
                        CoverListItem item = items.get(i);
                        String itemText = item.getText();

                        if(itemText.equals(button.getName())){
                            game.setCoverObjectPurchased(itemText);

                            removeActor(item, button);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void removeActor(CoverListItem actor, TextButton button) {
        items.removeValue(actor, true);
        buttons.removeValue(button, true);
        clear();
        addItems();
    }

    private void playSound(String sound){
        Assets.getInstance().setSound(sound);
    }
}
