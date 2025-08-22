package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	public String toString() {
		return "K";
	}

	private boolean CanMove(Position position) {
		ChessPiece kingPosition = (ChessPiece) getBoard().piece(position);
		return kingPosition == null || kingPosition.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece currentPiece = (ChessPiece)getBoard().piece(position);
		// if not null in near to rook have the same color and moving is 0
		return currentPiece != null && currentPiece instanceof Rook && currentPiece.getColor() == getColor() && currentPiece.getMoveCount() == 0;
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
		
		// special moving castling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// special move castling kingside rook
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(posT1)) {
				Position emptySquare1 = new Position(position.getRow(), position.getColumn() + 1);
				Position emptySquare2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(emptySquare1) == null && getBoard().piece(emptySquare2)  == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// special move castling queenside rook
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posT2)) {
				Position emptySquare1 = new Position(position.getRow(), position.getColumn() - 1);
				Position emptySquare2 = new Position(position.getRow(), position.getColumn() - 2);
				Position emptySquare3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(emptySquare1) == null && getBoard().piece(emptySquare2)  == null && getBoard().piece(emptySquare3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
			

		}

		return mat;
	}
}
