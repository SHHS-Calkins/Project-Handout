# CLANKER WORLD - PROJECT INDEX & QUICK START

## 🎮 Welcome to Clanker World v2.0

A complete agent-based simulation game with **professional GUI** and **console interfaces**. 

---

## 📋 Quick Navigation

### 🚀 I Want to Play the Game (Now!)
1. Open terminal in this folder
2. Type: `javac *.java`
3. Type: `java Universe`
4. Click "▶ PLAY" button
5. Watch the simulation!

### 💻 I Want to Use Console Version
1. Open terminal in this folder
2. Type: `java ConsoleGame`
3. Type `help` for commands
4. Type `step` to advance turns

### 📖 I Want to Learn About It

**Choose your documentation:**

| Document | Best For | Read Time |
|----------|----------|-----------|
| **README_GAME.md** | Game features & how to play | 10 min |
| **COMPLETION_SUMMARY.md** | Project overview | 5 min |
| **APPLICATION_NOTES.md** | Architecture & technical details | 20 min |
| **FILE_MANIFEST.md** | Detailed file descriptions | 15 min |

### 🛠️ I Want to Customize/Modify

See "CUSTOMIZATION EXAMPLES" section below

---

## 📁 What's Included

### Core Game Files (Must Have)
```
Agent.java              - Abstract base class for all agents
World.java              - 2D simulation environment
Hunter.java             - Aggressive hunter agent type
Gatherer.java           - Peaceful gatherer agent type
Scavenger.java          - Opportunistic scavenger agent type
```

### Application Files (For Running)
```
Universe.java           - Launches GUI application
ConsoleGame.java        - Launches console application
GameWindow.java         - Main GUI window & controls
GamePanel.java          - Graphics rendering engine
GameController.java     - Simulation logic controller
```

### Documentation Files (For Learning)
```
README_GAME.md          - Game user guide & features
APPLICATION_NOTES.md    - Technical architecture docs
FILE_MANIFEST.md        - Detailed file reference
COMPLETION_SUMMARY.md   - Project completion summary
```

---

## 🎮 Game Controls

### GUI Version
```
▶ PLAY          - Start simulation
⏸ PAUSE         - Pause execution
⏭ STEP          - Execute one turn
↻ RESET         - Restart world
◄◄ SLOWER       - Decrease speed
FASTER ►►       - Increase speed
```

### Console Version
```
step (s)        - Execute one turn
sim             - Run multiple turns
display (d)     - Show world grid
stats           - Show agent details
state           - Show current state
help (h)        - Show help menu
quit (q)        - Exit game
```

---

## 🧬 Agent Types

### Hunter (Red)
- **Behavior**: Aggressive hunters seeking food
- **Damage**: 15-20 (configurable)
- **Vision Range**: 3 cells
- **Energy Cost**: 2/turn
- **Symbol**: P

### Gatherer (Blue)
- **Behavior**: Peaceful efficient foragers
- **Vision Range**: 5 cells (configurable)
- **Energy Cost**: 1/turn (efficient)
- **Tracks**: Food collected
- **Symbol**: C/G

### Scavenger (Orange)
- **Behavior**: Opportunistic wanderers
- **Special**: Mutation system
- **Energy Cost**: 2/turn
- **Tracks**: Items consumed
- **Symbol**: W

---

## 🎨 Visual Elements (GUI)

| Symbol | Color | Meaning |
|--------|-------|---------|
| Empty | White | Walkable space |
| 🌾 | Green | Food |
| 🧱 | Gray | Obstacles/walls |
| P | Red | Hunter |
| C/G | Blue | Gatherer |
| W | Orange | Scavenger |
| Bar | Color | Health indicator |

---

## 📚 Documentation Map

### For Players
- **Start Here**: README_GAME.md
- **Controls Guide**: See "Game Controls" section above
- **Quick Tips**: COMPLETION_SUMMARY.md

### For Developers
- **Architecture**: APPLICATION_NOTES.md
- **File Reference**: FILE_MANIFEST.md
- **Code Structure**: See file comments

### For Learners
- **Game Mechanics**: README_GAME.md (Mechanics section)
- **How Agents Work**: APPLICATION_NOTES.md (Agent System)
- **Simulation Loop**: APPLICATION_NOTES.md (Core Game Mechanics)

---

## 🎯 Customization Examples

### Create a Custom Hunter
```java
Hunter boss = new Hunter("Boss", "B", 200, 100, 50);
boss.setDamage(30);
boss.setHunterRange(5);
world.addAgent(boss, 5, 5);
```

### Create a Custom Gatherer
```java
Gatherer specialist = new Gatherer("Specialist", "S", 120, 90);
specialist.setVisionRange(8);
world.addAgent(specialist, 10, 10);
```

### Make a Larger World
In `GameWindow.java`, find this line:
```java
World world = new World(14, 16);
```
Change to (height, width):
```java
World world = new World(20, 30);
```

### Adjust Simulation Speed
In `GameWindow.java`, the speed is controlled by buttons, or:
```java
gameController.setSimulationSpeed(200);  // 200ms per turn
```

---

## ⚡ Quick Reference Commands

### Compile Everything
```bash
javac *.java
```

### Run GUI Game
```bash
java Universe
```

### Run Console Game
```bash
java ConsoleGame
```

### Interactive Demo (Console)
```bash
echo -e "display\nstep\nstep\nstats\nquit" | java ConsoleGame
```

### Show Project Stats
```bash
wc -l *.java *.md
```

---

## 🐛 Troubleshooting

### GUI Won't Open
→ Try console version: `java ConsoleGame`
→ Check Java version: `java -version` (need 11+)

### Compilation Errors
→ Make sure all .java files in same folder
→ Try: `rm *.class && javac *.java`

### Agents Not Moving
→ Check energy levels (use `stats` command)
→ Verify obstacles don't block all paths

### Need Help?
→ Read: APPLICATION_NOTES.md
→ Check: Troubleshooting section in README_GAME.md

---

## 📊 Project Stats

| Metric | Value |
|--------|-------|
| Total Files | 15 (10 Java, 5 Markdown) |
| Total Lines | 2961+ |
| Code Lines | ~2000+ |
| Documentation | ~1000+ |
| Java Classes | 9 |
| Agent Types | 3 |
| Features | 50+ |

---

## 🔄 Development Workflow

### To Add a New Agent Type:
1. Create new class extending `Agent`
2. Implement `act()` method with behavior
3. Add to `GameWindow.initializeAgents()`
4. Add color in `GamePanel.getAgentColor()`
5. Recompile: `javac *.java`
6. Test

### To Modify Simulation:
1. Edit desired method in `World.java` or `Agent.java`
2. Recompile: `javac *.java`
3. Test with: `java ConsoleGame` or `java Universe`

### To Change Graphics:
1. Edit colors in `GamePanel.java`
2. Modify drawing methods (drawFood, drawAgent, etc.)
3. Recompile: `javac *.java`
4. Test: `java Universe`

---

## 💡 Tips & Tricks

**Console Game Tips:**
- Use `step` repeatedly to single-step
- Use `display` to see the world map
- Use `stats` to check agent conditions
- Type `sim` then `5` to run 5 turns fast

**GUI Game Tips:**
- Click PAUSE to inspect agents without time pressure
- Use STEP to watch specific agent behaviors
- Adjust speed with buttons for different paces
- RESET clears everything and starts over

**Learning Tips:**
- Read README_GAME.md first
- Run console version to learn commands
- Use STEP mode to understand behavior
- Check stats to see agent numbers

---

## 📚 Full Documentation Order

**For Quick Start:**
1. This file (you're reading it!)
2. COMPLETION_SUMMARY.md
3. Start playing with `java Universe`

**For Deep Learning:**
1. README_GAME.md
2. APPLICATION_NOTES.md
3. FILE_MANIFEST.md
4. Read the source code

**For Development:**
1. APPLICATION_NOTES.md (Architecture)
2. FILE_MANIFEST.md (File descriptions)
3. Source code with comments
4. Experiment with modifications

---

## ✅ Verification

All files have been tested and verified:
- ✅ Compilation: All 10 Java files compile
- ✅ GUI: GameWindow launches (with display)
- ✅ Console: ConsoleGame runs
- ✅ Simulation: Agents act autonomously
- ✅ Documentation: 1000+ lines included

---

## 🎓 What You'll Learn

By exploring this project, you'll understand:
- Object-Oriented Programming (inheritance, polymorphism)
- GUI programming with Swing
- Game simulation loops
- Multi-threading basics
- Agent-based modeling
- Autonomous system design
- Professional documentation

---

## 🚀 Next Steps

### To Play:
```bash
javac *.java && java Universe
```

### To Test:
```bash
java ConsoleGame
```

### To Learn:
Read `README_GAME.md` then `APPLICATION_NOTES.md`

### To Modify:
Edit source code and recompile: `javac *.java`

---

## 📞 Support

### Issues?
1. Check README_GAME.md Troubleshooting
2. Check APPLICATION_NOTES.md Troubleshooting
3. Read FILE_MANIFEST.md for file info

### Want to Learn More?
- Read inline code comments
- Study the source files
- Experiment with modifications
- Create custom agent types

---

## 📄 File Descriptions Summary

| File | Type | Purpose |
|------|------|---------|
| Agent.java | Core | Abstract base for agents |
| World.java | Core | 2D simulation environment |
| Hunter/Gatherer/Scavenger.java | Core | Agent types |
| Universe.java | App | GUI launcher |
| ConsoleGame.java | App | Console launcher |
| GameWindow.java | GUI | Main window |
| GamePanel.java | GUI | Graphics rendering |
| GameController.java | GUI | Simulation controller |
| README_GAME.md | Docs | User guide |
| APPLICATION_NOTES.md | Docs | Technical docs |
| FILE_MANIFEST.md | Docs | File reference |
| COMPLETION_SUMMARY.md | Docs | Project summary |

---

## 🎊 You're Ready!

Everything is set up and ready to use. Start with:

```bash
javac *.java
java Universe
```

Then click **▶ PLAY** and enjoy the simulation!

---

**Version**: 2.0 Complete Application  
**Status**: ✅ Fully Functional  
**Java**: 11+ Required  
**Last Updated**: May 2026
