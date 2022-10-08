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
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] pm = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, this.position.getColumn());
		// wildcard modifier it will be negative if the piece color is white and
		// positive if not
		// This will help to know which way the piece should move
		int wm = this.getColor() == Color.WHITE ? -1 : 1;

		// in front
		p.setRow(this.position.getRow() + wm);
		if (!getBoard().thereIsAPiece(p)) {
			pm[p.getRow()][p.getColumn()] = true;
			if (this.getMoveCount() == 0) {
				p.setRow(this.position.getRow() + wm * 2);
				if (!getBoard().thereIsAPiece(p))
					pm[p.getRow()][p.getColumn()] = true;
			}
		}

		// in diagonal
		p.setRow(this.position.getRow() + wm);
		// left
		p.setColumn(this.position.getColumn() + wm);
		if (getBoard().positionExists(p) && getBoard().thereIsAPiece(p)
				&& ((ChessPiece) getBoard().piece(p)).getColor() != this.getColor()) {
			pm[p.getRow()][p.getColumn()] = true;
		}
		// right
		p.setColumn(this.position.getColumn() - wm);
		if (getBoard().positionExists(p) && getBoard().thereIsAPiece(p)
				&& ((ChessPiece) getBoard().piece(p)).getColor() != this.getColor()) {
			pm[p.getRow()][p.getColumn()] = true;
		}

		return pm;
	}
}
