package client.threads;

import client.gui.StartGameController;
import shared.logic.GeneralTask;

public class GameHelper extends GeneralTask {
    private StartGameController graphics;

    public GameHelper() {

    }

    public void setGraphics(StartGameController graphics) {
        this.graphics = graphics;
    }

    @Override
    public void run() {
        super.run();
    }
}