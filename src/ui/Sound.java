/**
 * Object-Oriented Programming project.
 * @author Bui Thi Xuan Lan - ITDSIU19007
 * @author Nguyen Duc Minh - ITITIU19030
 */

package ui;

import java.io.*;
import javax.sound.sampled.*;

/**
 * Plays a sound using Singleton.
 */

public class Sound implements LineListener {
    private static Sound instance;
    private boolean playCompleted;

    private Sound() {}

    public static Sound getInstance() {
        if (instance == null)
            instance = new Sound();

        return instance;
    }

    /**
     * Nullifies the instance.
     */

    public void remove() {
        instance = null;
    }

    /**
     * Plays the sound.
     */

    public void play() {
        File audioFile = new File("in/sound.wav");

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.start();

            // Keeps the program going before the audio file ends.
            while (!playCompleted) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    /**
     * Listens to the START and STOP events of the audio line.
     * @param event LineEvent of the audio file.
     */

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.STOP)
            this.playCompleted = true;
    }
}
