/**
 * Hunter agent - actively hunts for food and other targets
 * Hunters are aggressive and have high damage
 */
public class Hunter extends Agent {
    private int damage;
    private int hunterRange;
    
    /**
     * Constructor for customizable hunter
     */
    public Hunter(String name, String symbol, int hp, int energy, int damage) {
        super(name, symbol, hp, energy);
        this.damage = damage;
        this.hunterRange = 3;
    }

    /**
     * Default hunter constructor
     */
    public Hunter() {
        super("Hunter", "H", 120, 60);
        this.damage = 15;
        this.hunterRange = 3;
    }
    
    /**
     * Hunter autonomous behavior - search for and eat food
     */
    @Override
    public void act() {
        if (!alive) return;
        
        // Lose energy each turn
        loseEnergy(2);
        
        // If low on energy, search for food aggressively
        if (energy < 20) {
            searchForFood();
        }
        // If hungry, search for food
        else if (energy < 40) {
            if (!searchForFood()) {
                moveRandomly();
            }
        }
        // Otherwise patrol randomly
        else {
            moveRandomly();
        }
    }
    
    /**
     * Search for food within range and move toward it
     */
    private boolean searchForFood() {
        // Look in all directions for food
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : directions) {
            for (int i = 1; i <= hunterRange; i++) {
                int checkX = x + (dir[0] * i);
                int checkY = y + (dir[1] * i);
                
                if (world.isValidPosition(checkX, checkY)) {
                    String cell = world.getCell(checkX, checkY);
                    if (cell.equals("F")) {
                        // Move toward food
                        if (move(dir[0], dir[1])) {
                            eatFood();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Hunter attack ability
     */
    public void attack(Agent target) {
        if (target != null && world != null) {
            target.takeDamage(damage);
        }
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public int getHunterRange() {
        return hunterRange;
    }
    
    public void setHunterRange(int range) {
        this.hunterRange = range;
    }

    @Override
    public String toString() {
       return super.toString() + " - Damage: " + damage + " - Range: " + hunterRange;
    }
}
