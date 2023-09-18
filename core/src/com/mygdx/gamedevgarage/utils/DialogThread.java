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
import com.mygdx.gamedevgarage.screens.MainScreen;
import com.mygdx.gamedevgarage.utils.constraints.Constants;
import com.mygdx.gamedevgarage.utils.constraints.GameState;
import com.mygdx.gamedevgarage.utils.data.GameFactory;

public class DialogThread {

    private final Timer currentTimer = new Timer();

    private static DialogThread instance;

    public static DialogThread getInstance(){
        if(instance == null){
            instance = new DialogThread();
        }
        return instance;
    }

    public void start(){
        mainThread.run();
    }

    public void restart(){
        currentTimer.clear();
    }

    private final Task mainThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();
            game.getMainScreen().setGameStarted();
            game.gameState = GameState.MAIN;
            openGameMakeDialog();
            System.out.println("mainThread started");
        }
    };

    private final Task designThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();
            game.gameState = GameState.DESIGN;
            game.setCoverScreen();
            System.out.println("designThread started");
        }
    };

    private final Task programmingThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();
            game.gameState = GameState.PROGRAMMING;
            game.setTechScreen();
            System.out.println("programmingThread started");
        }
    };

    private final Task gameDesignThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();
            game.gameState = GameState.GAMEDESIGN;
            game.setMechanicScreen();
            System.out.println("gameDesignThread started");
        }
    };

    private final Task platformThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();
            game.gameState = GameState.PLATFORM;
            game.setPlatformScreen();
            System.out.println("platformThread started");
        }
    };

    private final Task endGameThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();
            game.gameState = GameState.END;
            game.setEndScreen();
            System.out.println("EndGameThread started");
        }
    };

    private void openGameMakeDialog() {
        final Game game = Game.getInstance();
        final MainScreen screen = game.getMainScreen();

        mainThread.cancel();
        screen.hide();

        final Dialog dialog = DialogFactory.createMakeGameDialog();

        TextButton okButton = dialog.getButtonTable().findActor("okButton");
        TextButton cancelButton = dialog.getButtonTable().findActor("cancelButton");

        final TextField nameTextField = dialog.getContentTable().findActor("nameTextField");
        final SelectBox<String> genreSelectBox = dialog.getContentTable().findActor("genreSelectBox");
        final SelectBox<String> themesSelectBox = dialog.getContentTable().findActor("themesSelectBox");
        final SelectBox<String> levelSelectBox = dialog.getContentTable().findActor("levelSelectBox");

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.setDialogOpened(false);
                dialog.hide();
                getInstance().setDesignThread();
                GameFactory.genre = genreSelectBox.getSelected();
                GameFactory.theme = themesSelectBox.getSelected();
                GameFactory.name = nameTextField.getText();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                screen.setDialogOpened(false);
                screen.setGameCanceled();
            }
        });

        screen.setDialogOpened(true);
        dialog.show(screen.getStage());
    }

    public void setDesignThread() {
        currentTimer.scheduleTask(designThread, Constants.MAIN_TIME);
    }

    public void setProgrammingThread() {
        currentTimer.scheduleTask(programmingThread, Constants.DESIGN_TIME);
    }

    public void setGameDesignThread() {
        currentTimer.scheduleTask(gameDesignThread, Constants.PROGRAMING_TIME);
    }

    public void setEndGameThread() {
        currentTimer.scheduleTask(endGameThread, Constants.MAIN_TIME);
    }

    public void setPlatformThread() {
        currentTimer.scheduleTask(platformThread, Constants.GAME_DESIGN_TIME);
    }

    public void pause() {
        currentTimer.stop();
    }

    public void resume() {
        currentTimer.start();
    }
}