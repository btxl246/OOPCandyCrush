package code.model;

import code.ui.UI;

import java.awt.Point;

public class Model {
	private UI observer;	// Observes UI changes.
	private Grid grid = new Grid(5, 5);	// Grid initialization.
	private Selector selector;	// Observes selection.

	public Model() {
		this.selector = new Selector(this.grid);
	}

	/**
	 * The addObserver() method requires the UI to be updated.
	 * @param ui The UI.
	 */

	public void addObserver(UI ui) {
		this.observer = ui;
		this.observer.update();
	}

	/**
	 * The rows() method returns the number of rows.
	 * @return The number of rows.
	 */

	public int rows() {
		return this.grid.rows();
	}

	/**
	 * The cols() method returns the number of rows.
	 * @return The number of rows.
	 */

	public int cols() {
		return this.grid.cols();
	}

	/**
	 * The get() method returns the current Point's icon's name.
	 * @param p The point.
	 * @return The name.
	 */

	public String get(Point p) {
		return this.grid.get(p);
	}

	/**
	 * The getSelectedFirst() method returns the Point that was selected first.
	 * @return The point.
	 */

	public Point selectedFirst() {
		return this.selector.getSelectedFirst();
	}

	/**
	 * The selectedMove() method requires the UI to be updated if the selected move is correct.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */

	public void select(int x, int y) {
		this.selector.selectedMove(new Point(x, y));
		this.observer.update();
	}

	/**
	 * The score() method passes the total score from Grid to Model.
	 * @return The total score.
	 */

	public int score() {
		return this.grid.exchangeExtension();
	}

	/**
	 * The exit() method checks if the game ends due to no more possible moves.
	 * @return True if no more possible moves.
	 */

	public boolean exit() {
		return !this.grid.moreCorrectMoves();	// (!false)
	}
}
