package es.urjccode.mastercloudapps.adcs.draughts.views;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.PlayController;
import es.urjccode.mastercloudapps.adcs.draughts.models.Error;
import es.urjccode.mastercloudapps.adcs.draughts.utils.Message;
import es.urjccode.mastercloudapps.adcs.draughts.models.Coordinate;

public class CommandView extends SubView {

    private static final String[] COLORS = { Message.WHITES, Message.BLACKS };

    public CommandView() {
        super();
    }

    private int obtainCoordinateFromString(String command, int init, int end) {
        return Integer.parseInt(command.substring(init, end));
    }

    public void interact(PlayController playController) {
        String color = CommandView.COLORS[playController.getColor().ordinal()];
        Error error = null;
        GameView gameView = new GameView();
        do {
            error = commandMove(playController, color, gameView);
        } while (error != null);
        if (playController.isBlocked())
            this.console.write(Message.LOSE_GAME);
    }

    private Error commandMove(PlayController playController, String color, GameView gameView) {
        String command = this.console.readString(Message.MOVE + color + Message.TWO_DOTS);
        int origin = this.obtainCoordinateFromString(command, 0, 2);
        int target = this.obtainCoordinateFromString(command, 3, 5);
        Error error = playController.move(new Coordinate(origin / 10 - 1, origin % 10 - 1),
                new Coordinate(target / 10 - 1, target % 10 - 1));
        if (error != null) {
            this.console.writeln(Message.ERROR + error.name());
            gameView.write(playController);
        }
        return error;
    }

}