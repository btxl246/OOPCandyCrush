package model;

import java.awt.*;

public class Selector {
    private Point first;
    private Point second;
    private final Grid grid;

    public Selector(Grid grid) {
        this.grid = grid;
        clearLastMove();
    }

    public void selectedMove(Point p) {
        if (this.first == null) {
            first = p;
        }
        else {
            this.second = p;
            if (adjacent(first, second)) {
                this.grid.switchTilesDemo(first, second);

                if (!grid.checkMatches()) {
                    grid.switchTilesDemo(first, second);
                }

                grid.switchTiles();
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
