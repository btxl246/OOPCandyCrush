package model;

import ui.UI;

import java.awt.*;

public class Model {
    private UI observer;
    private Grid grid;
    private Selector selector;

    public Model(Grid grid) {
        this.grid = grid;
        this.selector = new Selector(grid);
    }

    public void addObserver(UI ui) {
        this.observer = ui;
        observer.update();
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void select(int x, int y) {
        this.selector.selectedMove(new Point(x, y));
        this.observer.update();
    }

    public Selector getSelector() {
        return this.selector;
    }

    public boolean exit() {
        return !this.grid.moreCorrectMoves();
    }
}
