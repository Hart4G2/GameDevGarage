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

    private static final Timer currentTimer = new Timer();

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
            game.setCoverScreen();
            game.gameState = GameState.DESIGN;
            System.out.println("designThread started");
        }
    };

    private final Task programmingThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();

            game.setTechScreen();
            game.gameState = GameState.PROGRAMMING;
            System.out.println("programmingThread started");
        }
    };

    private final Task gameDesignThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();

            game.setMechanicScreen();
            game.gameState = GameState.GAMEDESIGN;
            System.out.println("gameDesignThread started");
        }
    };

    private final Task platformThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();

            game.setPlatformScreen();
            game.gameState = GameState.PLATFORM;
            System.out.println("platformThread started");
        }
    };

    private final Task endGameThread = new Task() {
        @Override
        public void run() {
            Game game = Game.getInstance();

            game.setEndScreen();
            game.gameState = GameState.END;
            System.out.println("EndGameThread started");
        }
    };

    private void openGameMakeDialog() {
        final Game game = Game.getInstance();

        mainThread.cancel();
        game.getMainScreen().hide();

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
                dialog.hide();
                DialogThread.getInstance().setDesignThread();
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