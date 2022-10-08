package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> capturedPieces = new ArrayList<>();

		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				ChessPosition source, target;
				ChessPiece capturedPiece;
				UI.printMatch(chessMatch, capturedPieces);

				System.out.print("\nOrigem: ");
				source = UI.readChessPosition(sc);

				boolean possibleMovies[][] = chessMatch.possibleMovies(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMovies);

				System.out.print("Destino: ");
				target = UI.readChessPosition(sc);

				System.out.println("indo fazer movimento");
				capturedPiece = chessMatch.performChessMove(source, target);
				System.out.println("movimento feito");
				if (capturedPiece != null)
					capturedPieces.add(capturedPiece);
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, capturedPieces);
	}
}
