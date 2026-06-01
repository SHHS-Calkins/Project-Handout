# Cyber World - Complete File Manifest

## Project Overview
Cyber World is a complete agent-based simulation game with GUI and console interfaces. All functionality remains intact while the entire codebase has been transformed into a professional application.

## Core Simulation Files (Unchanged/Minimal Changes)

### `Agent.java` (Abstract Base Class)
**Purpose**: Define the interface for all agent types
**Key Features**:
- Abstract `act()` method for autonomous behavior
- Position tracking (x, y coordinates)
- Energy and health systems
- Movement mechanics: `move()`, `moveRandomly()`
- Food interaction: `eatFood()`
- Combat: `takeDamage()`, `attack()`
- Utility methods for world interaction

**Lines**: ~220 | **Status**: Completely rewritten for GUI compatibility

---

### `World.java` (2D Simulation Environment)
**Purpose**: Manage the game world, grid, and agents
**Key Features**:
- 2D cell grid (customizable size)
- Cell types: Empty (" "), Food ("F"), Obstacles ("#")
- Random world generation with varied terrain
- Agent management via HashMap
- Simulation engine: `simulateTurn()`, `simulate(int turns)`
- Visual display: `display()`, `displaySimple()`, `displayStats()`
- Boundary checking and movement validation

**Lines**: ~240 | **Status**: Enhanced with simulation loop

---

## Agent Type Files (Subclasses of Agent)

### `Hunter.java` (Aggressive Hunter Agent)
**Purpose**: Represent predatory agents with hunting behaviors
**Unique Traits**:
- High damage (15-20 configurable)
- Search range (3 cells default, customizable)
- Active food search using `searchForFood()`
- Aggressive hunting pattern: patrol when satisfied, hunt when hungry
- Energy cost: 2 per turn
- HP: 120 default

**Lines**: ~100 | **Status**: Autonomous behavior implemented

---

### `Gatherer.java` (Peaceful Collector Agent)
**Purpose**: Represent peaceful agents focused on efficient food gathering
**Unique Traits**:
- Excellent vision range (5 cells default, customizable)
- Tracks food collected counter
- Efficient pathfinding: `searchForFood()`
- Low energy consumption: 1 per turn
- Adaptive behavior based on energy levels
- HP: 100 default

**Lines**: ~100 | **Status**: Efficient gathering behaviors implemented

---

### `Scavenger.java` (Opportunistic Wanderer)
**Purpose**: Represent adaptable agents that wander and consume
**Unique Traits**:
- Mutation system for behavioral adaptation
- Items consumed counter
- Opportunistic searching: `searchNearby()`
- Random wandering with food seeking
- Mutation rate customizable (10% default)
- HP: 90 default (lowest among types)

**Lines**: ~100 | **Status**: Wandering and mutation system implemented

---

## GUI Application Files (NEW)

### `Universe.java` (Application Entry Point)
**Purpose**: Main launcher for the GUI application
**Key Features**:
- Creates world with default size (14x16)
- Instantiates GameWindow
- Initializes agents via `initializeAgents()`
- Uses SwingUtilities for thread safety

**Lines**: ~20 | **Status**: Simplified launcher for GUI

---

### `GameWindow.java` (Main Application Window)
**Purpose**: Primary GUI container and control center
**Key Components**:
- JFrame for main window (1000x900 default)
- North panel: Title and statistics display
- Center panel: GamePanel for rendering
- South panel: Control buttons and speed controls
- Button implementations: Play/Pause, Step, Reset, Speed
- Stats timer for real-time updates
- Initial agent placement

**Lines**: ~250 | **Status**: Complete GUI window implementation

**Controls Implemented**:
- ▶ PLAY / ⏸ PAUSE - Start/stop simulation
- ⏭ STEP - Single turn execution
- ↻ RESET - Restart world
- ◄◄ SLOWER / FASTER ►► - Speed adjustment

---

### `GamePanel.java` (Rendering Engine)
**Purpose**: Visual rendering of world and agents
**Key Features**:
- Cell-based graphics rendering (40x40 pixels default)
- Color-coded elements:
  - White: Empty space
  - Green circles: Food
  - Gray squares: Obstacles
  - Red circles: Hunters
  - Blue circles: Gatherers
  - Orange circles: Scavengers
- Health bar indicators below each agent
- UI overlay with statistics
- Legend display
- Anti-aliased rendering

**Lines**: ~300 | **Status**: Complete rendering system

**Visual Elements**:
- Grid lines for clarity
- Color-coded agent types
- Health bars (green/yellow/red)
- Agent symbols/initials
- Statistics overlay

---

### `GameController.java` (Simulation Engine)
**Purpose**: Manage game logic and simulation loop
**Key Features**:
- Background thread execution (SwingWorker)
- Pause/resume functionality
- Single-step capability for debugging
- Speed adjustment (100ms minimum)
- Turn-by-turn agent execution
- Dead agent cleanup

**Lines**: ~100 | **Status**: Complete game controller

**Key Methods**:
- `start()` - Begin simulation
- `stop()` - End simulation
- `togglePause()` - Pause/resume
- `stepOnce()` - Execute single turn
- `setSimulationSpeed()` - Adjust speed

---

## Console Application File (NEW - Alternative Interface)

### `ConsoleGame.java` (Text-Based Interface)
**Purpose**: Provide console/terminal game interface for headless environments
**Key Features**:
- Interactive command-line interface
- Commands: step, sim, display, stats, state, help, quit
- World initialization with agents
- Turn tracking and statistics
- State display with agent information
- Help menu system

**Lines**: ~200 | **Status**: Complete console interface

**Available Commands**:
```
step (s)    - Execute one turn
sim         - Run multiple turns
display (d) - Show world grid
stats       - Show detailed statistics
state       - Show current state
help (h)    - Display help
quit (q)    - Exit game
```

---

## Documentation Files

### `README_GAME.md` (Game User Guide)
**Contents**:
- Feature overview
- Architecture explanation
- Running instructions (both GUI and console)
- Control guide
- Game mechanics
- Customization examples
- System requirements
- Performance notes
- Enhancement ideas

**Lines**: ~250 | **Status**: Comprehensive user documentation

---

### `APPLICATION_NOTES.md` (Technical Documentation)
**Contents**:
- Complete architecture overview
- File structure
- Component interaction flows
- Game mechanics explanation
- Visual elements guide
- Command reference
- Implementation details
- Performance characteristics
- Testing guide
- Extensibility guide
- Troubleshooting

**Lines**: ~450 | **Status**: Complete technical documentation

---

### `FILE_MANIFEST.md` (This File)
**Contents**: Detailed description of all project files
**Lines**: ~300 | **Status**: File reference guide

---

## Build & Compilation

### Compilation Command
```bash
javac *.java
```

### Running GUI Version
```bash
java Universe
```

### Running Console Version
```bash
java ConsoleGame
```

### File Dependencies
```
Universe.java
    ├─ depends on: GameWindow.java
    │   ├─ depends on: GamePanel.java, GameController.java
    │   │   ├─ depends on: World.java, Agent.java (and subclasses)
    │   │   │   ├─ Hunter.java
    │   │   │   ├─ Gatherer.java
    │   │   │   └─ Scavenger.java

ConsoleGame.java
    ├─ depends on: World.java, Agent.java (and subclasses)
        ├─ Hunter.java
        ├─ Gatherer.java
        └─ Scavenger.java
```

---

## Statistics

| Metric | Value |
|--------|-------|
| **Total Java Files** | 9 |
| **Core Files** | 5 (Agent, World, Hunter, Gatherer, Scavenger) |
| **GUI Files** | 3 (Universe, GameWindow, GamePanel, GameController) |
| **Total Lines of Code** | ~2000+ |
| **Total Documentation** | 1000+ lines |
| **Classes** | 9 |
| **Abstract Classes** | 1 (Agent) |
| **Concrete Classes** | 8 |

---

## Change Summary

### What Was Modified
1. **Agent.java** - Completely rewritten as abstract base with autonomous behaviors
2. **World.java** - Enhanced with simulation engine and visual display
3. **Hunter.java** - Rewritten with autonomous hunting behavior
4. **Gatherer.java** - New file with autonomous gathering behavior
5. **Scavenger.java** - New file with autonomous wandering behavior
6. **Universe.java** - Converted from console launcher to GUI launcher

### What Was Added
1. **GameWindow.java** - New: Complete GUI window
2. **GamePanel.java** - New: Graphics rendering engine
3. **GameController.java** - New: Simulation controller
4. **ConsoleGame.java** - New: Alternative console interface
5. **README_GAME.md** - New: User documentation
6. **APPLICATION_NOTES.md** - New: Technical documentation
7. **FILE_MANIFEST.md** - New: This reference guide

---

## Version Information
- **Application**: Cyber World v2.0 (GUI)
- **Alternative**: Cyber World Console v2.0
- **Java Version Required**: 11+
- **Created**: 2026
- **Status**: Complete and Fully Functional

---

## File Size Summary
```
Core Simulation:
  Agent.java          ~220 lines
  World.java          ~240 lines
  Hunter.java         ~100 lines
  Gatherer.java       ~100 lines
  Scavenger.java      ~100 lines

GUI Application:
  Universe.java       ~20 lines
  GameWindow.java     ~250 lines
  GamePanel.java      ~300 lines
  GameController.java ~100 lines

Console Application:
  ConsoleGame.java    ~200 lines

Documentation:
  README_GAME.md      ~250 lines
  APPLICATION_NOTES.md ~450 lines
  FILE_MANIFEST.md    ~300 lines

Total: ~2600+ lines of code and documentation
```

---

## Quick Reference

### To Run GUI Game
```bash
javac *.java
java Universe
```

### To Run Console Game
```bash
javac *.java
java ConsoleGame
```

### To Add Custom Agent
1. Extend `Agent` class
2. Implement `act()` method
3. Add to `GameWindow.initializeAgents()`
4. Recompile and run

### To Modify World Size
Edit `GameWindow.java`, change line:
```java
World largeWorld = new World(20, 30);  // height, width
```

---

**End of File Manifest**
