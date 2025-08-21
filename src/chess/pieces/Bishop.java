package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position bishopPosition = new Position(0, 0);

		// nw
		bishopPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
			bishopPosition.setValues(bishopPosition.getRow() - 1, bishopPosition.getColumn() - 1);

		}
		if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
		}

		// ne
		bishopPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
			bishopPosition.setValues(bishopPosition.getRow() - 1, bishopPosition.getColumn() + 1);
		}
		if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
		}

		// sw
		bishopPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
			bishopPosition.setValues(bishopPosition.getRow() + 1, getMoveCount() - 1);
		}
		if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
		}

		// se
		bishopPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
			bishopPosition.setValues(bishopPosition.getRow() + 1, bishopPosition.getColumn() + 1);
		}
		if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
			mat[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
		}

		return mat;
	}

	public String toString() {
		return "B";
	}

}
