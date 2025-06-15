package flappybird;

import java.awt.*;
import java.util.Random;

/**
 * Represents a pipe in Hard Mode with vertical oscillation movement.
 * The pipe moves up and down to increase difficulty in the game.
 */
public class HardModePipe extends Pipe {

    private int direction = 1; // Direction of vertical movement (1 for down, -1 for up)
    private final int oscillationSpeed = 2; // Speed of the vertical oscillation

    /**
     * Constructs a `HardModePipe` with specified starting position and panel height.
     * The pipe is initialized with a random color and fixed dimensions.
     *
     * @param startX      The initial horizontal position of the pipe.
     * @param panelHeight The height of the game panel.
     */
    public HardModePipe(int startX, int panelHeight) {
        super(
                startX,
                panelHeight,
                150, // Width of the pipe
                5,   // Speed of the pipe
                new Color( // Random color for the pipe
                        new Random().nextInt(256),
                        new Random().nextInt(256),
                        new Random().nextInt(256)
                )
        );
    }

    /**
     * Updates the pipe's position and handles its vertical oscillation.
     * The pipe moves leftward and oscillates vertically within the allowed range.
     */
    @Override
    public void update() {
        x -= speed; // Move the pipe leftward
        gapY += direction * oscillationSpeed; // Adjust the vertical position of the gap
        int maxGapTop = panelHeight - groundHeight - gapHeight; // Maximum vertical position for the gap
        if (gapY <= 50 || gapY >= maxGapTop) { // Reverse direction if the gap reaches bounds
            direction *= -1;
        }
    }
}