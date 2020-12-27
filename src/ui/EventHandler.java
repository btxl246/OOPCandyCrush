package ui;

import model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener {
    private Model model;
    private int x;
    private int y;

    public EventHandler(Model model, int x, int y) {
        this.model = model;
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.select(this.x, this.y);
    }
}
