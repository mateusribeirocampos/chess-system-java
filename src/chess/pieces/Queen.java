package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position queenPosition = new Position(0, 0);
		
		
		// above
		queenPosition.setValues(position.getRow() - 1, position.getColumn());
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setRow(queenPosition.getRow() - 1);

		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}

		// left
		queenPosition.setValues(position.getRow(), position.getColumn() - 1);
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setColumn(queenPosition.getColumn() - 1);
		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}

		// right
		queenPosition.setValues(position.getRow(), position.getColumn() + 1);
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setColumn(queenPosition.getColumn() + 1);
		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}

		// below
		queenPosition.setValues(position.getRow() + 1, position.getColumn());
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setRow(queenPosition.getRow() + 1);
		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}
		
		// nw
		queenPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setValues(queenPosition.getRow() - 1, queenPosition.getColumn() - 1);

		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}

		// ne
		queenPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setValues(queenPosition.getRow() - 1, queenPosition.getColumn() + 1);
		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}

		// sw
		queenPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setValues(queenPosition.getRow() + 1, getMoveCount() - 1);
		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}

		// se
		queenPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
			queenPosition.setValues(queenPosition.getRow() + 1, queenPosition.getColumn() + 1);
		}
		if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
			mat[queenPosition.getRow()][queenPosition.getColumn()] = true;
		}

		return mat;

	}

	@Override
	public String toString() {
		return "Q";
	}
	
	

}
