package flappybird;

import java.awt.*;
import java.util.Random;

/**
 * Represents a pipe in Extreme Mode with sinusoidal vertical movement.
 * The pipe oscillates vertically to increase difficulty in the game.
 */
public class ExtremeModePipe extends Pipe {

    private final double oscillationAmplitude = 60; // Amplitude of the sinusoidal oscillation
    private final double oscillationSpeed = 0.08; // Speed of the sinusoidal oscillation
    private double time = 0; // Time variable for calculating the sinusoidal movement
    private final int baseGapY; // Base vertical position of the gap

    /**
     * Constructs an `ExtremeModePipe` with sinusoidal movement.
     *
     * @param startX      The initial horizontal position of the pipe.
     * @param panelHeight The height of the game panel.
     */
    public ExtremeModePipe(int startX, int panelHeight) {
        super(
                startX,
                panelHeight,
                80, // Width of the pipe
                11, // Speed of the pipe
                new Color( // Random color for the pipe
                        new Random().nextInt(256),
                        new Random().nextInt(256),
                        new Random().nextInt(256)
                )
        );
        int maxGapTop = panelHeight - groundHeight - gapHeight; // Maximum vertical position for the gap
        this.baseGapY = (int) (Math.random() * (maxGapTop - 50)) + 50; // Randomly generate the base gap position
        this.gapY = baseGapY; // Initialize the gap position
    }

    /**
     * Updates the pipe's position and calculates its sinusoidal movement.
     * The pipe moves leftward and oscillates vertically based on a sine wave.
     */
    @Override
    public void update() {
        x -= speed; // Move the pipe leftward
        time += oscillationSpeed; // Increment the time for sinusoidal calculation
        int maxGapTop = panelHeight - groundHeight - gapHeight; // Maximum vertical position for the gap
        gapY = baseGapY + (int) (Math.sin(time) * oscillationAmplitude); // Calculate the new gap position
        if (gapY < 50) gapY = 50; // Ensure the gap does not go below the minimum position
        if (gapY > maxGapTop) gapY = maxGapTop; // Ensure the gap does not exceed the maximum position
    }
}