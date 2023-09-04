package com.mygdx.gamedevgarage.upgrade.ui;

import static com.mygdx.gamedevgarage.utils.constraints.Constants.UPGRADE_MECHANIC_COST;
import static com.mygdx.gamedevgarage.utils.constraints.Constants.UPGRADE_TECHNOLOGY_COST;
import static com.mygdx.gamedevgarage.utils.Utils.createBuyButton;
import static com.mygdx.gamedevgarage.utils.Utils.getHeightPercent;
import static com.mygdx.gamedevgarage.utils.Utils.getWidthPercent;
import static com.mygdx.gamedevgarage.utils.data.DataArrayFactory.createMechanics;
import static com.mygdx.gamedevgarage.utils.data.DataArrayFactory.createTechnologies;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.mini_games.selection_actors.CheckListItem;

public class UpgradeCheckItemsList extends Table {

    private final Game game;
    private final Skin skin;

    private final boolean isTechnology;
    private final String buttonText;
    private final Drawable buttonIcon;

    private final Array<CheckListItem> items;
    private final Array<TextButton> buttons;

    public UpgradeCheckItemsList(Game game, boolean isTechnology) {
        super(game.getAssets().getSkin());
        skin = getSkin();
        this.game = game;
        this.isTechnology = isTechnology;
        this.items = isTechnology ? createTechnologies(game) : createMechanics(game);
        this.buttonText = String.valueOf(isTechnology ? UPGRADE_TECHNOLOGY_COST : UPGRADE_MECHANIC_COST);
        this.buttonIcon = isTechnology ? getSkin().getDrawable("programing") : getSkin().getDrawable("game_design");

        buttons = new Array<>();
        addItems();
    }

    private void addItems() {
        for (CheckListItem item : items) {
            if(!item.isPurchased()) {
                TextButton button = createTextButton(item.getText());

                buttons.add(button);
                addClickListener(button);

                add(item).width(getWidthPercent(.7f)).height(getHeightPercent(.15f))
                        .padRight(getWidthPercent(.001f));
                add(button).width(getWidthPercent(.21f)).height(getWidthPercent(.2f))
                        .row();
            }
        }
    }

    private TextButton createTextButton(String text){
        TextButton button = createBuyButton(buttonText, skin, text);

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
                buttonClicked(button);
            }
        });
    }

    private void buttonClicked(final TextButton button) {
        int property = isTechnology ? game.stats.getProgramming() : game.stats.getGameDesign();
        int cost = isTechnology ? UPGRADE_TECHNOLOGY_COST : UPGRADE_MECHANIC_COST;

        if (property >= cost) {
            for (int i = 0; i < items.size; i++) {
                CheckListItem item = items.get(i);
                String itemText = item.getText();

                if (itemText.equals(button.getName())) {
                    if (isTechnology) {
                        game.stats.setProgramming(property - cost);
                        game.setTechnologyPurchased(itemText);
                    } else {
                        game.stats.setGameDesign(property - cost);
                        game.setMechanicPurchased(itemText);
                    }

                    removeActor(item, button);
                    break;
                }
            }
        }
    }

    private void removeActor(CheckListItem actor, TextButton button) {
        items.removeValue(actor, true);
        buttons.removeValue(button, true);
        clear();
        addItems();
    }

    public void render(float delta){
        for(CheckListItem item : items){
            item.render(delta);
        }
    }
}
