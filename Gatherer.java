/**
 * Gatherer agent - peaceful collector that focuses on gathering food
 * Gatherers are efficient foragers with good vision
 */
public class Gatherer extends Agent {
    private int foodCollected;
    private int visionRange;
    
    /**
     * Constructor for customizable gatherer
     */
    public Gatherer(String name, String symbol, int hp, int energy) {
        super(name, symbol, hp, energy);
        this.foodCollected = 0;
        this.visionRange = 5;
    }

    /**
     * Default gatherer constructor
     */
    public Gatherer() {
        super("Gatherer", "G", 100, 70);
        this.foodCollected = 0;
        this.visionRange = 5;
    }
    
    /**
     * Gatherer autonomous behavior - search for and collect food efficiently
     */
    @Override
    public void act() {
        if (!alive) return;
        
        // Lose energy each turn (less than hunters)
        loseEnergy(1);
        
        // Always search for food with priority based on energy
        if (energy < 30) {
            searchForFoodAggressive();
        } 
        else if (!searchForFood()) {
            moveRandomly();
        }
    }
    
    /**
     * Search for food within range and move toward it
     */
    private boolean searchForFood() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : directions) {
            for (int i = 1; i <= visionRange; i++) {
                int checkX = x + (dir[0] * i);
                int checkY = y + (dir[1] * i);
                
                if (world.isValidPosition(checkX, checkY)) {
                    String cell = world.getCell(checkX, checkY);
                    if (cell.equals("F")) {
                        // Move toward food
                        if (move(dir[0], dir[1])) {
                            eatFood();
                            foodCollected++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Aggressive food search when energy is low
     */
    private boolean searchForFoodAggressive() {
        // Try all four directions first
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : directions) {
            if (move(dir[0], dir[1])) {
                if (world.getCell(x, y).equals("F")) {
                    eatFood();
                    foodCollected++;
                }
                return true;
            }
        }
        return false;
    }
    
    public int getFoodCollected() {
        return foodCollected;
    }
    
    public void setFoodCollected(int amount) {
        this.foodCollected = amount;
    }
    
    public int getVisionRange() {
        return visionRange;
    }
    
    public void setVisionRange(int range) {
        this.visionRange = range;
    }

    @Override
    public String toString() {
       return super.toString() + " - Food Collected: " + foodCollected + " - Vision: " + visionRange;
    }
}
