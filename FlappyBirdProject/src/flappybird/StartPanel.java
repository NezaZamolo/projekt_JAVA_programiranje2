package flappybird;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.List;

/**
 * Represents the start panel of the Flappy Bird game.
 * This panel serves as the main menu where players can select game modes, view the leaderboard,
 * and see animated elements like the bird and high scores.
 */
public class StartPanel extends JPanel {

    private final int WIDTH = 400; // Width of the panel
    private final int HEIGHT = 600; // Height of the panel
    private Timer timer; // Timer for animating the bird
    private int birdY = HEIGHT / 2; // Vertical position of the bird
    private int birdVel = 2; // Velocity of the bird's movement
    private int birdDir = 1; // Direction of the bird's movement
    private final int birdSize = 50; // Size of the bird
    private static final Image birdImage; // Image of the bird

    static {
        birdImage = new ImageIcon("media/bird.png").getImage(); // Load the bird image
    }

    private final Map<String, Integer> highScores = new LinkedHashMap<>(); // High scores for each mode

    /**
     * Constructs the `StartPanel` and initializes its layout, components, and animations.
     */
    public StartPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        // Center panel for title, subtitle, and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Flappy Bird");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(new Color(30, 144, 255));
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(title);

        JLabel subtitle = new JLabel("Choose your mode");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 22));
        subtitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        centerPanel.add(subtitle);

        // Add buttons for different game modes and leaderboard
        addButton(centerPanel, "Classic Mode", ClassicModePanel::new);
        addButton(centerPanel, "Hard Mode", HardModePanel::new);
        addButton(centerPanel, "Extreme Mode", ExtremeModePanel::new);
        addButton(centerPanel, "View Leaderboard", LeaderboardPanel::new);

        centerPanel.add(Box.createVerticalStrut(20));
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for instructions
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JLabel instructions = new JLabel("P = pause/resume   |   ESC = back to menu");
        instructions.setFont(new Font("Arial", Font.PLAIN, 14));
        instructions.setBorder(new EmptyBorder(0, 10, 0, 0));
        bottomPanel.add(instructions, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);

        loadHighScores(); // Load high scores from file

        // Timer for animating the bird
        timer = new Timer(30, e -> {
            birdY += birdVel * birdDir;
            if (birdY < HEIGHT / 2 - 40 || birdY > HEIGHT / 2 + 40) birdDir *= -1;
            repaint();
        });
        timer.start();
    }

    /**
     * Adds a button to the specified panel with the given label and action.
     *
     * @param panel    The panel to which the button will be added.
     * @param label    The text displayed on the button.
     * @param supplier A supplier that provides the panel to switch to when the button is clicked.
     */
    private void addButton(JPanel panel, String label, Supplier<JPanel> supplier) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 45));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(Color.DARK_GRAY);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(100, 100, 100), 1, true),
                new EmptyBorder(10, 25, 10, 25)
        ));
        button.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof GameFrame gameFrame) {
                gameFrame.switchPanel(supplier.get());
            }
            timer.stop();
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);
    }

    /**
     * Loads high scores from the file and updates the `highScores` map.
     */
    private void loadHighScores() {
        Map<String, List<ScoreEntry>> all = loadAllScores();
        for (Map.Entry<String, List<ScoreEntry>> entry : all.entrySet()) {
            List<ScoreEntry> scores = entry.getValue();
            if (!scores.isEmpty()) {
                highScores.put(entry.getKey(), scores.get(0).score); // Highest score
            }
        }
    }

    /**
     * Saves a new score for the specified game mode.
     * Prompts the user for their name and updates the scores file.
     *
     * @param mode  The game mode for which the score is being saved.
     * @param score The score to save.
     */
    public static void saveScore(String mode, int score) {
        Map<String, List<ScoreEntry>> allScores = loadAllScores();

        String name = JOptionPane.showInputDialog(null, "Enter your name:", "New High Score!", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) name = "Anonymous";

        List<ScoreEntry> list = allScores.getOrDefault(mode, new ArrayList<>());
        list.add(new ScoreEntry(name, score));
        list.sort((a, b) -> b.score - a.score);
        if (list.size() > 5) list = list.subList(0, 5);
        allScores.put(mode, list);

        try (PrintWriter pw = new PrintWriter("scores.txt")) {
            for (Map.Entry<String, List<ScoreEntry>> entry : allScores.entrySet()) {
                pw.println(entry.getKey() + ":");
                for (ScoreEntry s : entry.getValue()) {
                    pw.println(s.name + ":" + s.score);
                }
            }
        } catch (IOException ignored) {}
    }

    /**
     * Loads all scores from the scores file.
     *
     * @return A map containing scores for each game mode.
     */
    public static Map<String, List<ScoreEntry>> loadAllScores() {
        Map<String, List<ScoreEntry>> map = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("scores.txt"))) {
            String line;
            String currentMode = null;
            while ((line = br.readLine()) != null) {
                if (line.endsWith(":")) {
                    currentMode = line.replace(":", "").trim();
                    map.put(currentMode, new ArrayList<>());
                } else if (currentMode != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        map.get(currentMode).add(new ScoreEntry(parts[0].trim(), Integer.parseInt(parts[1].trim())));
                    }
                }
            }
        } catch (Exception ignored) {}
        return map;
    }

    /**
     * Paints the components of the panel, including the background, animated bird, and high scores.
     *
     * @param g The `Graphics` object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250), 0, HEIGHT, new Color(173, 216, 230));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Animated bird (image)
        if (birdImage != null) {
            g2.drawImage(birdImage, 25, birdY, birdSize, birdSize, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillOval(25, birdY, birdSize, birdSize);
        }

        // High score display (only if there is at least one score)
        if (!highScores.isEmpty()) {
            g2.setColor(new Color(255, 255, 255, 200));
            g2.fillRoundRect(75, HEIGHT - 170, 250, 100, 20, 20);

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            int y = HEIGHT - 140;
            for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
                g2.drawString(entry.getKey() + " High Score: " + entry.getValue(), 90, y);
                y += 22;
            }
        }
    }
}