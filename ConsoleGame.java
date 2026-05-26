import java.util.Scanner;

/**
 * Console-based version of Cyber World for headless environments
 * Run this with: java ConsoleGame
 */
public class ConsoleGame {
    private World world;
    private int turnCount;
    private boolean running;
    
    public ConsoleGame(int width, int length) {
        this.world = new World(length, width);
        this.turnCount = 0;
        this.running = true;
    }
    
    public void initialize() {
        // Create and place various agents
        Hunter predator = new Hunter("Predator-1", "P", 140, 80, 20);
        world.addAgent(predator, Math.max(1, world.getWidth() / 4), Math.max(1, world.getLength() / 4));
        
        Gatherer collector = new Gatherer("Collector-1", "C", 110, 85);
        world.addAgent(collector, Math.min(world.getWidth() - 2, 3 * world.getWidth() / 4), 
                      Math.max(1, world.getLength() / 4));
        
        Scavenger wanderer = new Scavenger("Wanderer-1", "W", 100, 65);
        world.addAgent(wanderer, Math.max(1, world.getWidth() / 2), 
                      Math.min(world.getLength() - 2, 3 * world.getLength() / 4));
        
        Hunter hunter = new Hunter("Hunter-1", "H", 120, 60, 15);
        world.addAgent(hunter, Math.min(world.getWidth() - 2, 3 * world.getWidth() / 4), 
                      Math.min(world.getLength() - 2, 3 * world.getLength() / 4));
        
        Gatherer gatherer = new Gatherer("Gatherer-1", "G", 100, 70);
        world.addAgent(gatherer, 2, 2);
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     CYBER WORLD - CONSOLE MODE      ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        System.out.println("World initialized: " + world.getWidth() + "x" + world.getLength());
        System.out.println("Agents added: " + world.getAgentCount());
        
        showHelp();
        
        while (running) {
            System.out.print("\n[Turn " + turnCount + "] > ");
            String command = scanner.nextLine().trim().toLowerCase();
            
            processCommand(command);
        }
        
        scanner.close();
    }
    
    private void processCommand(String command) {
        switch(command) {
            case "step":
            case "s":
                world.simulateTurn();
                turnCount++;
                displayState();
                break;
                
            case "sim":
                System.out.print("How many turns? ");
                try {
                    Scanner scanner = new Scanner(System.in);
                    int turns = scanner.nextInt();
                    for (int i = 0; i < turns; i++) {
                        world.simulateTurn();
                        turnCount++;
                        if (i % 5 == 0) {
                            System.out.print(".");
                        }
                    }
                    System.out.println("\nCompleted " + turns + " turns");
                    displayState();
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
                break;
                
            case "display":
            case "d":
                world.display();
                break;
                
            case "stats":
                world.displayStats();
                break;
                
            case "state":
                displayState();
                break;
                
            case "help":
            case "h":
                showHelp();
                break;
                
            case "quit":
            case "q":
                running = false;
                System.out.println("\nGame ended. Final statistics:");
                world.displayStats();
                break;
                
            default:
                System.out.println("Unknown command. Type 'help' for commands.");
        }
    }
    
    private void displayState() {
        System.out.println("\n────────────────────────────────────────");
        System.out.println("Turn: " + turnCount + " | Active Agents: " + world.getAgentCount());
        System.out.println("────────────────────────────────────────");
        
        // Show agent status in a compact format
        for (Agent agent : getAgents()) {
            String status = String.format("%-15s | HP: %3d | Energy: %3d | Pos: (%d, %d)",
                agent.getName(),
                agent.getHp(),
                agent.getEnergy(),
                agent.getX(),
                agent.getY());
            
            // Add type-specific info
            if (agent instanceof Hunter) {
                Hunter h = (Hunter) agent;
                status += " [Hunter, Dmg: " + h.getDamage() + "]";
            } else if (agent instanceof Gatherer) {
                Gatherer g = (Gatherer) agent;
                status += " [Gatherer, Food: " + g.getFoodCollected() + "]";
            } else if (agent instanceof Scavenger) {
                Scavenger s = (Scavenger) agent;
                status += " [Scavenger, Items: " + s.getItemsConsumed() + "]";
            }
            
            System.out.println(status);
        }
    }
    
    private java.util.ArrayList<Agent> getAgents() {
        java.util.ArrayList<Agent> agents = new java.util.ArrayList<>();
        // This is a workaround since World doesn't expose agents directly
        // We'll iterate through and get info from the world's getAgents() string
        for (String line : world.getAgents().split("\n")) {
            if (!line.isEmpty()) {
                // Could parse this, but for now we'll just display via displayStats
            }
        }
        return agents;
    }
    
    private void showHelp() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           AVAILABLE COMMANDS          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ step (s)        - Execute one turn     ║");
        System.out.println("║ sim             - Run multiple turns   ║");
        System.out.println("║ display (d)     - Show world grid      ║");
        System.out.println("║ stats           - Show agent details   ║");
        System.out.println("║ state           - Show current state   ║");
        System.out.println("║ help (h)        - Show this help       ║");
        System.out.println("║ quit (q)        - Exit game            ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public static void main(String[] args) {
        // Create world
        int width = 14;
        int length = 12;
        
        if (args.length >= 2) {
            try {
                width = Integer.parseInt(args[0]);
                length = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid dimensions. Using default.");
            }
        }
        
        ConsoleGame game = new ConsoleGame(width, length);
        game.initialize();
        game.run();
    }
}
