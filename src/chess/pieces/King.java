package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	public String toString() {
		return "K";
	}

	private boolean CanMove(Position position) {
		ChessPiece kingPosition = (ChessPiece) getBoard().piece(position);
		return kingPosition == null || kingPosition.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position kingPostion = new Position(0, 0);

		// above
		kingPostion.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		// below
		kingPostion.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		// left
		kingPostion.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		// right
		kingPostion.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		// nw
		kingPostion.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		// ne
		kingPostion.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		// sw
		kingPostion.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		// se
		kingPostion.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(kingPostion) && CanMove(kingPostion)) {
			mat[kingPostion.getRow()][kingPostion.getColumn()] = true;
		}

		return mat;
	}
}
