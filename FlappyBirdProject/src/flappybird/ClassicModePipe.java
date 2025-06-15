package flappybird;

import java.awt.*;

/**
 * Represents an obstacle pipe in the Flappy Bird game.
 */
public class ClassicModePipe {
    private int x; // Current horizontal position of the pipe
    private int gapY; // Vertical position of the gap between the top and bottom pipes
    private static final int WIDTH = 60; // Width of the pipe
    private static final int GAP_HEIGHT = 200; // Height of the gap between the top and bottom pipes
    private static final int SPEED = 4; // Speed at which the pipe moves leftward
    private final int panelHeight; // Total height of the game panel
    private static final int GROUND_HEIGHT = 100; // Height of the ground at the bottom of the panel

    /**
     * Constructs a new Pipe at the specified starting x-position.
     *
     * @param startX       Initial x-coordinate of the pipe.
     * @param panelHeight  Total height of the game panel.
     */
    public ClassicModePipe(int startX, int panelHeight) {
        this.x = startX;
        this.panelHeight = panelHeight;
        int maxGapTop = panelHeight - GROUND_HEIGHT - GAP_HEIGHT; // Maximum vertical position for the gap
        this.gapY = (int) (Math.random() * (maxGapTop - 50)) + 50; // Randomly generate the gap's vertical position
    }

    /**
     * Updates the pipe's position by moving it leftward.
     */
    public void update() {
        x -= SPEED; // Decrease the x-coordinate to move the pipe leftward
    }

    /**
     * Draws the pipe (top and bottom) on the screen.
     *
     * @param g The Graphics context used for drawing.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Draw the main body of the pipe
        g2.setColor(new Color(30, 180, 70)); // Green color for the pipe
        g2.fillRect(x, 0, WIDTH, gapY); // Top pipe
        g2.fillRect(x, gapY + GAP_HEIGHT, WIDTH, panelHeight - GROUND_HEIGHT - (gapY + GAP_HEIGHT)); // Bottom pipe

        // Draw shadows and highlights for depth
        g2.setColor(new Color(20, 130, 50)); // Darker left edge
        g2.fillRect(x, 0, 5, gapY);
        g2.fillRect(x, gapY + GAP_HEIGHT, 5, panelHeight - GROUND_HEIGHT - (gapY + GAP_HEIGHT));

        g2.setColor(new Color(50, 220, 90)); // Lighter right edge
        g2.fillRect(x + WIDTH - 5, 0, 5, gapY);
        g2.fillRect(x + WIDTH - 5, gapY + GAP_HEIGHT, 5, panelHeight - GROUND_HEIGHT - (gapY + GAP_HEIGHT));

        // Draw rounded gaps at the top and bottom of the pipe
        g2.setColor(new Color(25, 160, 60));
        g2.fillRoundRect(x - 4, gapY - 10, WIDTH + 8, 12, 10, 10); // Top gap
        g2.fillRoundRect(x - 4, gapY + GAP_HEIGHT - 2, WIDTH + 8, 12, 10, 10); // Bottom gap
    }

    /**
     * Checks whether the given bird collides with this pipe.
     *
     * @param bird The Bird instance to check for collision.
     * @return True if the bird intersects with the pipe; false otherwise.
     */
    public boolean collidesWith(Bird bird) {
        Rectangle birdRect = bird.getBounds(); // Get the bounding rectangle of the bird
        Rectangle topPipe = new Rectangle(x, 0, WIDTH, gapY); // Bounding rectangle of the top pipe
        Rectangle bottomPipe = new Rectangle(x, gapY + GAP_HEIGHT, WIDTH, panelHeight - GROUND_HEIGHT - (gapY + GAP_HEIGHT)); // Bounding rectangle of the bottom pipe
        return birdRect.intersects(topPipe) || birdRect.intersects(bottomPipe); // Check for intersection
    }

    /**
     * Checks if the pipe has moved off the left side of the screen.
     *
     * @return True if the pipe is off-screen; false otherwise.
     */
    public boolean isOffScreen() {
        return x + WIDTH < 0; // Check if the pipe's right edge is beyond the left edge of the screen
    }

    /**
     * Returns the current horizontal position of the pipe.
     *
     * @return The x-coordinate of the pipe.
     */
    public int getX() {
        return x;
    }
}