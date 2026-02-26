package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private boolean stalemate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<Piece> pieceOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);

			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// special move promotion
		promoted = null;

		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
					|| (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else if (testStalemate(opponent(currentPlayer))) {
			stalemate = true;
		} else {
			nextTurn();
		}

		// Espcial mode en passant
		if (movedPiece instanceof Pawn && ((target.getRow() == source.getRow() - 2)
				|| (target.getRow() == source.getRow() + 2))) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece;
	}

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted;
		}

		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		pieceOnTheBoard.remove(p);

		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		pieceOnTheBoard.add(newPiece);

		return newPiece;
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B"))
			return new Bishop(board, color);
		if (type.equals("N"))
			return new Knight(board, color);
		if (type.equals("Q"))
			return new Queen(board, color);
		return new Rook(board, color);
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chose piece");
		}
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece positionSource = (ChessPiece) board.removePiece(source);
		positionSource.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(positionSource, target);

		if (capturedPiece != null) {
			pieceOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// Special move castling king side rook
		if (positionSource instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}

		// Special move castling queen side rook
		if (positionSource instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}

		// Special move en passant
		if (positionSource instanceof Pawn) {
			// if the pawn move in the diagonal and not get a piece than is en passant
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (positionSource.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				pieceOnTheBoard.remove(capturedPiece);
			}
		}
		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece positionTarget = (ChessPiece) board.removePiece(target);
		positionTarget.decreaseMoveCount();
		board.placePiece(positionTarget, source);
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			pieceOnTheBoard.add(capturedPiece);
		}

		// Special move castling king side rook
		if (positionTarget instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();
		}

		// Special move castling queen side rook
		if (positionTarget instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();
		}

		// Special move en passant
		if (positionTarget instanceof Pawn) {
			// if the pawn move in the diagonal and not get a piece than is en passant
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				Position pawnPosition;
				if (positionTarget.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("the chosen piece can't move to target position");
		}
	}

	private Color opponent(Color color) {
		return (color == color.WHITE) ? color.BLACK : color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> colorPieces = pieceOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece currentPiece : colorPieces) {
			if (currentPiece instanceof King) {
				return (ChessPiece) currentPiece;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = pieceOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece opponentCurrentPiece : opponentPieces) {
			boolean[][] mat = opponentCurrentPiece.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true; // king is in check
			}
		}
		return false; // king is not in check
	}

	private boolean testCheckMate(Color color) {
		// if the king can move for other position without to check Mate return false
		if (!testCheck(color)) {
			return false;
		}

		// Verify all the pieces on the with opponent color and filter this color
		List<Piece> colorPieces = pieceOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		// currentPieces will go through the entire list of colorPieces
		for (Piece currentPiece : colorPieces) {
			// possible move that do not left the king in checkMate than return false;
			boolean[][] mat = currentPiece.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					// this position mat[i][j] leave the check?
					if (mat[i][j]) {
						// origin position of piece. From ChessPiece convert to position
						Position source = ((ChessPiece) currentPiece).getChessPosition().toPosition();
						// destiny position was instantiate a new position
						Position target = new Position(i, j);
						// capturePiece is moving from origin to target
						Piece capturedPiece = makeMove(source, target);
						// test if the king color
						boolean testCheck = testCheck(color);
						// undo the move after test
						undoMove(source, target, capturedPiece);
						// if is not in check return false
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public boolean getStalemate() {
		return stalemate;
	}

	private boolean testStalemate(Color color) {
		if (testCheck(color)) {
			return false;
		}
		List<Piece> colorPieces = pieceOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece currentPiece : colorPieces) {
			boolean[][] mat = currentPiece.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) currentPiece).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		pieceOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
