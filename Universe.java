import javax.swing.*;

/**
 * Main entry point for the Clanker World game
 */
public class Universe {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create world with default or random size
            World world = new World(14, 16);
            
            // Create and setup the game window
            GameWindow gameWindow = new GameWindow(world);
            
            // Initialize agents
            gameWindow.initializeAgents();
            
            // Make window visible
            gameWindow.setVisible(true);
        });
    }
}
