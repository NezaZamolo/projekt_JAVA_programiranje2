package flappybird;

/**
 * Represents the Extreme Mode game panel, which is the final level of the game.
 * This panel uses `ExtremeModePipe` for obstacles and does not have a next level.
 */
public class ExtremeModePanel extends GamePanel {

    /**
     * Constructs an `ExtremeModePanel` with default settings.
     * Initializes the game mode to "Extreme".
     */
    public ExtremeModePanel() {
        super();
        this.mode = "Extreme";
    }

    /**
     * Constructs an `ExtremeModePanel` with a specified starting score.
     * Initializes the game mode to "Extreme".
     *
     * @param startingScore The initial score for the game.
     */
    public ExtremeModePanel(int startingScore) {
        super(startingScore);
        this.mode = "Extreme";
    }

    /**
     * Returns the score required to win the level.
     * In Extreme Mode, there is no win condition, so the maximum integer value is returned.
     *
     * @return The maximum integer value representing no win condition.
     */
    @Override
    protected int getWinScore() {
        return Integer.MAX_VALUE;
    }

    /**
     * Creates a new pipe for Extreme Mode.
     *
     * @return A new instance of `ExtremeModePipe`.
     */
    @Override
    protected ExtremeModePipe createPipe() {
        return new ExtremeModePipe(WIDTH, HEIGHT);
    }

    /**
     * Returns the panel for the next level.
     * Since this is the final level, no next level panel exists.
     *
     * @return `null`, indicating no next level.
     */
    @Override
    protected javax.swing.JPanel nextLevelPanel() {
        return null; // This is the final level, so no next level panel.
    }
}