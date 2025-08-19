package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position rookPositionInitial = new Position(0, 0);

		// above
		rookPositionInitial.setValues(position.getRow() - 1, position.getColumn());
		while (getBoard().positionExists(rookPositionInitial) && !getBoard().thereIsAPiece(rookPositionInitial)) {
			mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			rookPositionInitial.setRow(rookPositionInitial.getRow() - 1);

			if (getBoard().positionExists(rookPositionInitial) && isThereOpponentPiece(rookPositionInitial)) {
				mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			}
		}

		// left
		rookPositionInitial.setValues(position.getRow(), position.getColumn() - 1);
		while (getBoard().positionExists(rookPositionInitial) && !getBoard().thereIsAPiece(rookPositionInitial)) {
			mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			rookPositionInitial.setColumn(rookPositionInitial.getColumn() - 1);

			if (getBoard().positionExists(rookPositionInitial) && isThereOpponentPiece(rookPositionInitial)) {
				mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			}
		}

		// right
		rookPositionInitial.setValues(position.getRow(), position.getColumn() + 1);
		while (getBoard().positionExists(rookPositionInitial) && !getBoard().thereIsAPiece(rookPositionInitial)) {
			mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			rookPositionInitial.setColumn(rookPositionInitial.getColumn() + 1);

			if (getBoard().positionExists(rookPositionInitial) && isThereOpponentPiece(rookPositionInitial)) {
				mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			}
		}

		// below
		rookPositionInitial.setValues(position.getRow() + 1, position.getColumn());
		while (getBoard().positionExists(rookPositionInitial) && !getBoard().thereIsAPiece(rookPositionInitial)) {
			mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			rookPositionInitial.setRow(rookPositionInitial.getRow() + 1);

			if (getBoard().positionExists(rookPositionInitial) && isThereOpponentPiece(rookPositionInitial)) {
				mat[rookPositionInitial.getRow()][rookPositionInitial.getColumn()] = true;
			}
		}

		return mat;
	}
}
