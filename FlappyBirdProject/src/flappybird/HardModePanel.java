package flappybird;

/**
 * Represents the Hard Mode game panel in the Flappy Bird game.
 * This panel uses `HardModePipe` for obstacles and does not have a next level.
 */
public class HardModePanel extends GamePanel {

    /**
     * Constructs a `HardModePanel` with default settings.
     * Initializes the game mode to "Hard".
     */
    public HardModePanel() {
        super();
        this.mode = "Hard";
    }

    /**
     * Constructs a `HardModePanel` with a specified starting score.
     * Initializes the game mode to "Hard".
     *
     * @param startingScore The initial score for the game.
     */
    public HardModePanel(int startingScore) {
        super(startingScore);
        this.mode = "Hard";
    }

    /**
     * Returns the score required to win the level.
     * In Hard Mode, there is no win condition, so the maximum integer value is returned.
     *
     * @return The maximum integer value representing no win condition.
     */
    @Override
    protected int getWinScore() {
        return Integer.MAX_VALUE;
    }

    /**
     * Creates a new pipe for Hard Mode.
     *
     * @return A new instance of `HardModePipe`.
     */
    @Override
    protected HardModePipe createPipe() {
        return new HardModePipe(WIDTH, HEIGHT);
    }

    /**
     * Returns the panel for the next level.
     * In Hard Mode, there is no next level, so `null` is returned.
     *
     * @return `null`, indicating no next level.
     */
    @Override
    protected javax.swing.JPanel nextLevelPanel() {
        return null;
    }
}