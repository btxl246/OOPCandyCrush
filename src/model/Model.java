package model;

import ui.UI;

import java.awt.*;

public class Model {
    private UI observer;
    private final Grid grid;
    private final Selector selector;

    public Model(Grid grid) {
        this.grid = grid;
        this.selector = new Selector(grid);
    }

    public void addModelObserver(UI ui) {
        this.observer = ui;
        observer.update();
    }

    public void select(int x, int y) {
        this.selector.selectedMove(new Point(x, y));
        this.observer.update();
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Selector getSelector() {
        return this.selector;
    }

    public boolean exit() {
        if (this.grid.getMoreCorrectMoves() == 0)
            return grid.moreCorrectMoves();

        return !grid.moreCorrectMoves();
    }
}