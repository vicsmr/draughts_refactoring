package es.urjccode.mastercloudapps.adcs.draughts.models;

public class BoardWithPieces extends Board {

    BoardWithPieces() {
        super();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < this.getDimension(); j++) {
                if (new Coordinate(i, j).isBlack()) {
                    this.put(new Coordinate(i, j), new Piece(Color.BLACK));
                }
            }
        }
        for (int i = 5; i < this.getDimension(); i++) {
            for (int j = 0; j < this.getDimension(); j++) {
                if (new Coordinate(i, j).isBlack()) {
                    this.put(new Coordinate(i, j), new Piece(Color.WHITE));
                }
            }
        }
    }
}