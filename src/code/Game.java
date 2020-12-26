package code;

import java.io.IOException;

import javax.swing.*;

import code.model.Sound;
import code.ui.*;

public class Game {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new UI());
		Sound Background = new Sound();
		Background.playSound();
	}
}
