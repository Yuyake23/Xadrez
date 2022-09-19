package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {
		ChessMatch chessMatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);

		while (true) {
			try{
				UI.clearScreen();
				ChessPosition source, target;
				ChessPiece capturedPiece;
				UI.printBoard(chessMatch.getPieces());
				
				System.out.print("\nOrigem: ");
				source = UI.readChessPosition(sc);
				
				System.out.print("Destino: ");
				target = UI.readChessPosition(sc);
				
				capturedPiece = chessMatch.performChessMove(source, target);
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}
}
