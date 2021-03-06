package es.urjccode.mastercloudapps.adcs.draughts.models;

import java.util.ArrayList;
import java.util.List;

import es.urjccode.mastercloudapps.adcs.draughts.utils.Message;

class Board {

    private static final int DIMENSION = 8;

    private Square[][] squares;

    Board() {
        this.squares = new Square[this.getDimension()][this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            for (int j = 0; j < this.getDimension(); j++) {
                this.squares[i][j] = new Square();
            }
        }
    }

    private Square getSquare(Coordinate coordinate) {
        assert coordinate != null && coordinate.isValid();
        return this.squares[coordinate.getRow()][coordinate.getColumn()];
    }

    void put(Coordinate coordinate, Piece piece) {
        assert piece != null;
        this.getSquare(coordinate).put(piece);
    }

    Piece remove(Coordinate coordinate) {
        assert this.getPiece(coordinate) != null;
        return this.getSquare(coordinate).remove();
    }

    void move(Coordinate origin, Coordinate target) {
        this.put(target, this.remove(origin));
    }

    Piece getPiece(Coordinate coordinate) {
        return this.getSquare(coordinate).getPiece();
    }

    boolean isEmpty(Coordinate coordinate) {
        return this.getSquare(coordinate).isEmpty();
    }

    Color getColor(Coordinate coordinate) {
        return this.getSquare(coordinate).getColor();
    }

    List<Piece> getPieces(Color color) {
        List<Piece> pieces = new ArrayList<Piece>();
        for (int i = 0; i < this.getDimension(); i++) {
            for (int j = 0; j < this.getDimension(); j++) {
                pieces.add(this.squares[i][j].getPiece());
            }
        }
		return pieces;
	}
    
    int getDimension() {
		return Board.DIMENSION;
	}

    @Override
    public String toString() {
        String string = "";
        string += this.toStringHorizontalNumbers();
        for (int i = 0; i < this.getDimension(); i++) {
            string += this.toStringHorizontalPiecesWithNumbers(i);
        }
        string += this.toStringHorizontalNumbers();
        return string;
    }

    private String toStringHorizontalNumbers() {
        String string = Message.SPACE;
        for (int j = 0; j < this.getDimension(); j++) {
            string += j;
        }
        return string + Message.LINE_BREAK;
    }

    private String toStringHorizontalPiecesWithNumbers(int row) {
        String string = "" + row;
        for (int j = 0; j < this.getDimension(); j++) {
            string += this.obtainPiecesLettersFrom(row, j);
        }
        return string + row + Message.LINE_BREAK;
    }

    private String obtainPiecesLettersFrom(int row, int column) {
        Piece piece = this.getPiece(new Coordinate(row, column));
        if (piece == null) {
            return Message.SPACE;
        } else {
            final String[] letters = { Message.WHITE_PIECE, Message.BLACK_PIECE };
            return letters[piece.getColor().ordinal()];
        }
    }

}