# Clanker World - Agent Simulation Game

## Overview
Clanker World is a sophisticated agent-based simulation game where autonomous agents interact within a 2D world. Watch as hunters, gatherers, and scavengers compete for food while navigating obstacles.

## Architecture

### Core Classes

**Agent.java** (Abstract Parent Class)
- Base class for all agents in the world
- Features:
  - Position tracking (x, y coordinates)
  - Health and energy systems
  - Autonomous behavior through `act()` method
  - Movement, eating, damage/healing mechanics
  - Visual representation in the game

**World.java**
- 2D grid-based environment
- Cell types: Empty space, Food (F), Obstacles (#)
- Agent management and tracking
- Simulation engine with turn-based execution
- Visual display capabilities

### Agent Subclasses

**Hunter.java** - Aggressive food seeker
- High damage (15-20)
- Searches for food within configurable range
- Attacks when threatened
- Customizable: damage, range, HP, energy

**Gatherer.java** - Peaceful collector
- Excellent vision (5+ cell range)
- Efficient foragers
- Tracks food collected
- Customizable: vision range, collection efficiency

**Scavenger.java** - Opportunistic wanderer
- Eats anything found
- Mutation system for adaptation
- Customizable: mutation rate, hunger threshold

### GUI Components

**GamePanel.java**
- Renders the world visually with colors and symbols
- Displays agent health bars
- Shows UI overlay with statistics
- Real-time visualization of simulation

**GameController.java**
- Manages game simulation logic
- Controls simulation speed
- Pause/resume functionality
- Step-through capability for debugging

**GameWindow.java**
- Main application window
- Control buttons (Play, Pause, Step, Reset)
- Speed controls
- Agent initialization
- Statistics display

**Universe.java**
- Application entry point
- Launches the GUI window
- Initializes world with random agents

## Running the Application

### On Windows/Mac/Linux (with Java 11+)
```bash
javac *.java
java Universe
```

The game window will appear with:
- **Graphical grid display** with colored cells
- **Control buttons** for play/pause/step
- **Speed controls** for simulation tempo
- **Real-time statistics** showing turn count and agent count

### Controls
- **▶ PLAY** - Start the simulation
- **⏸ PAUSE** - Pause execution
- **⏭ STEP** - Execute one turn manually
- **↻ RESET** - Restart the world
- **◄◄ SLOWER** / **FASTER ►►** - Adjust simulation speed

## Game Features

### Visual Elements
- 🌾 **Green circles** = Food
- 🧱 **Gray squares** = Obstacles
- **Red circles** = Hunters (P for Predator)
- **Blue circles** = Gatherers (C for Collector)
- **Orange circles** = Scavengers (W for Wanderer)
- **Health bars** below each agent (color coded)

### Agent Behaviors
Each agent type acts autonomously:
- **Hunters**: Search for food aggressively, patrol when satisfied
- **Gatherers**: Efficiently search for food using vision range
- **Scavengers**: Wander and eat anything found

### Customization
Create custom agents before simulation:
```java
Hunter customHunter = new Hunter("MyHunter", "M", 150, 75, 25);
world.addAgent(customHunter, 5, 5);

Gatherer customGatherer = new Gatherer("Collector", "C", 120, 90);
world.addAgent(customGatherer, 10, 10);
```

### Simulation System
- Each turn, all agents execute their `act()` method
- Energy depletes over time (simulating hunger)
- Agents can eat food to gain energy/health
- Dead agents are removed from the world
- Statistics updated in real-time

## File Structure
```
Project-Handout/
├── Agent.java          (Abstract agent base class)
├── Hunter.java         (Aggressive hunter agent)
├── Gatherer.java       (Peaceful gatherer agent)
├── Scavenger.java      (Opportunistic scavenger agent)
├── World.java          (2D simulation world)
├── GamePanel.java      (Rendering/visualization)
├── GameController.java (Simulation logic)
├── GameWindow.java     (Main GUI window)
├── Universe.java       (Application entry point)
└── README.md          (This file)
```

## Customization Examples

### Create a larger world
```java
World largeWorld = new World(20, 30);  // 20 high, 30 wide
```

### Adjust agent stats
```java
Hunter powerful = new Hunter("Boss", "B", 200, 100, 50);
powerful.setHunterRange(5);  // Increase search range
```

### Control simulation speed
```java
// In the game window, use the speed buttons
// Or programmatically: gameController.setSimulationSpeed(200); // 200ms per turn
```

## System Requirements
- Java 11 or higher
- 8MB RAM minimum
- Mouse and keyboard for control
- 1024x900 pixels or larger display

## Performance Notes
- Handles up to 50+ agents smoothly
- Each turn calculation takes ~10-50ms depending on world size
- Simulation can be paused to inspect agent states
- Memory efficient with automatic dead agent cleanup

## Future Enhancement Ideas
- Save/load world states
- Agent breeding system
- Pheromone trails
- Different biome types
- Predator-prey chain analysis
- Statistics graphs

## License & Credits
Clanker World - Agent-Based Simulation Game
Educational project for teaching OOP and game programming concepts
