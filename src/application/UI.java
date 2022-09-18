package application;

import chess.ChessPiece;

public class UI {
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.printf("%d ", 8 - i);
			for (int j = 0; j < pieces[0].length; j++) {
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	private static void printPiece(ChessPiece piece) {
		System.out.print((piece == null ? '-' : piece) + " ");
	}
}