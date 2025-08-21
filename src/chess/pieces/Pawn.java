package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position pawnPosition = new Position(0, 0);
		// Verify what kind piece color the user is play
		if (getColor() == Color.WHITE) {
			pawnPosition.setValues(position.getRow() - 1, position.getColumn());
			// verify the position that pawn can move
			if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
			pawnPosition.setValues(position.getRow() - 2, position.getColumn());
			Position pawnPosition2 = new Position(position.getRow() - 1, position.getColumn());
			// verify the second position that pawn can move
			if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)
					&& getBoard().positionExists(pawnPosition2) && !getBoard().thereIsAPiece(pawnPosition2)
					&& getMoveCount() == 0) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
			pawnPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
			// verify the position in the diagonal if only the pawn attack nw
			if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
			pawnPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
			// verify the position in the diagonal if only the pawn attack ne
			if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
		} else {
			pawnPosition.setValues(position.getRow() + 1, position.getColumn());
			// verify the position that pawn can move
			if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
			pawnPosition.setValues(position.getRow() + 2, position.getColumn());
			Position pawnPosition2 = new Position(position.getRow() + 1, position.getColumn());
			// verify the second position that pawn can move
			if (getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)
					&& getBoard().positionExists(pawnPosition2) && !getBoard().thereIsAPiece(pawnPosition2)
					&& getMoveCount() == 0) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
			pawnPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
			// verify the position in the diagonal if only the pawn attack nw
			if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
			pawnPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
			// verify the position in the diagonal if only the pawn attack ne
			if (getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
				mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}