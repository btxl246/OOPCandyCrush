package code;

import java.io.IOException;

import javax.swing.*;
import code.ui.*;

public class Game {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new UI());

		LeaderBoard highscore = new LeaderBoard();
		highscore.test();
	}
}
