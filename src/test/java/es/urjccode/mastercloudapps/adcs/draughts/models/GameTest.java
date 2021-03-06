package es.urjccode.mastercloudapps.adcs.draughts.models;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class GameTest {

    @Spy
    private BoardWithPieces board;

    @Spy
    private Turn turn;

    @InjectMocks
    private Game gameMock;

    private Game game;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public GameTest() {
        game = new Game();
    }

    @Test
    public void testGivenNewBoardThenGoodLocations() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < game.getDimension(); j++) {
                this.checkPieceLocation(Color.BLACK, i, j);
            }
        }
        for (int i = 5; i < game.getDimension(); i++) {
            for (int j = 0; j < game.getDimension(); j++) {
                this.checkPieceLocation(Color.WHITE, i, j);
            }
        }
    }

    private void checkPieceLocation(Color colorToCheck, int row, int column) {
        Coordinate coordinate = new Coordinate(row, column);
        Color color = game.getColor(coordinate);
        if (coordinate.isBlack()) {
            assertEquals(colorToCheck, color);
        } else {
            assertNull(color);
        }
    }

    private Error advance(Coordinate[][] coordinates){
        Error error = null;
        for (int i = 0; i < coordinates.length; i++) {
            assertNull(error);
            System.out.println(game);
            error = game.move(coordinates[i][0], coordinates[i][1]);
        }
        return error;
    }

    @Test
    public void testGivenGameWhenConstructThenInitialDistribution(){
        assertEquals(Color.WHITE, game.getColor(new Coordinate(5,0)));
        assertEquals(Color.BLACK, game.getColor(new Coordinate(2,1)));
    }

    @Test()
    public void testGivenGameWhenMoveWithOuterCoordinateThenOutCoordinateError() {
        assertEquals(Error.OUT_COORDINATE, this.advance(new Coordinate[][] { 
            { new Coordinate(4, 7), new Coordinate(3, 8) }, 
        }));
    }

    @Test
    public void testGivenGameWhenMoveEmptySquaerThenEmptySquareError() {
        assertEquals(Error.EMPTY_ORIGIN, this.advance(new Coordinate[][] { 
            {  new Coordinate(4, 3), new Coordinate(3, 4), }, 
        }));
    }

    @Test
    public void testGivenGameWhenMoveOppositePieceThenError() {
        assertEquals(Error.OPPOSITE_PIECE, this.advance(new Coordinate[][] { 
            { new Coordinate(5, 6), new Coordinate(4, 7) },
            { new Coordinate(2, 7), new Coordinate(3, 6) }, 
            { new Coordinate(3, 6), new Coordinate(2, 7) }, 
        }));
    }

    @Test
    public void testGivenGameWhenNotDiagonalMovementThenError() {
        assertEquals(Error.NOT_DIAGONAL, this.advance(new Coordinate[][] { 
            { new Coordinate(5, 2), new Coordinate(4, 2) },
        }));
    }

    @Test
    public void testGivenGameWhenMoveWithNotAdvancedThenError() {
        Coordinate origin = new Coordinate(3, 4);
        when(board.getColor(origin)).thenReturn(Color.WHITE);
        when(board.isEmpty(origin)).thenReturn(false);
        when(board.getPiece(origin)).thenReturn(new Piece(Color.WHITE));
        assertEquals(Error.NOT_ADVANCED, gameMock.move(origin, new Coordinate(4, 5)));     
    }

    @Test
    public void testGivenGameWhenNotEmptyTargeThenError() {
        Coordinate target = new Coordinate(4, 5);
        when(board.getPiece(target)).thenReturn(new Piece(Color.BLACK));
        when(board.isEmpty(target)).thenReturn(false);
        assertEquals(Error.NOT_EMPTY_TARGET, gameMock.move(new Coordinate(5, 4), target));
    }

    @Test
    public void testGivenGameWhenCorrectMovementThenOk() {
        Coordinate origin = new Coordinate(5, 0);
        Coordinate target = new Coordinate(4, 1);
        this.game.move(origin, target);
        assertNull(this.game.getColor(origin));
        assertEquals(Color.WHITE, this.game.getColor(target));
        origin = new Coordinate(2, 3);
        target = new Coordinate(3, 4);
        this.game.move(origin, target);
        assertNull(this.game.getColor(origin));
        assertEquals(Color.BLACK, this.game.getColor(target));
    }

    @Test
    public void testGivenGameWhenMovementThenEatPiece() {
        Coordinate origin = new Coordinate(5, 0);
        Coordinate target = new Coordinate(3, 2);
        Coordinate originEatPiece = new Coordinate(4, 1);
        when(board.getPiece(originEatPiece)).thenReturn(new Piece(Color.BLACK));
        Error error = gameMock.move(origin, target);
        assertNull(error);
        assertNull(gameMock.getColor(origin));
        verify(board).remove(originEatPiece);
        assertEquals(Color.WHITE, gameMock.getColor(target));
    }

    @Test
    public void testGivenGameWhenEatEmptyPieceThenError() {
        assertEquals(Error.EATING_EMPTY, this.advance(new Coordinate[][] { 
            { new Coordinate(5, 4), new Coordinate(3, 2) },
        })); 
    }

    @Test
    public void testGivenGameWhenMoveBadDistanceThenError() {
        assertEquals(Error.BAD_DISTANCE, this.advance(new Coordinate[][] { 
            { new Coordinate(5, 0), new Coordinate(2, 3) },
        })); 
    }

}