/**
 * Scavenger agent - wanderer that eats anything it finds
 * Scavengers are opportunistic and adaptable
 */
public class Scavenger extends Agent {
    private int itemsConsumed;
    private int mutationRate;
    
    /**
     * Constructor for customizable scavenger
     */
    public Scavenger(String name, String symbol, int hp, int energy) {
        super(name, symbol, hp, energy);
        this.itemsConsumed = 0;
        this.mutationRate = 10; // % chance to adapt behavior
    }

    /**
     * Default scavenger constructor
     */
    public Scavenger() {
        super("Scavenger", "S", 90, 55);
        this.itemsConsumed = 0;
        this.mutationRate = 10;
    }
    
    /**
     * Scavenger autonomous behavior - wander and eat when found
     */
    @Override
    public void act() {
        if (!alive) return;
        
        // Lose energy each turn (moderate)
        loseEnergy(2);
        
        // Check current location for food
        if (world != null && world.getCell(x, y).equals("F")) {
            eatFood();
            itemsConsumed++;
        }
        
        // Decide whether to search or wander
        if (energy < 25) {
            // Low energy - search more systematically
            if (!searchNearby()) {
                moveRandomly();
            }
        }
        else if (Math.random() < 0.7) {
            // Normal wandering
            moveRandomly();
        }
        else {
            // Occasionally search
            searchNearby();
        }
    }
    
    /**
     * Search nearby locations for food
     */
    private boolean searchNearby() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, 
                             {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        
        for (int[] dir : directions) {
            int checkX = x + dir[0];
            int checkY = y + dir[1];
            
            if (world.isValidPosition(checkX, checkY)) {
                String cell = world.getCell(checkX, checkY);
                if (cell.equals("F")) {
                    move(dir[0], dir[1]);
                    eatFood();
                    itemsConsumed++;
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Mutate - slightly change behavior
     */
    public void mutate() {
        if (Math.random() < 0.5) {
            maxEnergy += 5;
        } else {
            maxHp -= 5;
        }
    }
    
    public int getItemsConsumed() {
        return itemsConsumed;
    }
    
    public void setItemsConsumed(int amount) {
        this.itemsConsumed = amount;
    }
    
    public int getMutationRate() {
        return mutationRate;
    }
    
    public void setMutationRate(int rate) {
        this.mutationRate = rate;
    }

    @Override
    public String toString() {
       return super.toString() + " - Items Consumed: " + itemsConsumed + " - Mutation Rate: " + mutationRate + "%";
    }
}
