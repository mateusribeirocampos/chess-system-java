package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}
	
	private boolean CanMove(Position position) {
		ChessPiece knightPostion = (ChessPiece) getBoard().piece(position);
		return knightPostion == null || knightPostion.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position knightPostion = new Position(0, 0);

		knightPostion.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}

		knightPostion.setValues(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}

		knightPostion.setValues(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}

		knightPostion.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}

		knightPostion.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}

		knightPostion.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}

		knightPostion.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}

		knightPostion.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(knightPostion) && CanMove(knightPostion)) {
			mat[knightPostion.getRow()][knightPostion.getColumn()] = true;
		}
		return mat;
	}

	@Override
	public String toString() {
		return "N";
	}

}
