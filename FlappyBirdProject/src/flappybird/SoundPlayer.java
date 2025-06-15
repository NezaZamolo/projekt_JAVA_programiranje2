package flappybird;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Utility class for playing short sound effects (.wav).
 * This class provides a static method to play sound files from the 'sounds/' folder.
 */
public class SoundPlayer {

    /**
     * Plays a sound file located in the 'sounds/' folder.
     * The method attempts to load and play the specified .wav file.
     * If the file does not exist or an error occurs during playback, the exception is caught and printed.
     *
     * @param fileName The name of the .wav file to play (e.g., "jump.wav").
     */
    public static void playSound(String fileName) {
        try {
            File file = new File("sounds/" + fileName); // Locate the sound file
            if (!file.exists()) return; // Exit if the file does not exist
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file); // Load the audio stream
            Clip clip = AudioSystem.getClip(); // Obtain a clip for playback
            clip.open(audioIn); // Open the audio stream in the clip
            clip.start(); // Start playing the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
    }
}