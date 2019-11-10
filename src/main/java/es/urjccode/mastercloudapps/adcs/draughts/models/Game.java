package es.urjccode.mastercloudapps.adcs.draughts.models;

public class Game {

	private Board board;

	private Turn turn;

	public Game() {
		this.turn = new Turn();
		this.board = new Board();
		for (int i = 0; i < this.board.getDimension(); i++) {
			for (int j = 0; j < this.board.getDimension(); j++) {
				Coordinate coordinate = new Coordinate(i, j);
				Piece piece = this.getInitialPiece(coordinate);
				if (piece != null) {
					this.board.put(coordinate, piece);
				}
			}
		}
	}

	private Piece getInitialPiece(Coordinate coordinate) {
		if (coordinate.isBlack()) {
			final int row = coordinate.getRow();
			Color color = null;
			if (row <= 2) {
				color = Color.BLACK;
			} else if (row >= 5) {
				color = Color.WHITE;
			}
			if (color != null) {
				return new Piece(color);
			}
		}
		return null;
	}

	public Error move(Coordinate origin, Coordinate target) {
		assert origin != null && target != null;
		ValidatorErrorMove validatorErrorMove = new ValidatorErrorMove(this.board, this.turn, origin, target);
		Error error = validatorErrorMove.checkError();
		error = error == null ? checkDiagonalError(origin, target) : error;
		if (error != null) {
			return error;
		}
		this.board.move(origin, target);
		this.turn.change();
		return null;
	}

	private Error checkDiagonalError(Coordinate origin, Coordinate target) {
		if (origin.diagonalDistance(target) == 2) {
			Coordinate between = origin.betweenDiagonal(target);
			if (board.getPiece(between) == null) {
				return Error.EATING_EMPTY;
			}
			board.remove(between);
		}
		return null;
	}

	public Color getColor(Coordinate coordinate) {
		return this.board.getColor(coordinate);
	}

	@Override
	public String toString() {
		return this.board + "\n" + this.turn;
	}

	public Color getColor() {
		return this.turn.getColor();
	}

	public Piece getPiece(Coordinate coordinate) {
		return this.board.getPiece(coordinate);
	}

	public boolean isBlocked() {
		return this.board.getPieces(this.turn.getColor()).isEmpty();
	}

	public int getDimension() {
		return this.board.getDimension();
	}

}