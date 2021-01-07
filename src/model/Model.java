/**
 * Object-Oriented Programming project.
 * @author Bui Thi Xuan Lan - ITDSIU19007
 * @author Nguyen Duc Minh - ITITIU19030
 */

package model;

import ui.UI;

import java.awt.*;

/**
 * Handles changes in the UI and the play grid.
 */

public class Model {
    private UI observer;                // UI.
    private final Grid GRID;            // Play grid.
    private final Selector SELECTOR;    // Selector.

    /**
     * Model constructor.
     * @param grid Play grid.
     */

    public Model(Grid grid) {
        this.GRID = grid;
        this.SELECTOR = new Selector(grid);
    }

    /**
     * Observes the UI and updates it accordingly.
     * @param ui UI in observation.
     */

    public void addModelObserver(UI ui) {
        this.observer = ui;
        observer.update();
    }

    /**
     * Handles selections and updates the UI accordingly.
     * @param x X coordinate of a tile.
     * @param y Y coordinate of a tile.
     */

    public void select(int x, int y) {
        this.SELECTOR.processSelectedMove(new Point(x, y));
        this.observer.update();
    }

    /**
     * If the play grid has no more correct moves, the game is over.
     * @return True if the game ends.
     */

    public boolean exit() {
        return !this.GRID.moreCorrectMoves();
    }

    /**
     * Returns the current Grid handler.
     * @return Play grid.
     */

    public Grid getGrid() {
        return this.GRID;
    }

    /**
     * Returns the current Selector handler.
     * @return Selector.
     */

    public Selector getSelector() {
        return this.SELECTOR;
    }
}
