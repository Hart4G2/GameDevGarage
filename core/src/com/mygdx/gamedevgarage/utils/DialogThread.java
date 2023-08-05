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
    private static Stage stage;

    private static MainScreen mainScreen;
    private static Assets assets;
    private static long designTime;
    private static long programmingTime;
    private static long gameDesignTime;

    public DialogThread(Game game, Stage stage, Assets assets, long mainTime, double designPercent, double programmingPercent, double gameDesignPercent) {
        DialogThread.game = game;
        DialogThread.stage = stage;
        DialogThread.assets = assets;
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
            openDesignDialog();
            System.out.println("designThread started");
        }
    };

    private static Task programmingThread = new Task() {
        @Override
        public void run() {
            openProgrammingDialog();
            System.out.println("programmingThread started");
        }
    };

    private static Task gameDesignThread = new Task() {
        @Override
        public void run() {
            openGameDesignDialog();
            System.out.println("gameDesignThread started");
        }
    };

    private static Task endGameThread = new Task() {
        @Override
        public void run() {
            System.out.println("EndGameThread started");
            mainScreen.setGameEnded();
            endGameThread.cancel();
        }
    };

    private static void openGameMakeDialog() {
        mainThread.cancel();
        game.getMainScreen().hide();

        final Dialog dialog = DialogFactory.createMakeGameDialog(assets.getSkin());

        TextButton okButton = dialog.getButtonTable().findActor("okButton");
        TextButton cancelButton = dialog.getButtonTable().findActor("cancelButton");

        final TextField nameTextField = dialog.getContentTable().findActor("nameTextField");
        final SelectBox<String> genreSelectBox = dialog.getContentTable().findActor("genreSelectBox");
        final SelectBox<String> platformSelectBox = dialog.getContentTable().findActor("platformSelectBox");
        final SelectBox<String> levelSelectBox = dialog.getContentTable().findActor("levelSelectBox");

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                new Timer().scheduleTask(designThread, 0);
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.show(stage);
    }

    private static void openDesignDialog() {
        game.setCoverScreen();
    }

    private static void openProgrammingDialog() {
        game.setTechScreen();
    }

    private static void openGameDesignDialog() {
        game.setMechanicScreen();
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
}