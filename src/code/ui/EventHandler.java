package code.ui;

import code.model.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener {
	private Model model;
	private int row;
	private int col;

	/**
	 * Constructor.
	 * @param model The model.
	 * @param row The row.
	 * @param col The column.
	 */

	public EventHandler(Model model, int row, int col) {
		this.model = model;
		this.row = row;
		this.col = col;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.model.select(this.row, this.col);
	}
}
