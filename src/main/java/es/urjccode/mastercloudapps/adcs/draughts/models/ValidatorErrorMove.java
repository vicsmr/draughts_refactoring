package es.urjccode.mastercloudapps.adcs.draughts.models;

public class ValidatorErrorMove {

	private Board board;
	private Turn turn;
	private Coordinate origin;
	private Coordinate target;
    
    ValidatorErrorMove(Board board, Turn turn, Coordinate origin, Coordinate target) {
		this.board = board;
		this.turn = turn;
		this.origin = origin;
		this.target = target;
    }

    Error checkError() {
        return this.checkAreValidCoordinates();
	}

	private Error checkAreValidCoordinates() {
		if (!this.origin.isValid() || !this.target.isValid()) {
			return Error.OUT_COORDINATE;
		}
		return this.checkIsEmptyOrigin();
	}

	private Error checkIsEmptyOrigin() {
		if (this.board.isEmpty(this.origin)) {
			return Error.EMPTY_ORIGIN;
		}
		return this.checkIsValidTurn();
	}

	private Error checkIsValidTurn() {
		Color color = this.board.getColor(this.origin);
		if (this.turn.getColor() != color) {
			return Error.OPPOSITE_PIECE;
		}
		return this.checkIsDiagonalMove();
	}

	private Error checkIsDiagonalMove() {
		if (!this.origin.isDiagonal(this.target)) {
			return Error.NOT_DIAGONAL;
		}
		return this.checkIsAdvancedMove();
	}

	private Error checkIsAdvancedMove() {
		Piece piece = this.board.getPiece(this.origin);
		if (!piece.isAdvanced(this.origin, this.target)) {
			return Error.NOT_ADVANCED;
		}
		return this.checkDiagonalDistance();
	}

	private Error checkDiagonalDistance() {
		if (this.origin.diagonalDistance(this.target) >= 3) {
			return Error.BAD_DISTANCE;
		}
		return this.checkIsEmptyTarget();
	}

	private Error checkIsEmptyTarget() {
		if (!this.board.isEmpty(this.target)) {
			return Error.NOT_EMPTY_TARGET;
		}
		return null;
	}
}