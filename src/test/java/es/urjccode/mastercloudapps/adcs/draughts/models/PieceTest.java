package es.urjccode.mastercloudapps.adcs.draughts.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PieceTest {

    Coordinate coordinateRowFiveColumnZero;
    Coordinate coordinateRowTwoColumnOne;
    Piece whitePiece;
    Piece blackPiece;

    public PieceTest() {
        this.coordinateRowFiveColumnZero = new Coordinate(5, 0);
        this.coordinateRowTwoColumnOne = new Coordinate(2, 1);
        this.whitePiece = new Piece(Color.WHITE);
        this.blackPiece = new Piece(Color.BLACK);
    }

    @Test
    public void testGivenPieceWhenIsAdvancedThenTrue() {
        assertTrue(whitePiece.isAdvanced(this.coordinateRowFiveColumnZero, new Coordinate(4, 1)));
        assertTrue(blackPiece.isAdvanced(this.coordinateRowTwoColumnOne, new Coordinate(3, 2)));
    }

    @Test
    public void testGivenPieceWhenNotIsAdvancedThenFalse() {
        assertFalse(whitePiece.isAdvanced(this.coordinateRowFiveColumnZero, new Coordinate(6, 1)));
        assertFalse(whitePiece.isAdvanced(this.coordinateRowFiveColumnZero, new Coordinate(5, 2)));
        assertFalse(blackPiece.isAdvanced(this.coordinateRowTwoColumnOne, new Coordinate(2, 3)));
        assertFalse(blackPiece.isAdvanced(this.coordinateRowTwoColumnOne, new Coordinate(1, 2)));
    }
}