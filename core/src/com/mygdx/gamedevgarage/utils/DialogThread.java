package com.mygdx.gamedevgarage.utils;

import static com.badlogic.gdx.utils.Timer.Task;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.utils.constraints.Constants;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.GameFactory;

public class DialogThread {

    private static Game game;

    public DialogThread(Game game) {
        DialogThread.game = game;
    }

    public void start(){
        mainThread.run();
    }

    private static final Task mainThread = new Task() {
        @Override
        public void run() {
            game.getMainScreen().setGameStarted();
            game.gameState = GameState.MAIN;
            openGameMakeDialog();
            System.out.println("mainThread started");
        }
    };

    private static final Task designThread = new Task() {
        @Override
        public void run() {
            game.setCoverScreen();
            game.gameState = GameState.DESIGN;
            System.out.println("designThread started");
        }
    };

    private static final Task programmingThread = new Task() {
        @Override
        public void run() {
            game.setTechScreen();
            game.gameState = GameState.PROGRAMMING;
            System.out.println("programmingThread started");
        }
    };

    private static final Task gameDesignThread = new Task() {
        @Override
        public void run() {
            game.setMechanicScreen();
            game.gameState = GameState.GAMEDESIGN;
            System.out.println("gameDesignThread started");
        }
    };

    private static final Task platformThread = new Task() {
        @Override
        public void run() {
            game.setPlatformScreen();
            game.gameState = GameState.PLATFORM;
            System.out.println("platformThread started");
        }
    };

    private static final Task endGameThread = new Task() {
        @Override
        public void run() {
            game.setEndScreen();
            game.gameState = GameState.END;
            System.out.println("EndGameThread started");
        }
    };

    private static void openGameMakeDialog() {
        mainThread.cancel();
        game.getMainScreen().hide();

        final Dialog dialog = DialogFactory.createMakeGameDialog(game);

        TextButton okButton = dialog.getButtonTable().findActor("okButton");
        TextButton cancelButton = dialog.getButtonTable().findActor("cancelButton");

        final TextField nameTextField = dialog.getContentTable().findActor("nameTextField");
        final SelectBox<String> genreSelectBox = dialog.getContentTable().findActor("genreSelectBox");
        final SelectBox<String> themesSelectBox = dialog.getContentTable().findActor("themesSelectBox");
        final SelectBox<String> levelSelectBox = dialog.getContentTable().findActor("levelSelectBox");

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                DialogThread.setDesignThread();
                GameFactory.genre = genreSelectBox.getSelected();
                GameFactory.theme = themesSelectBox.getSelected();
                GameFactory.name = nameTextField.getText();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                game.getMainScreen().setGameCanceled();
            }
        });

        dialog.show(game.getMainScreen().getStage());
    }

    public static void setDesignThread() {
        new Timer().scheduleTask(designThread, Constants.MAIN_TIME);
    }

    public static void setProgrammingThread() {
        new Timer().scheduleTask(programmingThread, Constants.DESIGN_TIME);
    }

    public static void setGameDesignThread() {
        new Timer().scheduleTask(gameDesignThread, Constants.PROGRAMING_TIME);
    }

    public static void setEndGameThread() {
        new Timer().scheduleTask(endGameThread, Constants.MAIN_TIME);
    }

    public static void setPlatformThread() {
        new Timer().scheduleTask(platformThread, Constants.GAME_DESIGN_TIME);
    }
}