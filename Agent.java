/**
 * Abstract parent class for all agents in the world
 * Agents can move, eat, interact with the environment, and have autonomous behavior
 */
public abstract class Agent {
    protected String symbol;
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int energy;
    protected int maxEnergy;
    protected int x;
    protected int y;
    protected World world;
    protected boolean alive;
    
    /**
     * Constructor for creating a customizable agent
     */
    public Agent(String name, String symbol, int hp, int energy) {
        this.name = name;
        this.symbol = symbol;
        this.hp = hp;
        this.maxHp = hp;
        this.energy = energy;
        this.maxEnergy = energy;
        this.x = -1;
        this.y = -1;
        this.world = null;
        this.alive = true;
    }

    /**
     * Default constructor
     */
    public Agent() {
        this("Billy", "B", 100, 50);
    }
    
    /**
     * Called when agent is placed in the world
     */
    public void onPlacedInWorld(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.alive = true;
    }
    
    /**
     * Abstract method - child classes define their behavior
     * Called each turn to allow agents to act autonomously
     */
    public abstract void act();
    
    /**
     * Move the agent in a direction
     */
    public boolean move(int dx, int dy) {
        if (world == null || energy < 5) {
            return false;
        }
        
        int newX = x + dx;
        int newY = y + dy;
        
        // Check if position is valid and empty
        if (world.isValidPosition(newX, newY)) {
            String cellContent = world.getCell(newX, newY);
            
            // Can move to empty space or food
            if (cellContent.equals(" ") || cellContent.equals("F")) {
                world.moveAgent(name, newX, newY);
                this.x = newX;
                this.y = newY;
                energy -= 5;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Move randomly
     */
    public void moveRandomly() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[] direction = directions[(int)(Math.random() * 4)];
        move(direction[0], direction[1]);
    }
    
    /**
     * Eat food at the agent's current location
     */
    public void eatFood() {
        if (world != null && world.getCell(x, y).equals("F")) {
            energy = Math.min(energy + 30, maxEnergy);
            hp = Math.min(hp + 10, maxHp);
            world.setCell(x, y, " ");
        }
    }
    
    /**
     * Lose energy over time (hunger)
     */
    public void loseEnergy(int amount) {
        energy -= amount;
        if (energy < 0) {
            energy = 0;
            hp -= 5; // Starving causes HP loss
        }
        if (hp <= 0) {
            alive = false;
        }
    }
    
    /**
     * Gain health
     */
    public void heal(int amount) {
        hp = Math.min(hp + amount, maxHp);
    }
    
    /**
     * Take damage
     */
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            alive = false;
        }
    }
    
    /**
     * Check if looking in a direction for something
     */
    public String lookInDirection(int dx, int dy, int range) {
        for (int i = 1; i <= range; i++) {
            int checkX = x + (dx * i);
            int checkY = y + (dy * i);
            
            if (world.isValidPosition(checkX, checkY)) {
                String cell = world.getCell(checkX, checkY);
                if (!cell.equals(" ")) {
                    return cell;
                }
            }
        }
        return null;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public String getSymbol() {
        return symbol;
    }

    public int getHp() {
        return hp;
    }
    
    public int getEnergy() {
        return energy;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp);
    }
    
    public void setEnergy(int energy) {
        this.energy = Math.min(energy, maxEnergy);
    }

    @Override
    public String toString() {
        return name + " (" + symbol + ") - HP: " + hp + "/" + maxHp + " - Energy: " + energy + "/" + maxEnergy;
    }
}
