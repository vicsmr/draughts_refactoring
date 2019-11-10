package es.urjccode.mastercloudapps.adcs.draughts.views;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.StartController;
import es.urjccode.mastercloudapps.adcs.draughts.utils.Message;

public class StartView extends SubView {

    public StartView() {
        super();
    }

    public void interact(StartController startController) {
        this.console.writeln(Message.GAME_TITLE);
        new GameView().write(startController);
        startController.start();
    }
}
