package com.mygdx.gamedevgarage.utils;

import static com.badlogic.gdx.utils.Timer.Task;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.gamedevgarage.Assets;
import com.mygdx.gamedevgarage.Game;
import com.mygdx.gamedevgarage.MainScreen;

public class DialogThread {

    private static Game game;
    private static Assets assets;
    private static Stage stage;

    private static MainScreen mainScreen;
    private static long designTime;
    private static long programmingTime;
    private static long gameDesignTime;

    public DialogThread(Game game, Stage stage, long mainTime, double designPercent,
                        double programmingPercent, double gameDesignPercent) {
        DialogThread.game = game;
        DialogThread.stage = stage;
        DialogThread.assets = game.getAssets();
        DialogThread.mainScreen = game.getMainScreen();
        designTime = Math.round(mainTime * designPercent);
        programmingTime = Math.round(mainTime * programmingPercent);
        gameDesignTime = Math.round(mainTime * gameDesignPercent);
    }

    public void start(){
        mainThread.run();
    }

    private static Task mainThread = new Task() {
        @Override
        public void run() {
            System.out.println("mainThread started");
            mainScreen.setGameStarted();
            openGameMakeDialog();
        }
    };

    private static Task designThread = new Task() {
        @Override
        public void run() {
            game.setCoverScreen();
            System.out.println("designThread started");
        }
    };

    private static Task programmingThread = new Task() {
        @Override
        public void run() {
            game.setTechScreen();
            System.out.println("programmingThread started");
        }
    };

    private static Task gameDesignThread = new Task() {
        @Override
        public void run() {
            game.setMechanicScreen();

            System.out.println("gameDesignThread started");
        }
    };

    private static Task platformThread = new Task() {
        @Override
        public void run() {
            game.setPlatformScreen();

            System.out.println("platformThread started");
        }
    };

    private static Task endGameThread = new Task() {
        @Override
        public void run() {
            game.setEndScreen();

            System.out.println("EndGameThread started");
            mainScreen.setGameEnded();
            endGameThread.cancel();
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
                new Timer().scheduleTask(designThread, 1);
                game.reward.genre = genreSelectBox.getSelected();
                game.reward.theme = themesSelectBox.getSelected();
                game.reward.name = nameTextField.getText();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                mainScreen.setGameCanceled();
            }
        });

        dialog.show(stage);
    }

    public static long getDesignTime() {
        return designTime;
    }

    public static long getProgrammingTime() {
        return programmingTime;
    }

    public static long getGameDesignTime() {
        return gameDesignTime;
    }

    public static Task getDesignThread() {
        return designThread;
    }

    public static Task getProgrammingThread() {
        return programmingThread;
    }

    public static Task getGameDesignThread() {
        return gameDesignThread;
    }

    public static Task getEndGameThread() {
        return endGameThread;
    }

    public static Task getPlatformThread() {
        return platformThread;
    }
}