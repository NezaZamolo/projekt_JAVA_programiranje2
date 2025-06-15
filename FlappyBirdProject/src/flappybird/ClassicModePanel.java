package flappybird;

/**
 * GamePanel for Classic Mode. Uses ClassicModePipe and has no next level.
 */
public class ClassicModePanel extends GamePanel {

    /**
     * Constructs a ClassicModePanel with default settings.
     * Initializes the game mode to "Classic".
     */
    public ClassicModePanel() {
        super();
        this.mode = "Classic";
    }

    /**
     * Constructs a ClassicModePanel with a specified starting score.
     * Initializes the game mode to "Classic".
     *
     * @param startingScore The initial score for the game.
     */
    public ClassicModePanel(int startingScore) {
        super(startingScore);
        this.mode = "Classic";
    }

    /**
     * Returns the score required to win the level.
     * In Classic Mode, there is no win condition, so the maximum integer value is returned.
     *
     * @return The maximum integer value representing no win condition.
     */
    @Override
    protected int getWinScore() {
        return Integer.MAX_VALUE;
    }

    /**
     * Creates a new pipe for Classic Mode.
     *
     * @return A new instance of ClassicModePipe.
     */
    @Override
    protected ClassicModePipe createPipe() {
        return new ClassicModePipe(WIDTH, HEIGHT);
    }

    /**
     * Returns the panel for the next level.
     * In Classic Mode, there is no next level, so null is returned.
     *
     * @return null, indicating no next level.
     */
    @Override
    protected javax.swing.JPanel nextLevelPanel() {
        return null;
    }
}