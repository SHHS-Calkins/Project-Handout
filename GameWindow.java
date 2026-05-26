import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Main game window - contains the game panel and control buttons
 */
public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    private GameController gameController;
    private World world;
    
    private JButton playPauseButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton speedSlowerButton;
    private JButton speedFasterButton;
    private JLabel speedLabel;
    private JLabel statsLabel;
    
    private Timer statsUpdateTimer;
    
    public GameWindow(World world) {
        this.world = world;
        setTitle("Cyber World - Agent Simulation Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Create game panel
        gamePanel = new GamePanel(world, null);
        
        // Create game controller
        gameController = new GameController(world, gamePanel);
        gamePanel.gameController = gameController;
        
        // Setup UI
        setupUI();
        
        // Setup stats update timer
        setupStatsTimer();
    }
    
    private void setupUI() {
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Game panel (center)
        gamePanel.setPreferredSize(new Dimension(900, 700));
        mainPanel.add(new JScrollPane(gamePanel), BorderLayout.CENTER);
        
        // Control panel (south)
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        // Info panel (north)
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(50, 50, 50));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // Play/Pause button
        playPauseButton = createButton("▶ PLAY", new Color(34, 139, 34));
        playPauseButton.addActionListener(e -> togglePlayPause());
        panel.add(playPauseButton);
        
        // Step button
        stepButton = createButton("⏭ STEP", new Color(70, 130, 180));
        stepButton.addActionListener(e -> gameController.stepOnce());
        panel.add(stepButton);
        
        // Reset button
        resetButton = createButton("↻ RESET", new Color(220, 20, 60));
        resetButton.addActionListener(e -> resetGame());
        panel.add(resetButton);
        
        panel.add(new JSeparator(JSeparator.VERTICAL) {{
            setPreferredSize(new Dimension(2, 40));
        }});
        
        // Speed slower button
        speedSlowerButton = createButton("◄◄ SLOWER", new Color(100, 100, 100));
        speedSlowerButton.addActionListener(e -> gameController.setSimulationSpeed(gameController.simulationSpeed + 100));
        panel.add(speedSlowerButton);
        
        // Speed label
        speedLabel = new JLabel("Speed: NORMAL");
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(speedLabel);
        
        // Speed faster button
        speedFasterButton = createButton("FASTER ►►", new Color(100, 100, 100));
        speedFasterButton.addActionListener(e -> gameController.setSimulationSpeed(Math.max(100, gameController.simulationSpeed - 100)));
        panel.add(speedFasterButton);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(220, 220, 220));
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panel.setPreferredSize(new Dimension(0, 50));
        
        JLabel titleLabel = new JLabel("🎮 CYBER WORLD - Agent Simulation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 15, 0, 0));
        panel.add(titleLabel, BorderLayout.WEST);
        
        statsLabel = new JLabel("Agents: 0 | Turn: 0");
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statsLabel.setBorder(new EmptyBorder(10, 0, 0, 15));
        statsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(statsLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(
                    Math.min(255, color.getRed() + 30),
                    Math.min(255, color.getGreen() + 30),
                    Math.min(255, color.getBlue() + 30)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void togglePlayPause() {
        if (!gameController.isRunning()) {
            gameController.start();
            playPauseButton.setText("⏸ PAUSE");
            playPauseButton.setBackground(new Color(220, 20, 60));
        } else {
            gameController.togglePause();
            if (gameController.isPaused()) {
                playPauseButton.setText("▶ RESUME");
                playPauseButton.setBackground(new Color(34, 139, 34));
            } else {
                playPauseButton.setText("⏸ PAUSE");
                playPauseButton.setBackground(new Color(220, 20, 60));
            }
        }
    }
    
    private void resetGame() {
        gameController.stop();
        playPauseButton.setText("▶ PLAY");
        playPauseButton.setBackground(new Color(34, 139, 34));
        
        // Recreate the world
        int width = world.getWidth();
        int length = world.getLength();
        this.world = new World(length, width);
        gamePanel.world = world;
        gamePanel.repaint();
        
        // Reset game panel turn counter
        gamePanel.turn = 0;
        gameController = new GameController(world, gamePanel);
        gamePanel.gameController = gameController;
    }
    
    private void setupStatsTimer() {
        statsUpdateTimer = new Timer(200, e -> {
            String stats = String.format("Agents: %d | Turn: %d | Speed: %dms",
                world.getAgentCount(),
                gamePanel.getTurn(),
                gameController.simulationSpeed);
            statsLabel.setText(stats);
        });
        statsUpdateTimer.start();
    }
    
    /**
     * Add initial agents to the world
     */
    public void initializeAgents() {
        int width = world.getWidth();
        int length = world.getLength();
        
        // Create and place various agents
        Hunter predator = new Hunter("Predator-1", "P", 140, 80, 20);
        world.addAgent(predator, Math.max(1, width / 4), Math.max(1, length / 4));
        
        Gatherer collector = new Gatherer("Collector-1", "C", 110, 85);
        world.addAgent(collector, Math.min(width - 2, 3 * width / 4), Math.max(1, length / 4));
        
        Scavenger wanderer = new Scavenger("Wanderer-1", "W", 100, 65);
        world.addAgent(wanderer, Math.max(1, width / 2), Math.min(length - 2, 3 * length / 4));
        
        Hunter hunter = new Hunter("Hunter-1", "H", 120, 60, 15);
        world.addAgent(hunter, Math.min(width - 2, 3 * width / 4), Math.min(length - 2, 3 * length / 4));
        
        Gatherer gatherer = new Gatherer("Gatherer-1", "G", 100, 70);
        world.addAgent(gatherer, 2, 2);
    }
}
