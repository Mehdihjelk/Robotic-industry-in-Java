# Factory Robot Simulator

## Overview

This project is a Java-based simulation of a small factory where autonomous robots move between work areas, booths, charging points, and stock zones.

The simulation is rendered through a Canvas viewer UI and follows an MVC-style organization:

- **Model**: factory components and robot behaviors.
- **Controller**: simulation lifecycle (start/stop), observers, and persistence bridge.
- **View**: provided by the external `canvas-viewer` library.

The main objective is to model robot movement in a constrained environment and compute shortest paths in real time using a graph-based pathfinding approach.

## Main Features

- Factory layout composed of rooms, areas, booths, charging points, and doors.
- Multiple robots running concurrently in dedicated threads.
- Shortest-path computation with **Dijkstra** (via JGraphT).
- Dynamic path recomputation toward each robot's next target component.
- Observer-based model updates for UI refresh.
- Basic simulation persistence (serialize/deserialize canvas state).
- Java logging support through `config/logging.properties`.

## How It Works

1. `implement.Application` builds the factory map and initializes components.
2. Each `Robot` receives an ordered list of components to visit.
3. `pathfinder.FactoryToGraph` turns the factory into a weighted directed graph of valid positions.
4. `pathfinder.FactoryPathFinder` computes shortest paths between source and target positions.
5. `controller.SimulatorController` starts the simulation loop and robot threads.
6. The Canvas viewer observes the model and redraws changes continuously.

## Project Structure

```text
source/
	controller/      # Simulation controller
	implement/       # App bootstrap and factory setup
	model/           # Domain model (Factory, Robot, Room, Door, etc.)
	pathfinder/      # Graph construction and shortest-path logic
	persistence/     # Save/load simulation state
	test/            # Main entry point: SimulatorApp

libs/
	canvas-viewer-v2.jar
	jgrapht-core-1.5.2.jar
	jheaps-0.14.jar

config/
	logging.properties
	SimulatorApp.launch
```

## Requirements

- Java **17**
- A Java IDE (Eclipse recommended, project already includes `.project` and `.classpath`)

## Run The Project

### Option 1: Eclipse (recommended)

1. Import the folder as an existing Eclipse Java project.
2. Ensure the JRE is Java 17.
3. Run `test.SimulatorApp` (or use `config/SimulatorApp.launch`).

### Option 2: Command line

From the project root, compile and run:

```powershell
mkdir bin -ErrorAction SilentlyContinue
javac -cp "libs/*" -d bin (Get-ChildItem -Path source -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -Djava.util.logging.config.file=config/logging.properties -cp "bin;libs/*" test.SimulatorApp
```

## Notes

- Robots move with small pauses on some target zones to emulate task execution timing.
- The pathfinding graph uses orthogonal and diagonal weighted edges.
- Some classes (for example around puck handling) indicate work-in-progress behavior and future extensions.

## Authors

Mehdi El Kouarati
