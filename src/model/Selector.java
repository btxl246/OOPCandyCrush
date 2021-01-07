/**
 * Object-Oriented Programming project.
 * @author Bui Thi Xuan Lan - ITDSIU19007
 * @author Nguyen Duc Minh - ITITIU19030
 */

package model;

import java.awt.*;

/**
 * Handles selections in a play grid.
 */

public class Selector {
    private Point first;        // First selected tiles.
    private Point second;       // Second selected tiles.
    private final Grid GRID;    // Play grid.

    /**
     * Selector constructor.
     * @param grid Play grid.
     */

    public Selector(Grid grid) {
        this.GRID = grid;
        clearLastMove();
    }

    /**
     * Processes selections.
     * @param p Tile.
     */

    public void processSelectedMove(Point p) {
        // If this is the first of two selections.
        if (this.first == null)
            first = p;
        // If this is the second of two selections
        else {
            this.second = p;
            if (adjacent(first, second)) {
                // Tests the switch.
                this.GRID.switchTilesTest(first, second);

                // If not a match, switches back.
                if (!GRID.checkMatches())
                    GRID.switchTilesTest(first, second);

                // Processes the switch.
                GRID.switchTiles();
            }

            // Clears the selections.
            clearLastMove();
        }
    }

    /**
     * Checks if two selected tiles are adjacent.
     * @param a First tile.
     * @param b Second tile.
     * @return True if adjacent.
     */

    private boolean adjacent(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) == 1;
    }

    /**
     * Clear the selections.
     */

    private void clearLastMove() {
        this.first = null;
        this.second = null;
    }

    /**
     * Returns the first selection.
     * @return Tile.
     */

    public Point getFirst() {
        return this.first;
    }
}
