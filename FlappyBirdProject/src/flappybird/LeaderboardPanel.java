package flappybird;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Represents the leaderboard panel in the Flappy Bird game.
 * This panel displays the scores for different game modes and provides a button to return to the main menu.
 */
public class LeaderboardPanel extends JPanel {

    private final Map<String, List<ScoreEntry>> scores; // Stores scores for each game mode

    /**
     * Constructs a `LeaderboardPanel` and initializes its layout and components.
     * The panel displays the leaderboard title, scores for each mode, and a back button.
     */
    public LeaderboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.cyan);

        scores = StartPanel.loadAllScores(); // Load scores from the start panel

        // Title label
        JLabel title = new JLabel("Leaderboard");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Center panel for scores
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(10, 40, 10, 40));

        for (String mode : scores.keySet()) {
            // Label for each game mode
            JLabel modeLabel = new JLabel(mode + " Mode:");
            modeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            center.add(modeLabel);

            for (ScoreEntry entry : scores.get(mode)) {
                // Label for each score entry
                JLabel scoreLabel = new JLabel("  " + entry.name + " — " + entry.score);
                scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                center.add(scoreLabel);
            }

            center.add(Box.createVerticalStrut(10)); // Add spacing between modes
        }

        add(center, BorderLayout.CENTER);

        // Back button to return to the main menu
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof GameFrame gameFrame) {
                gameFrame.switchPanel(new StartPanel()); // Switch to the start panel
            }
        });

        // Bottom panel for the back button
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(backButton);
        bottom.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(bottom, BorderLayout.SOUTH);
    }
}