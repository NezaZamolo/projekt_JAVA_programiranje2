package flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Abstract base class for all game panel levels.
 * Handles shared logic for rendering, updates, input, and collision detection.
 */
public abstract class GamePanel extends JPanel implements ActionListener, KeyListener {

    // Constants for panel dimensions and ground height
    protected static final int WIDTH = 400;
    protected static final int HEIGHT = 600;
    protected static final int GROUND_HEIGHT = 100;

    // Image for displaying lives
    private static final Image heartImage = new ImageIcon("media/heart.png").getImage();

    // Game state variables
    protected Timer timer; // Timer for game updates
    protected Bird bird; // The bird object controlled by the player
    protected ArrayList<ClassicModePipe> pipes; // List of pipes in the game
    protected boolean gameStarted = false; // Flag to indicate if the game has started
    protected boolean gameOver = false; // Flag to indicate if the game is over
    protected boolean gameWon = false; // Flag to indicate if the game is won
    protected boolean askedToContinue = false; // Flag to prevent multiple "continue" prompts
    protected int score = 0; // Current score
    protected int finishLineX = -1; // X-coordinate of the finish line
    protected int lives = 3; // Number of lives remaining
    protected boolean paused = false; // Flag to indicate if the game is paused
    protected String mode = "Classic"; // Current game mode

    // Variables for collision handling
    private long lastHitTime = 0; // Timestamp of the last collision
    private final int invincibilityDuration = 1000; // Duration of invincibility after a collision (in ms)
    private boolean waitingForResume = false; // Flag to indicate if the game is waiting for resume after a collision

    /**
     * Default constructor for the game panel.
     * Initializes the bird, pipes, and timer.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(this);

        bird = new Bird(100, HEIGHT / 2);
        pipes = new ArrayList<>();

        timer = new Timer(20, this);
        timer.start();
    }

    /**
     * Constructor for the game panel with a starting score.
     * @param startingScore Initial score for the game.
     */
    public GamePanel(int startingScore) {
        this(); // Calls the default constructor
        this.score = startingScore;
    }

    /**
     * Abstract method to get the score required to win the level.
     * @return The score required to win.
     */
    protected abstract int getWinScore();

    /**
     * Abstract method to create a new pipe object.
     * @return A new pipe object.
     */
    protected abstract ClassicModePipe createPipe();

    /**
     * Abstract method to get the panel for the next level.
     * @return The panel for the next level.
     */
    protected abstract JPanel nextLevelPanel();

    /**
     * Handles game updates triggered by the timer.
     * @param e The action event triggered by the timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (paused) return;

        if (waitingForResume || gameOver || !gameStarted) {
            repaint();
            return;
        }

        if (gameStarted && !gameOver && !gameWon) {
            bird.update();
            spawnPipeIfNeeded();
            updatePipes();
            updateFinishLine();
            checkGroundCollision();

            if (gameOver) {
                timer.stop();

                StartPanel.saveScore(mode, score);

                SwingUtilities.invokeLater(() -> {
                    int choice = JOptionPane.showOptionDialog(
                            this,
                            "Game Over! Your score: " + score,
                            "Game Over",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[]{"Back to Menu"},
                            "Back to Menu"
                    );

                    if (choice == 0) {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        if (topFrame instanceof GameFrame gameFrame) {
                            gameFrame.switchPanel(new StartPanel());
                        }
                    }
                });

                return;
            }
        }
        repaint();
    }

    /**
     * Spawns a new pipe if needed based on the current pipe positions.
     */
    private void spawnPipeIfNeeded() {
        if (pipes.isEmpty() || pipes.get(pipes.size() - 1).getX() < 200) {
            pipes.add(createPipe());
        }
    }

    /**
     * Updates the positions of pipes and handles collisions.
     */
    private void updatePipes() {
        Iterator<ClassicModePipe> iter = pipes.iterator();
        while (iter.hasNext()) {
            ClassicModePipe pipe = iter.next();
            pipe.update();

            long now = System.currentTimeMillis();

            if (pipe.collidesWith(bird) && !waitingForResume && (System.currentTimeMillis() - lastHitTime > invincibilityDuration)) {
                SoundPlayer.playSound("hit.wav");
                waitingForResume = true;
                resetBirdOnly();
            }

            if (pipe.isOffScreen()) {
                iter.remove();
                score++;
                SoundPlayer.playSound("point.wav");
                if (score == getWinScore()) {
                    finishLineX = WIDTH;
                }
            }
        }
    }

    /**
     * Updates the position of the finish line and checks for collisions with the bird.
     */
    private void updateFinishLine() {
        if (finishLineX != -1) {
            finishLineX -= 4;
            Rectangle birdBounds = bird.getBounds();
            if (birdBounds.x + 20 >= finishLineX && birdBounds.x <= finishLineX + 60) {
                gameWon = true;
                askToContinue();
            }
        }
    }

    /**
     * Checks if the bird has collided with the ground and updates lives accordingly.
     */
    private void checkGroundCollision() {
        if (bird.getY() > HEIGHT - GROUND_HEIGHT) {
            lives--;
            SoundPlayer.playSound("hit.wav");

            if (lives <= 0) {
                gameOver = true;
                SoundPlayer.playSound("die.wav");
            } else {
                resetBirdOnly();
            }
        }
    }

    /**
     * Prompts the player to continue to the next level if the game is won.
     */
    private void askToContinue() {
        if (askedToContinue) return;
        askedToContinue = true;

        SwingUtilities.invokeLater(() -> {
            SoundPlayer.playSound("swoosh.wav");
            int response = JOptionPane.showConfirmDialog(this, "Continue to the next level?", "You Win!", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.setContentPane(nextLevelPanel());
                topFrame.revalidate();
                topFrame.repaint();
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * Paints the game components on the panel.
     * @param g The graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGround(g);
        drawPipes(g);
        drawFinishLine(g);
        bird.draw(g);
        drawUI(g);

        if (paused) {
            // Draw a translucent overlay
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            // Display "Game Paused" text
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm1 = g.getFontMetrics();
            String pauseText = "Game Paused";
            int x1 = (getWidth() - fm1.stringWidth(pauseText)) / 2;
            int y1 = getHeight() / 2 - 20;
            g.drawString(pauseText, x1, y1);

            // Display "Press P to resume" text
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            FontMetrics fm2 = g.getFontMetrics();
            String resumeText = "Press P to resume";
            int x2 = (getWidth() - fm2.stringWidth(resumeText)) / 2;
            int y2 = y1 + 40;
            g.drawString(resumeText, x2, y2);

            String exitText = "Press ESC to return to menu";
            int x3 = (getWidth() - fm2.stringWidth(exitText)) / 2;
            int y3 = y2 + 30;
            g.drawString(exitText, x3, y3);
        }
    }

    /**
     * Draws the ground at the bottom of the panel.
     * @param g The graphics object used for painting.
     */
    private void drawGround(Graphics g) {
        g.setColor(new Color(245, 180, 80)); // Softer orange color
        g.fillRect(0, HEIGHT - GROUND_HEIGHT, WIDTH, GROUND_HEIGHT);
    }

    /**
     * Draws all pipes on the panel.
     * @param g The graphics object used for painting.
     */
    private void drawPipes(Graphics g) {
        for (ClassicModePipe pipe : pipes) {
            pipe.draw(g);
        }
    }

    /**
     * Draws the finish line on the panel.
     * @param g The graphics object used for painting.
     */
    private void drawFinishLine(Graphics g) {
        if (finishLineX != -1) {
            for (int i = 0; i < HEIGHT - GROUND_HEIGHT; i += 20) {
                g.setColor((i / 20) % 2 == 0 ? new Color(60, 60, 60) : new Color(230, 230, 230));
                g.fillRect(finishLineX, i, 60, 20);
            }
        }
    }

    /**
     * Draws the user interface elements such as score and lives.
     * @param g The graphics object used for painting.
     */
    private void drawUI(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        if (!gameStarted) {
            g.drawString("Press SPACE to start", 50, HEIGHT / 2 - 50);
        }
        if (gameOver) {
            g.drawString("Game Over", 130, HEIGHT / 2);
        }
        g.drawString("Score: " + score, 20, 40);

        int heartSize = 24;
        int spacing = 5;
        int startX = WIDTH - 20 - (lives * (heartSize + spacing));

        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, startX + i * (heartSize + spacing), 15, heartSize, heartSize, null);
        }

        if (waitingForResume) {
            g.drawString("Press SPACE to continue", 70, HEIGHT / 2 + 50);
        }
    }

    /**
     * Handles key press events for controlling the game.
     * @param e The key event triggered by the user.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameStarted) {
                gameStarted = true;
            }
            if (!gameOver && !gameWon) {
                bird.jump();
            } else if (gameOver) {
                resetGame();
            }
        }

        if (waitingForResume && e.getKeyCode() == KeyEvent.VK_SPACE) {
            lives--;

            if (lives <= 0) {
                gameOver = true;
                SoundPlayer.playSound("die.wav");
                StartPanel.saveScore(mode, score);
            }

            lastHitTime = System.currentTimeMillis(); // Update invincibility time
            waitingForResume = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            timer.stop();
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof GameFrame gameFrame) {
                gameFrame.switchPanel(new StartPanel());
            }
            return;
        }
    }

    /**
     * Resets the game state to start a new game.
     */
    private void resetGame() {
        lives = 3;
        waitingForResume = false;
        bird = new Bird(100, HEIGHT / 2);
        pipes.clear();
        score = 0;
        gameOver = false;
        gameStarted = false;
        finishLineX = -1;
        askedToContinue = false;
    }

    /**
     * Resets only the bird's state after a collision.
     */
    private void resetBirdOnly() {
        bird.setVelocity(0);
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}