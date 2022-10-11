package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import chess.pieces.King;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
		}
	}

	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedPieces) {
		printBoard(chessMatch);
		System.out.println();
		printCapturedPieces(capturedPieces);
		System.out.println("\nTurn: " + chessMatch.getTurn());
		if (!chessMatch.getCheckMate()) {
			System.out.println("Waiting player: " + getUIPieceColor(chessMatch.getCurrentPlayer())
					+ chessMatch.getCurrentPlayer() + ANSI_RESET);
			if (chessMatch.getCheck()) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + getUIPieceColor(chessMatch.getCurrentPlayer())
					+ chessMatch.getCurrentPlayer() + ANSI_RESET);
		}
	}

	public static void printBoard(ChessMatch chessMatch) {
		ChessPiece[][] pieces = chessMatch.getPieces();
		for (int i = 0; i < pieces.length; i++) {
			System.out.printf("%d ", 8 - i);
			for (int j = 0; j < pieces[0].length; j++) {
				printPiece(pieces[i][j], chessMatch, false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	public static void printBoard(ChessMatch chessMatch, boolean[][] possibleMovies) {
		ChessPiece[][] pieces = chessMatch.getPieces();
		for (int i = 0; i < pieces.length; i++) {
			System.out.printf("%d ", 8 - i);
			for (int j = 0; j < pieces[0].length; j++) {
				printPiece(pieces[i][j], chessMatch, possibleMovies[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	private static void printPiece(ChessPiece piece, ChessMatch chessMatch, boolean highlight) {
		if (highlight)
			System.out.print(ANSI_PURPLE_BACKGROUND);

		if (piece == null) {
			System.out.print('-');
		} else {
			if (piece instanceof King && chessMatch.testCheck(piece.getColor())) {
				// if it's in check
				System.out.print(ANSI_RED);
			} else {
				System.out.print(getUIPieceColor(piece.getColor()));
			}
			System.out.print(piece);
		}
		System.out.print(ANSI_RESET + " ");
	}

	private static void printCapturedPieces(List<ChessPiece> capturedPieces) {
		List<ChessPiece> whites = capturedPieces.stream().filter(x -> x.getColor() == Color.WHITE)
				.collect(Collectors.toList());
		List<ChessPiece> blacks = capturedPieces.stream().filter(x -> x.getColor() == Color.BLACK)
				.collect(Collectors.toList());

		System.out.println("Captured pieces:");
		System.out.println("White: " + getUIPieceColor(Color.WHITE) + Arrays.toString(whites.toArray()) + ANSI_RESET);
		System.out.println("Black: " + getUIPieceColor(Color.BLACK) + Arrays.toString(blacks.toArray()) + ANSI_RESET);
	}

	public static String getUIPieceColor(Color color) {
		return color == Color.WHITE ? ANSI_BLUE : ANSI_YELLOW;
	}

}
