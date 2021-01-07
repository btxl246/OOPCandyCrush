package model;

import java.awt.*;

public class Selector {
    private Point first;
    private Point second;
    private final Grid GRID;

    public Selector(Grid grid) {
        this.GRID = grid;
        clearLastMove();
    }

    public void processSelectedMove(Point p) {
        if (this.first == null) {
            first = p;
        }
        else {
            this.second = p;
            if (adjacent(first, second)) {
                this.GRID.switchTilesTest(first, second);

                if (!GRID.checkMatches()) {
                    GRID.switchTilesTest(first, second);
                }

                GRID.switchTiles();
            }

            clearLastMove();
        }
    }

    private boolean adjacent(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) == 1;
    }

    private void clearLastMove() {
        this.first = null;
        this.second = null;
    }

    public Point getFirst() {
        return this.first;
    }
}
