import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private String[][] area;
    private HashMap<String, Agent> livingAgents;
    private int length;
    private int width;
    
    // Cell type constants
    private static final String EMPTY = " ";
    private static final String FOOD = "F";
    private static final String OBSTACLE = "#";
    
    /**
     * Default constructor creates a 12x12 world
     */
    public World() {
        this(12, 12);
    }
    
    /**
     * Constructor that creates a world with specified dimensions
     * Randomly populates the world with food, obstacles, and empty space
     */
    public World(int length, int width) {
        this.length = length;
        this.width = width;
        this.area = new String[length][width];
        this.livingAgents = new HashMap<>();
        
        // Initialize the world with random cells
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < width; j++) {
                int random = (int) (Math.random() * 10);
                
                if(random < 3) {
                    area[i][j] = FOOD;
                } 
                else if(random < 5) {
                    area[i][j] = OBSTACLE;
                } 
                else {
                    area[i][j] = EMPTY;
                }
            }
        }
    }
    
    /**
     * Adds an agent to the world at the specified coordinates
     */
    public void addAgent(Agent a, int x, int y) {
        // Validate coordinates
        if(isValidPosition(x, y)) {
            // Store agent in map
            livingAgents.put(a.getName(), a);
            
            // Notify agent it's been placed in the world
            a.onPlacedInWorld(this, x, y);
            
            // Place agent symbol in the world
            area[y][x] = a.getSymbol();
        }
        else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
        }
    }
    
    /**
     * Removes an agent from the world
     */
    public void removeAgent(String agentName) {
        if(livingAgents.containsKey(agentName)) {
            livingAgents.remove(agentName);
            // Replace agent's cell with empty space
            for(int i = 0; i < length; i++) {
                for(int j = 0; j < width; j++) {
                    if(area[i][j].equals(agentName)) {
                        area[i][j] = EMPTY;
                    }
                }
            }
        }
    }
    
    /**
     * Moves an agent from one position to another
     */
    public void moveAgent(String agentName, int newX, int newY) {
        if(livingAgents.containsKey(agentName) && isValidPosition(newX, newY)) {
            // Remove agent from current position
            for(int i = 0; i < length; i++) {
                for(int j = 0; j < width; j++) {
                    if(area[i][j].equals(agentName)) {
                        area[i][j] = EMPTY;
                    }
                }
            }
            // Place agent at new position
            area[newY][newX] = livingAgents.get(agentName).getSymbol();
        }
    }
    
    /**
     * Returns list of all agents in the world
     */
    public String getAgents() {
        String s = "";
        for (String agentName : livingAgents.keySet()) {
            s += livingAgents.get(agentName).toString() + "\n";
        }
        return s;
    }
    
    /**
     * Gets a specific agent by name
     */
    public Agent getAgent(String agentName) {
        return livingAgents.get(agentName);
    }
    
    /**
     * Returns the content at a specific position
     */
    public String getCell(int x, int y) {
        if(isValidPosition(x, y)) {
            return area[y][x];
        }
        return null;
    }
    
    /**
     * Sets the content of a cell
     */
    public void setCell(int x, int y, String content) {
        if(isValidPosition(x, y)) {
            area[y][x] = content;
        }
    }
    
    /**
     * Validates if a position is within world bounds
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < length;
    }
    
    /**
     * Displays the world in the console with borders and legend
     */
    public void display() {
        System.out.println("\n" + "═".repeat(width * 2 + 2));
        
        for(int i = 0; i < length; i++) {
            System.out.print("║");
            for(int j = 0; j < width; j++) {
                String cell = area[i][j];
                if(cell.equals(EMPTY)) {
                    System.out.print("  ");
                }
                else if(cell.equals(FOOD)) {
                    System.out.print("🌾 ");
                }
                else if(cell.equals(OBSTACLE)) {
                    System.out.print("🧱 ");
                }
                else {
                    // Agent symbol
                    System.out.print(cell + " ");
                }
            }
            System.out.println("║");
        }
        
        System.out.println("═".repeat(width * 2 + 2));
        System.out.println("\nLegend: 🌾=Food | 🧱=Obstacle | Agent Symbols | Space=Empty");
        System.out.println("Agents: " + livingAgents.size() + " | Dimensions: " + width + "x" + length);
    }
    
    /**
     * Displays a simple text-based version of the world
     */
    public void displaySimple() {
        System.out.println();
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < width; j++) {
                String cell = area[i][j];
                System.out.print(cell);
            }
            System.out.println();
        }
    }
    
    /**
     * Gets the world dimensions
     */
    public int getLength() { return length; }
    public int getWidth() { return width; }
    
    /**
     * Gets the number of agents in the world
     */
    public int getAgentCount() {
        return livingAgents.size();
    }
    
    /**
     * Execute one simulation turn - all agents act autonomously
     */
    public void simulateTurn() {
        // Get list of agent names to avoid concurrent modification
        java.util.ArrayList<String> agentNames = new java.util.ArrayList<>(livingAgents.keySet());
        
        // Each agent acts
        for (String agentName : agentNames) {
            Agent agent = livingAgents.get(agentName);
            if (agent != null && agent.isAlive()) {
                agent.act();
            }
        }
        
        // Remove dead agents
        java.util.ArrayList<String> deadAgents = new java.util.ArrayList<>();
        for (String agentName : livingAgents.keySet()) {
            Agent agent = livingAgents.get(agentName);
            if (!agent.isAlive()) {
                deadAgents.add(agentName);
            }
        }
        
        // Clean up dead agents
        for (String agentName : deadAgents) {
            removeAgent(agentName);
        }
    }
    
    /**
     * Simulate multiple turns
     */
    public void simulate(int turns) {
        for (int i = 0; i < turns; i++) {
            simulateTurn();
        }
    }
    
    /**
     * Get statistics about the world
     */
    public void displayStats() {
        System.out.println("\n=== WORLD STATISTICS ===");
        System.out.println("Dimensions: " + width + "x" + length);
        System.out.println("Active Agents: " + livingAgents.size());
        System.out.println("\nAgent Details:");
        for (Agent agent : livingAgents.values()) {
            System.out.println("  " + agent.toString());
        }
        System.out.println("========================\n");
    }
}
