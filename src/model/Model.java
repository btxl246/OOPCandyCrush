package model;

import ui.UI;

import java.awt.*;

public class Model {
    private UI observer;
    private final Grid GRID;
    private final Selector SELECTOR;

    public Model(Grid grid) {
        this.GRID = grid;
        this.SELECTOR = new Selector(grid);
    }

    public void addModelObserver(UI ui) {
        this.observer = ui;
        observer.update();
    }

    public void select(int x, int y) {
        this.SELECTOR.processSelectedMove(new Point(x, y));
        this.observer.update();
    }

    public boolean exit() {
        return !this.GRID.moreCorrectMoves();
    }

    public Grid getGrid() {
        return this.GRID;
    }

    public Selector getSelector() {
        return this.SELECTOR;
    }
}
