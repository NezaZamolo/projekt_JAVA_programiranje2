package flappybird;

/**
 * Represents a score entry in the Flappy Bird game leaderboard.
 * Each entry contains the player's name and their score.
 */
public class ScoreEntry {
    public String name; // The name of the player
    public int score;   // The score achieved by the player

    /**
     * Constructs a `ScoreEntry` with the specified player name and score.
     *
     * @param name  The name of the player.
     * @param score The score achieved by the player.
     */
    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }
}