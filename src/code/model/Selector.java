package code.model;

import java.awt.Point;

public class Selector {
	private Point selectedFirst;	// First selection.
	private Point selectedSecond;	// Second selection.
	private Grid grid;				// The grid.

	/**
	 * Constructor.
	 * @param grid The grid.
	 */

	public Selector(Grid grid) {
		this.grid = grid;
		this.clearLastMove();
	}

	/**
	 * The selectedMove() method checks if a move is correct and clear the match.
	 * @param p Either first or second selection.
	 */

	public void selectedMove(Point p) {
		if (this.selectedFirst == null) {
			selectedFirst = p;
		}
		else {
			this.selectedSecond = p;
			if (this.adjacent(selectedFirst, selectedSecond)) {
				this.grid.exchange(selectedFirst, selectedSecond);
				if (!grid.matchCheck()) {
					grid.exchange(selectedFirst, selectedSecond);
				}
				/*try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				grid.exchangeExtension();
			}
			this.clearLastMove();
		}
	}

	/**
	 * The getSelectedFirst() method returns the first selection.
	 * @return The first selection.
	 */

	public Point getSelectedFirst() {
		return this.selectedFirst;
	}

	/**
	 * The adjacent() method checks if two tiles are next to each other.
	 * @param a The first tile.
	 * @param b The second tile.
	 * @return True if adjacent.
	 */

	private boolean adjacent(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) == 1;
	}

	/**
	 * The clearLastMove() method nullifies the previous two selections in the last move.
	 */

	private void clearLastMove() {
		this.selectedFirst = null;
		this.selectedSecond = null;
	}
}
