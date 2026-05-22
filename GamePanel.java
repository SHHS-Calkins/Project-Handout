import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Main game rendering panel - displays the world and agents visually
 */
public class GamePanel extends JPanel {
    public World world; // Public for window access
    private int cellSize;
    public GameController gameController; // Public for window access
    public int turn; // Public for window access
    private boolean isPaused;
    
    private static final Color EMPTY_COLOR = new Color(240, 240, 240);
    private static final Color FOOD_COLOR = new Color(34, 139, 34);
    private static final Color OBSTACLE_COLOR = new Color(105, 105, 105);
    private static final Color GRID_COLOR = new Color(200, 200, 200);
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    
    // Agent colors for visual distinction
    private static final Color HUNTER_COLOR = new Color(220, 20, 60);
    private static final Color GATHERER_COLOR = new Color(30, 144, 255);
    private static final Color SCAVENGER_COLOR = new Color(255, 165, 0);
    private static final Color DEFAULT_AGENT_COLOR = new Color(100, 100, 100);
    
    public GamePanel(World world, GameController gameController) {
        this.world = world;
        this.gameController = gameController;
        this.cellSize = 40;
        this.turn = 0;
        this.isPaused = false;
        
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw the world grid
        drawWorld(g2d);
        
        // Draw UI overlay
        drawUI(g2d);
    }
    
    private void drawWorld(Graphics2D g2d) {
        int worldWidth = world.getWidth();
        int worldHeight = world.getLength();
        
        // Draw cells
        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                int screenX = x * cellSize;
                int screenY = y * cellSize;
                
                String cellContent = world.getCell(x, y);
                
                // Draw cell background
                g2d.setColor(EMPTY_COLOR);
                g2d.fillRect(screenX, screenY, cellSize, cellSize);
                
                // Draw cell content
                if (cellContent.equals("F")) {
                    drawFood(g2d, screenX, screenY);
                } else if (cellContent.equals("#")) {
                    drawObstacle(g2d, screenX, screenY);
                } else if (!cellContent.equals(" ")) {
                    // It's an agent
                    drawAgent(g2d, screenX, screenY, cellContent);
                }
                
                // Draw grid lines
                g2d.setColor(GRID_COLOR);
                g2d.setStroke(new BasicStroke(0.5f));
                g2d.drawRect(screenX, screenY, cellSize, cellSize);
            }
        }
    }
    
    private void drawFood(Graphics2D g2d, int x, int y) {
        g2d.setColor(FOOD_COLOR);
        int padding = 8;
        int size = cellSize - (2 * padding);
        g2d.fillOval(x + padding, y + padding, size, size);
        g2d.setColor(new Color(0, 100, 0));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(x + padding, y + padding, size, size);
    }
    
    private void drawObstacle(Graphics2D g2d, int x, int y) {
        g2d.setColor(OBSTACLE_COLOR);
        g2d.fillRect(x + 2, y + 2, cellSize - 4, cellSize - 4);
        g2d.setColor(new Color(60, 60, 60));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x + 2, y + 2, cellSize - 4, cellSize - 4);
    }
    
    private void drawAgent(Graphics2D g2d, int x, int y, String agentName) {
        Agent agent = world.getAgent(agentName);
        if (agent == null) return;
        
        Color agentColor = getAgentColor(agent);
        
        // Draw agent circle
        int padding = 4;
        int size = cellSize - (2 * padding);
        g2d.setColor(agentColor);
        g2d.fillOval(x + padding, y + padding, size, size);
        
        // Draw border
        g2d.setColor(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x + padding, y + padding, size, size);
        
        // Draw agent symbol/initial
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        String symbol = agent.getSymbol();
        int textX = x + (cellSize - fm.stringWidth(symbol)) / 2;
        int textY = y + ((cellSize - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(symbol, textX, textY);
        
        // Draw health indicator
        int healthBarWidth = cellSize - (2 * padding);
        int healthBarHeight = 3;
        int healthX = x + padding;
        int healthY = y + cellSize - padding - healthBarHeight;
        
        g2d.setColor(new Color(100, 100, 100));
        g2d.fillRect(healthX, healthY, healthBarWidth, healthBarHeight);
        
        float healthPercent = (float) agent.getHp() / 100;
        Color healthColor = healthPercent > 0.5f ? new Color(50, 200, 50) : 
                          healthPercent > 0.25f ? new Color(255, 200, 0) : 
                          new Color(255, 50, 50);
        g2d.setColor(healthColor);
        g2d.fillRect(healthX, healthY, (int)(healthBarWidth * healthPercent), healthBarHeight);
    }
    
    private Color getAgentColor(Agent agent) {
        if (agent instanceof Hunter) {
            return HUNTER_COLOR;
        } else if (agent instanceof Gatherer) {
            return GATHERER_COLOR;
        } else if (agent instanceof Scavenger) {
            return SCAVENGER_COLOR;
        }
        return DEFAULT_AGENT_COLOR;
    }
    
    private void drawUI(Graphics2D g2d) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        // Draw semi-transparent background for UI
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, panelHeight - 100, panelWidth, 100);
        
        // Draw text information
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        
        int x = 10;
        int y = panelHeight - 80;
        
        g2d.drawString("Turn: " + turn, x, y);
        g2d.drawString("Agents: " + world.getAgentCount(), x + 150, y);
        g2d.drawString("Status: " + (isPaused ? "PAUSED" : "RUNNING"), x + 300, y);
        
        // Draw legend
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        y += 20;
        drawColorBox(g2d, x, y, HUNTER_COLOR);
        g2d.drawString("Hunter", x + 25, y + 12);
        
        drawColorBox(g2d, x + 120, y, GATHERER_COLOR);
        g2d.drawString("Gatherer", x + 145, y + 12);
        
        drawColorBox(g2d, x + 260, y, SCAVENGER_COLOR);
        g2d.drawString("Scavenger", x + 285, y + 12);
        
        drawColorBox(g2d, x + 420, y, FOOD_COLOR);
        g2d.drawString("Food", x + 445, y + 12);
    }
    
    private void drawColorBox(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x, y, 12, 12);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(x, y, 12, 12);
    }
    
    public void incrementTurn() {
        turn++;
    }
    
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
    
    public void setCellSize(int size) {
        this.cellSize = size;
        repaint();
    }
    
    public int getTurn() {
        return turn;
    }
}
