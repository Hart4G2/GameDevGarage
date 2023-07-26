package com.mygdx.gamedevgarage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gamedevgarage.mini_games.CoverCreationScreen;
import com.mygdx.gamedevgarage.utils.DialogFactory;

import java.util.Timer;
import java.util.TimerTask;

public class DialogThread {

    long mainTime;
    double designPercent;
    double programmingPercent;
    double gameDesignPercent;
    long designTime;
    long programmingTime;
    long gameDesignTime;

    Game game;
    Stage stage;
    Assets assets;

    public DialogThread(Game game, Stage stage, Assets assets, long mainTime, double designPercent, double programmingPercent, double gameDesignPercent) {
        this.mainTime = mainTime;
        this.designPercent = designPercent;
        this.programmingPercent = programmingPercent;
        this.gameDesignPercent = gameDesignPercent;
        this.game = game;
        this.stage = stage;
        this.assets = assets;
        designTime = Math.round(mainTime * designPercent);
        programmingTime = Math.round(mainTime * programmingPercent);
        gameDesignTime = Math.round(mainTime * gameDesignPercent);
    }

    public void start(){
        mainThread.run();
    }

    TimerTask mainThread = new TimerTask() {
        @Override
        public void run() {
//            System.out.println("mainThread started");
//            openGameMakeDialog();
            openDesignDialog();
        }

        @Override
        public boolean cancel() {
            System.out.println("mainThread canceled");
            return super.cancel();
        }
    };

    TimerTask designThread = new TimerTask() {
        @Override
        public void run() {
            openDesignDialog();
            System.out.println("designThread started");
        }

        @Override
        public boolean cancel() {
            System.out.println("designThread canceled");
            return super.cancel();
        }
    };

    TimerTask programmingThread = new TimerTask() {
        @Override
        public void run() {
            openProgrammingDialog();
            System.out.println("programmingThread started");
        }

        @Override
        public boolean cancel() {
            System.out.println("programmingThread canceled");
            return super.cancel();
        }
    };

    TimerTask gameDesignThread = new TimerTask() {
        @Override
        public void run() {
            openGameDesignDialog();
            System.out.println("gameDesignThread started");
        }

        @Override
        public boolean cancel() {
            System.out.println("gameDesignThread canceled");
            return super.cancel();
        }
    };

    TimerTask endGameThread = new TimerTask() {
        @Override
        public void run() {
            isGameInProgress = false;
            System.out.println("EndGameThread started");
            endGameThread.cancel();
        }

        @Override
        public boolean cancel() {
            System.out.println("EndGameThread canceled");
            return super.cancel();
        }
    };

    public static boolean isGameInProgress = false;

    private void openGameMakeDialog() {
        mainThread.cancel();

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
                new Timer().schedule(designThread, 0L);
                isGameInProgress = true;
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
                isGameInProgress = false;
            }
        });

        dialog.show(stage);
    }

    private void openDesignDialog() {
        game.setScreen(new CoverCreationScreen(game));

//        final Dialog dialog = DialogFactory.createDesignDialog(assets.skin);
//        isGameInProgress = false;
//
//        TextButton okButton = dialog.getButtonTable().findActor("okButton");
//
//        final SelectBox<String> graphicSelectBox = dialog.getContentTable().findActor("graphicSelectBox");
//
//        okButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                String graphic = graphicSelectBox.getSelected();
//
//                dialog.hide();
//                isGameInProgress = true;
//                designThread.cancel();
//                new Timer().schedule(programmingThread, designTime);
//            }
//        });
//
//        dialog.show(stage);

    }

    private void openProgrammingDialog() {
        final Dialog dialog = DialogFactory.createProgrammingDialog(assets.getSkin());
        isGameInProgress = false;

        TextButton okButton = dialog.getButtonTable().findActor("okButton");

        final SelectBox<String> technicSelectBox = dialog.getContentTable().findActor("technicSelectBox");

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String technic = technicSelectBox.getSelected();

                dialog.hide();
                isGameInProgress = true;
                programmingThread.cancel();
                new Timer().schedule(gameDesignThread, programmingTime);
            }
        });

        dialog.show(stage);
    }

    private void openGameDesignDialog() {
        final Dialog dialog = DialogFactory.createGameDesignDialog(assets.getSkin());
        isGameInProgress = false;

        TextButton okButton = dialog.getButtonTable().findActor("okButton");

        final SelectBox<String> mechanicSelectBox = dialog.getContentTable().findActor("mechanicSelectBox");

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String mechanic = mechanicSelectBox.getSelected();

                dialog.hide();
                isGameInProgress = true;
                gameDesignThread.cancel();
                new Timer().schedule(endGameThread, gameDesignTime);
            }
        });

        dialog.show(stage);
    }
}