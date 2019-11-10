package es.urjccode.mastercloudapps.adcs.draughts.views;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.Controller;
import es.urjccode.mastercloudapps.adcs.draughts.models.Color;
import es.urjccode.mastercloudapps.adcs.draughts.models.Coordinate;
import es.urjccode.mastercloudapps.adcs.draughts.utils.Message;

public class GameView extends SubView {

    private static final String[] COLORS = new String[] { Message.WHITE_PIECE, Message.BLACK_PIECE, Message.SPACE };

    public void write(Controller controller) {
        final int DIMENSION = controller.getDimension();
        this.writeNumbersLine(DIMENSION);
        for (int i = 0; i < DIMENSION; i++) {
            this.console.write((i + 1) + "");
            for (int j = 0; j < DIMENSION; j++) {
                writePieceColor(controller, i, j);
            }
            this.console.writeln((i + 1) + "");
        }
        this.writeNumbersLine(DIMENSION);
    }

    private void writePieceColor(Controller controller, int rowPosition, int columnPosition) {
        Color color = controller.getColor(new Coordinate(rowPosition, columnPosition));
        if (color == null) {
            this.console.write(GameView.COLORS[2]);
        } else {
            this.console.write(GameView.COLORS[color.ordinal()]);
        }
    }

    private void writeNumbersLine(final int DIMENSION) {
        this.console.write(Message.SPACE);
        for (int i = 0; i < DIMENSION; i++) {
            this.console.write((i + 1) + "");
        }
        this.console.writeln();
    }

}
