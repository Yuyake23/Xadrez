package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.UI;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board   board;
	private int     turn;
	private Color   currentPlayer;
	private boolean check;        // is false by default

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces   = new ArrayList<>();

	public ChessMatch() {
		this.board = new Board(8, 8);
		this.turn = 1;
		this.currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean isCheck() {
		return check;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] chessPieces = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++)
			for (int j = 0; j < board.getColumns(); j++)
				chessPieces[i][j] = (ChessPiece) board.piece(i, j);

		return chessPieces;
	}

	public boolean[][] possibleMovies(ChessPosition sourcePosition) {
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
		
		this.check = testCheck(opponent(currentPlayer));

		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p             = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece piece = board.removePiece(target);
		board.placePiece(piece, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			this.capturedPieces.remove(capturedPiece);
			this.piecesOnTheBoard.add(capturedPiece);
		}
	}

	private void validateSourcePosition(Position source) {
		if (!board.thereIsAPiece(source))
			throw new ChessException("There is no piece on source position");
		if (currentPlayer != ((ChessPiece) board.piece(source)).getColor())
			throw new ChessException("The chose piece is not yours");
		if (!board.piece(source).isThereAnyPossibleMove())
			throw new ChessException("There is no possible moves for the chosen piece");
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target))
			throw new ChessException("The chosen piece can't move to target position");
	}

	private void nextTurn() {
		this.turn++;
		this.currentPlayer = this.currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		return color == Color.WHITE ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		for (Piece piece : this.piecesOnTheBoard) {
			if (((ChessPiece) piece).getColor() == color && piece instanceof King king) {
				return king;
			}
		}
		throw new IllegalStateException(
				"There is no " + UI.getUIPieceColor(color) + color + UI.ANSI_RESET + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position    kingPosition   = king(color).getPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(p -> ((ChessPiece) p).getColor() != color)
				.collect(Collectors.toList());
		for (Piece opponentPiece : opponentPieces) {
			if (opponentPiece.possibleMoves()[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
