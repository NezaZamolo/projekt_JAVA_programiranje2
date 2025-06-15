package flappybird;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the player-controlled bird in the Flappy Bird game.
 */
public class Bird {
    private int x, y; // The current position of the bird
    private int velocity; // The current vertical velocity of the bird
    private static final int GRAVITY = 1; // The constant gravity affecting the bird
    private static final int JUMP_STRENGTH = -12; // The strength of the bird's jump
    private static final int SIZE = 20; // The size of the bird for collision detection
    private static final int BIRD_SIZE = 50; // The size of the bird image
    private static final Image birdImage; // The image representing the bird

    // Static block to initialize the bird image
    static {
        birdImage = new ImageIcon("media/bird.png").getImage();
    }

    /**
     * Constructs a new Bird at the specified (x, y) position.
     *
     * @param x Initial horizontal position of the bird.
     * @param y Initial vertical position of the bird.
     */
    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0; // Initialize the bird's velocity to 0
    }

    /**
     * Updates the bird's position based on gravity.
     * This method is called on each game tick to simulate the bird's movement.
     */
    public void update() {
        velocity += GRAVITY; // Apply gravity to the bird's velocity
        y += velocity; // Update the bird's vertical position
    }

    /**
     * Makes the bird jump (move upward).
     * Sets the bird's velocity to the jump strength and plays a sound effect.
     */
    public void jump() {
        velocity = JUMP_STRENGTH; // Set the velocity to the jump strength
        SoundPlayer.playSound("jump.wav"); // Play the jump sound effect
    }

    /**
     * Sets the bird's vertical velocity.
     *
     * @param v The new velocity to set for the bird.
     */
    public void setVelocity(int v) {
        this.velocity = v;
    }

    /**
     * Draws the bird on the screen.
     * If the bird image is available, it is drawn; otherwise, a red oval is drawn.
     *
     * @param g The Graphics context used for drawing.
     */
    public void draw(Graphics g) {
        if (birdImage != null) {
            g.drawImage(birdImage, x, y, BIRD_SIZE, BIRD_SIZE, null); // Draw the bird image
        } else {
            g.setColor(Color.red); // Set the color to red
            g.fillOval(x, y, SIZE, SIZE); // Draw a red oval representing the bird
        }
    }

    /**
     * Returns the bounding rectangle of the bird for collision detection.
     *
     * @return A Rectangle representing the bird's bounds.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE); // Return the bird's bounding rectangle
    }

    /**
     * Returns the vertical position of the bird.
     *
     * @return The y-coordinate of the bird.
     */
    public int getY() {
        return y;
    }
}