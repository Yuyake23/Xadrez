package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	private boolean canMove(Position position) {
		ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
		return chessPiece == null || !chessPiece.getColor().equals(this.getColor());
	}

	@Override
	public String toString() {
		return "N";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] pm = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		int v1[] = { +1, -1 };
		int v2[] = { +2, -2 };

		for (int i : v1) {
			for (int j : v2) {
				p.setValues(position.getRow() + i, position.getColumn() + j);
				if (getBoard().positionExists(p) && canMove(p))
					pm[p.getRow()][p.getColumn()] = true;
				
				p.setValues(position.getRow() + j, position.getColumn() + i);
				if (getBoard().positionExists(p) && canMove(p))
					pm[p.getRow()][p.getColumn()] = true;
			}	
		}

		return pm;
	}
}
