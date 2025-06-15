package flappybird;

import java.awt.*;

/**
 * Abstract base class for obstacle pipes used in different levels.
 * This class provides common functionality for drawing pipes, detecting collisions,
 * and managing their movement across the screen.
 */
public abstract class Pipe extends ClassicModePipe {

    protected int x; // Current horizontal position of the pipe
    protected int gapY; // Vertical position of the gap between top and bottom pipes
    protected final int width = 60; // Width of the pipe
    protected final int gapHeight; // Height of the gap between top and bottom pipes
    protected final int speed; // Horizontal movement speed of the pipe
    protected final int panelHeight; // Height of the game panel
    protected final int groundHeight = 100; // Height of the ground area
    protected Color pipeColor; // Color of the pipe

    /**
     * Constructs a `Pipe` with specified parameters.
     * Initializes the pipe's position, dimensions, speed, and color.
     *
     * @param startX      The initial horizontal position of the pipe.
     * @param panelHeight The height of the game panel.
     * @param gapHeight   The height of the gap between the top and bottom pipes.
     * @param speed       The horizontal movement speed of the pipe.
     * @param color       The color of the pipe (default is green if null).
     */
    public Pipe(int startX, int panelHeight, int gapHeight, int speed, Color color) {
        super(startX, panelHeight); // Satisfy inheritance from `ClassicModePipe`
        this.x = startX;
        this.panelHeight = panelHeight;
        this.gapHeight = gapHeight;
        this.speed = speed;
        this.pipeColor = (color != null) ? color : Color.green;

        int maxGapTop = panelHeight - groundHeight - gapHeight; // Maximum vertical position for the gap
        this.gapY = (int) (Math.random() * (maxGapTop - 50)) + 50; // Randomize gap position within bounds
    }

    /**
     * Draws the pipe on the game panel.
     * Includes both the top and bottom pipes with a gradient for a realistic appearance.
     *
     * @param g The `Graphics` object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Gradient for realistic pipe appearance
        GradientPaint gradient = new GradientPaint(x, 0, new Color(100, 220, 100), x + width, 0, new Color(60, 180, 60));
        g2.setPaint(gradient);

        // Top pipe
        g2.fillRoundRect(x, 0, width, gapY, 20, 20);
        g2.setColor(new Color(40, 120, 40));
        g2.fillRect(x, gapY - 10, width, 10); // Top edge

        // Bottom pipe
        g2.setPaint(gradient);
        int bottomPipeHeight = panelHeight - groundHeight - (gapY + gapHeight);
        g2.fillRoundRect(x, gapY + gapHeight, width, bottomPipeHeight, 20, 20);
        g2.setColor(new Color(40, 120, 40));
        g2.fillRect(x, gapY + gapHeight, width, 10); // Bottom edge
    }

    /**
     * Checks if the pipe collides with the given bird.
     * Collision is detected if the bird intersects with either the top or bottom pipe.
     *
     * @param bird The `Bird` object to check for collision.
     * @return `true` if the bird collides with the pipe, otherwise `false`.
     */
    @Override
    public boolean collidesWith(Bird bird) {
        Rectangle birdRect = bird.getBounds();
        Rectangle topPipe = new Rectangle(x, 0, width, gapY);
        Rectangle bottomPipe = new Rectangle(x, gapY + gapHeight, width, panelHeight - groundHeight - (gapY + gapHeight));
        return birdRect.intersects(topPipe) || birdRect.intersects(bottomPipe);
    }

    /**
     * Checks if the pipe has moved off the screen.
     *
     * @return `true` if the pipe is completely off the screen, otherwise `false`.
     */
    @Override
    public boolean isOffScreen() {
        return x + width < 0;
    }

    /**
     * Gets the current horizontal position of the pipe.
     *
     * @return The `x` coordinate of the pipe.
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Updates the pipe's position and behavior.
     * This method must be implemented by subclasses to define specific movement logic.
     */
    @Override
    public abstract void update();
}