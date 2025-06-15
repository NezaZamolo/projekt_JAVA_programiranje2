package flappybird;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents the main game window for Flappy Bird.
 * This class initializes the game frame and provides functionality to switch between different panels.
 */
public class GameFrame extends JFrame {

    /**
     * Constructs a `GameFrame` and sets up the main game window.
     * The window is titled "Flappy Bird", non-resizable, and starts with the `StartPanel`.
     */
    public GameFrame() {
        this.setTitle("Flappy Bird"); // Set the title of the game window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application exits on close
        this.setResizable(false); // Prevent resizing of the game window
        switchPanel(new StartPanel()); // Display the initial panel
        this.pack(); // Adjust the window size to fit its components
        this.setLocationRelativeTo(null); // Center the window on the screen
        this.setVisible(true); // Make the window visible
    }

    /**
     * Replaces the current content pane with the specified panel.
     * This method ensures the new panel is displayed and focused.
     *
     * @param panel The new panel to display in the game window.
     */
    public void switchPanel(JPanel panel) {
        this.getContentPane().removeAll(); // Remove all components from the current content pane
        this.setContentPane(panel); // Set the new panel as the content pane
        this.revalidate(); // Refresh the layout to reflect changes
        this.repaint(); // Redraw the window
        panel.requestFocusInWindow(); // Request focus for the new panel
    }
}