package code.model;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private File soundFile = new File("8-bit Coffin Dance.wav");
	private AudioInputStream audioIn;
	
	public void playSound() {
		try{
			Clip clip = AudioSystem.getClip();
			audioIn = AudioSystem.getAudioInputStream(soundFile);
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			System.out.print("Something wong, no file found");
		}
	}
}
