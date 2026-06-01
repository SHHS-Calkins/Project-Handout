import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

/**
 * Controls the game logic and simulation
 */
public class GameController {
    private World world;
    private GamePanel gamePanel;
    private boolean isRunning;
    private boolean isPaused;
    private GameThread gameThread;
    public int simulationSpeed; // Milliseconds per turn (public for UI access)
    
    public GameController(World world, GamePanel gamePanel) {
        this.world = world;
        this.gamePanel = gamePanel;
        this.isRunning = false;
        this.isPaused = false;
        this.simulationSpeed = 500; // Default: 500ms per turn
    }
    
    /**
     * Start the game simulation
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
            isPaused = false;
            gameThread = new GameThread();
            gameThread.execute();
        }
    }
    
    /**
     * Stop the game simulation
     */
    public void stop() {
        isRunning = false;
        if (gameThread != null) {
            gameThread.cancel(true);
        }
    }
    
    /**
     * Pause or resume the simulation
     */
    public void togglePause() {
        isPaused = !isPaused;
        gamePanel.setPaused(isPaused);
        gamePanel.repaint();
    }
    
    /**
     * Execute a single simulation turn manually
     */
    public void stepOnce() {
        world.simulateTurn();
        gamePanel.incrementTurn();
        gamePanel.repaint();
    }
    
    /**
     * Set simulation speed
     */
    public void setSimulationSpeed(int delayMs) {
        this.simulationSpeed = Math.max(100, delayMs);
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    /**
     * Inner class that runs the simulation in a background thread
     */
    private class GameThread extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            while (isRunning && !isCancelled()) {
                if (!isPaused) {
                    // Run one simulation turn
                    world.simulateTurn();
                    gamePanel.incrementTurn();
                    gamePanel.repaint();
                }
                
                // Sleep for the configured simulation speed
                Thread.sleep(simulationSpeed);
            }
            return null;
        }
        
        @Override
        protected void done() {
            isRunning = false;
        }
    }
}
