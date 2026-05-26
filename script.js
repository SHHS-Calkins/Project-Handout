const canvas = document.getElementById('gameCanvas');
const context = canvas.getContext('2d');
const turnCountElement = document.getElementById('turnCount');
const agentCountElement = document.getElementById('agentCount');
const speedValueElement = document.getElementById('speedValue');
const statusValueElement = document.getElementById('statusValue');
const agentListElement = document.getElementById('agentList');
const startButton = document.getElementById('startButton');
const pauseButton = document.getElementById('pauseButton');
const stepButton = document.getElementById('stepButton');
const resetButton = document.getElementById('resetButton');
const speedSlider = document.getElementById('speedSlider');

const CELL_SIZE = 40;
const GRID_WIDTH = 16;
const GRID_HEIGHT = 14;

let world = null;
let running = false;
let interval = null;
let turn = 0;
let speed = 500;

class World {
  constructor(rows, cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = [];
    this.agents = [];
    this.initialize();
  }

  initialize() {
    this.grid = Array.from({ length: this.rows }, () =>
      Array.from({ length: this.cols }, () => {
        const value = Math.random();
        if (value < 0.28) return 'food';
        if (value < 0.46) return 'obstacle';
        return 'empty';
      })
    );
    this.agents = [];
  }

  addAgent(agent, x, y) {
    if (!this.isValidPosition(x, y)) return false;
    if (this.cellHasAgent(x, y) || this.grid[y][x] === 'obstacle') return false;
    agent.place(this, x, y);
    this.agents.push(agent);
    return true;
  }

  removeAgent(agent) {
    this.agents = this.agents.filter((item) => item !== agent);
  }

  getCell(x, y) {
    if (!this.isValidPosition(x, y)) return null;
    return this.grid[y][x];
  }

  setCell(x, y, value) {
    if (!this.isValidPosition(x, y)) return;
    this.grid[y][x] = value;
  }

  isValidPosition(x, y) {
    return x >= 0 && x < this.cols && y >= 0 && y < this.rows;
  }

  cellHasAgent(x, y) {
    return this.agents.some((agent) => agent.x === x && agent.y === y && agent.alive);
  }

  getAgentAt(x, y) {
    return this.agents.find((agent) => agent.x === x && agent.y === y && agent.alive) || null;
  }

  simulateTurn() {
    const activeAgents = [...this.agents];
    activeAgents.forEach((agent) => {
      if (agent.alive) agent.act();
    });
    this.agents = this.agents.filter((agent) => agent.alive);
  }
}

class Agent {
  constructor(name, symbol, hp, energy) {
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

  place(world, x, y) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.alive = true;
  }

  act() {
    // Default behavior evolves into random movement
    this.loseEnergy(2);
    if (this.energy < 20) {
      this.searchForFood();
    } else {
      if (!this.searchForFood()) {
        this.moveRandomly();
      }
    }
  }

  move(dx, dy) {
    const newX = this.x + dx;
    const newY = this.y + dy;
    if (!this.world.isValidPosition(newX, newY)) return false;
    if (this.world.cellHasAgent(newX, newY)) return false;
    const target = this.world.getCell(newX, newY);
    if (target === 'obstacle') return false;

    this.x = newX;
    this.y = newY;
    this.energy = Math.max(0, this.energy - 5);

    if (target === 'food') {
      this.eatFood();
    }
    return true;
  }

  moveRandomly() {
    const directions = [
      { dx: -1, dy: 0 },
      { dx: 1, dy: 0 },
      { dx: 0, dy: -1 },
      { dx: 0, dy: 1 },
    ];
    const choice = directions[Math.floor(Math.random() * directions.length)];
    this.move(choice.dx, choice.dy);
  }

  searchForFood() {
    const directions = [
      { dx: -1, dy: 0 },
      { dx: 1, dy: 0 },
      { dx: 0, dy: -1 },
      { dx: 0, dy: 1 },
    ];
    for (const direction of directions) {
      const newX = this.x + direction.dx;
      const newY = this.y + direction.dy;
      if (!this.world.isValidPosition(newX, newY)) continue;
      if (this.world.getCell(newX, newY) === 'food') {
        return this.move(direction.dx, direction.dy);
      }
    }
    return false;
  }

  eatFood() {
    if (this.world && this.world.getCell(this.x, this.y) === 'food') {
      this.world.setCell(this.x, this.y, 'empty');
      this.energy = Math.min(this.maxEnergy, this.energy + 30);
      this.hp = Math.min(this.maxHp, this.hp + 10);
    }
  }

  loseEnergy(amount) {
    this.energy -= amount;
    if (this.energy < 0) {
      this.energy = 0;
      this.hp -= 5;
    }
    if (this.hp <= 0) {
      this.alive = false;
    }
  }

  getColor() {
    return '#888';
  }

  getType() {
    return 'Agent';
  }
}

class Hunter extends Agent {
  constructor(name, symbol, hp, energy, damage) {
    super(name, symbol, hp, energy);
    this.damage = damage;
    this.range = 3;
  }

  act() {
    if (!this.alive) return;
    this.loseEnergy(2);
    if (this.energy < 20) {
      if (!this.searchForFood()) this.moveRandomly();
    } else if (this.energy < 40) {
      if (!this.searchForFood()) this.moveRandomly();
    } else {
      this.moveRandomly();
    }
  }

  getColor() {
    return '#ff6d6d';
  }

  getType() {
    return 'Hunter';
  }
}

class Gatherer extends Agent {
  constructor(name, symbol, hp, energy) {
    super(name, symbol, hp, energy);
    this.vision = 5;
    this.foodCollected = 0;
  }

  act() {
    if (!this.alive) return;
    this.loseEnergy(1);
    if (!this.searchForFood()) {
      this.moveRandomly();
    }
  }

  searchForFood() {
    const directions = [
      { dx: -1, dy: 0 },
      { dx: 1, dy: 0 },
      { dx: 0, dy: -1 },
      { dx: 0, dy: 1 },
    ];
    for (const direction of directions) {
      for (let i = 1; i <= this.vision; i++) {
        const newX = this.x + direction.dx * i;
        const newY = this.y + direction.dy * i;
        if (!this.world.isValidPosition(newX, newY)) break;
        if (this.world.getCell(newX, newY) === 'food') {
          return this.move(direction.dx, direction.dy);
        }
      }
    }
    return false;
  }

  eatFood() {
    super.eatFood();
    this.foodCollected += 1;
  }

  getColor() {
    return '#5ea8ff';
  }

  getType() {
    return 'Gatherer';
  }
}

class Scavenger extends Agent {
  constructor(name, symbol, hp, energy) {
    super(name, symbol, hp, energy);
    this.mutationRate = 10;
    this.itemsConsumed = 0;
  }

  act() {
    if (!this.alive) return;
    this.loseEnergy(2);
    if (this.world.getCell(this.x, this.y) === 'food') {
      this.eatFood();
      this.itemsConsumed += 1;
      return;
    }
    if (Math.random() < 0.8) {
      this.moveRandomly();
    } else {
      this.searchNearby();
    }
  }

  searchNearby() {
    const directions = [
      { dx: -1, dy: 0 },
      { dx: 1, dy: 0 },
      { dx: 0, dy: -1 },
      { dx: 0, dy: 1 },
      { dx: -1, dy: -1 },
      { dx: -1, dy: 1 },
      { dx: 1, dy: -1 },
      { dx: 1, dy: 1 },
    ];
    for (const direction of directions) {
      const newX = this.x + direction.dx;
      const newY = this.y + direction.dy;
      if (!this.world.isValidPosition(newX, newY)) continue;
      if (this.world.getCell(newX, newY) === 'food') {
        if (this.move(direction.dx, direction.dy)) {
          this.itemsConsumed += 1;
          return true;
        }
      }
    }
    return false;
  }

  getColor() {
    return '#ffb75d';
  }

  getType() {
    return 'Scavenger';
  }
}

function resetSimulation() {
  world = new World(GRID_HEIGHT, GRID_WIDTH);
  turn = 0;
  running = false;
  clearInterval(interval);
  placeAgents();
  updateUI();
  drawWorld();
}

function placeAgents() {
  const predator = new Hunter('Predator', 'P', 140, 80, 20);
  const hunter = new Hunter('Hunter', 'H', 120, 60, 15);
  const collector = new Gatherer('Collector', 'C', 110, 85);
  const gatherer = new Gatherer('Gatherer', 'G', 100, 70);
  const scavenger = new Scavenger('Wanderer', 'W', 100, 65);

  world.addAgent(predator, 3, 3);
  world.addAgent(hunter, 12, 10);
  world.addAgent(collector, 12, 3);
  world.addAgent(gatherer, 2, 2);
  world.addAgent(scavenger, 8, 5);
}

function startSimulation() {
  if (running || world.agents.length === 0) return;
  running = true;
  statusValueElement.textContent = 'Running';
  interval = setInterval(() => {
    tick();
  }, speed);
}

function pauseSimulation() {
  running = false;
  if (world.agents.length === 0) {
    statusValueElement.textContent = 'Ended';
  } else {
    statusValueElement.textContent = 'Paused';
  }
  clearInterval(interval);
}

function tick() {
  world.simulateTurn();
  turn += 1;
  updateUI();
  drawWorld();

  if (world.agents.length === 0) {
    pauseSimulation();
    statusValueElement.textContent = 'Ended';
  }
}

function toggleRunning() {
  if (running) {
    pauseSimulation();
  } else {
    startSimulation();
  }
}

function updateUI() {
  turnCountElement.textContent = turn;
  agentCountElement.textContent = world.agents.length;
  speedValueElement.textContent = `${speed}ms`;
  statusValueElement.textContent = running ? 'Running' : 'Paused';
  renderAgentList();
}

function renderAgentList() {
  agentListElement.innerHTML = '';
  world.agents.forEach((agent) => {
    const item = document.createElement('div');
    item.className = 'agent-item';
    item.innerHTML = `
      <strong>${agent.name} (${agent.symbol})</strong>
      <span><strong>Type:</strong> ${agent.getType()}</span>
      <span><strong>HP:</strong> ${agent.hp}/${agent.maxHp}</span>
      <span><strong>Energy:</strong> ${agent.energy}/${agent.maxEnergy}</span>
      <span><strong>Position:</strong> (${agent.x}, ${agent.y})</span>
    `;
    if (agent instanceof Gatherer) {
      item.innerHTML += `<span><strong>Food Collected:</strong> ${agent.foodCollected}</span>`;
    }
    if (agent instanceof Scavenger) {
      item.innerHTML += `<span><strong>Items Consumed:</strong> ${agent.itemsConsumed}</span>`;
    }
    if (agent instanceof Hunter) {
      item.innerHTML += `<span><strong>Damage:</strong> ${agent.damage}</span>`;
    }
    agentListElement.appendChild(item);
  });
}

function drawWorld() {
  context.clearRect(0, 0, canvas.width, canvas.height);
  for (let y = 0; y < world.rows; y++) {
    for (let x = 0; x < world.cols; x++) {
      drawCell(x, y);
    }
  }
  drawGrid();
  drawAgents();
}

function drawCell(x, y) {
  const cell = world.getCell(x, y);
  const posX = x * CELL_SIZE;
  const posY = y * CELL_SIZE;
  context.fillStyle = '#0d1428';
  context.fillRect(posX, posY, CELL_SIZE, CELL_SIZE);

  if (cell === 'food') {
    context.fillStyle = '#34c18e';
    context.beginPath();
    context.arc(posX + CELL_SIZE / 2, posY + CELL_SIZE / 2, 10, 0, Math.PI * 2);
    context.fill();
  } else if (cell === 'obstacle') {
    context.fillStyle = '#6e6e6e';
    context.fillRect(posX + 6, posY + 6, CELL_SIZE - 12, CELL_SIZE - 12);
  }
}

function drawAgents() {
  world.agents.forEach((agent) => {
    if (!agent.alive) return;
    const posX = agent.x * CELL_SIZE;
    const posY = agent.y * CELL_SIZE;
    context.fillStyle = agent.getColor();
    context.beginPath();
    context.arc(posX + CELL_SIZE / 2, posY + CELL_SIZE / 2, 16, 0, Math.PI * 2);
    context.fill();

    context.fillStyle = '#fff';
    context.font = 'bold 16px Inter, system-ui';
    context.textAlign = 'center';
    context.textBaseline = 'middle';
    context.fillText(agent.symbol, posX + CELL_SIZE / 2, posY + CELL_SIZE / 2);

    const barWidth = 28;
    const hpPercent = Math.max(0, agent.hp / agent.maxHp);
    const barX = posX + (CELL_SIZE - barWidth) / 2;
    const barY = posY + CELL_SIZE - 14;
    context.fillStyle = 'rgba(255,255,255,0.2)';
    context.fillRect(barX, barY, barWidth, 5);
    context.fillStyle = hpPercent > 0.5 ? '#5de57e' : hpPercent > 0.25 ? '#f2c94c' : '#ef5350';
    context.fillRect(barX, barY, barWidth * hpPercent, 5);
  });
}

function drawGrid() {
  context.strokeStyle = 'rgba(255,255,255,0.08)';
  context.lineWidth = 1;
  for (let x = 0; x <= world.cols; x++) {
    context.beginPath();
    context.moveTo(x * CELL_SIZE, 0);
    context.lineTo(x * CELL_SIZE, world.rows * CELL_SIZE);
    context.stroke();
  }
  for (let y = 0; y <= world.rows; y++) {
    context.beginPath();
    context.moveTo(0, y * CELL_SIZE);
    context.lineTo(world.cols * CELL_SIZE, y * CELL_SIZE);
    context.stroke();
  }
}

startButton.addEventListener('click', startSimulation);
pauseButton.addEventListener('click', pauseSimulation);
stepButton.addEventListener('click', () => {
  if (!running) tick();
});
resetButton.addEventListener('click', resetSimulation);
speedSlider.addEventListener('input', (event) => {
  speed = parseInt(event.target.value, 10);
  speedValueElement.textContent = `${speed}ms`;
  if (running) {
    clearInterval(interval);
    interval = setInterval(tick, speed);
  }
});

resetSimulation();
