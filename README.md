# Elevator System

A Java-based multi-elevator control system simulation that models real-world elevator behavior in a building environment. This project demonstrates the implementation of a complex system design problem commonly used in technical interviews.

## Overview

This elevator system simulates multiple elevators in a building, handling passenger requests efficiently while maintaining realistic elevator behavior including:

- Multiple elevator coordination
- Up/down request management
- Door opening/closing sequences
- Floor-to-floor movement
- Out-of-service handling
- System start/stop capabilities

## Features

### Building Management
- Support for up to 10 elevators
- Configurable number of floors (3-30)
- Adjustable elevator capacity (3-20 passengers)
- Real-time request processing and distribution
- System status monitoring (Running/Stopping/Out of Service)

### Elevator Behavior
- Individual elevator state management
- Door control (open/close with timing)
- Direction control (UP/DOWN/STOPPED)
- Floor request processing
- Service state management
- Comprehensive status reporting

## Technical Specifications

### Building Requirements
- Minimum floors: 3
- Maximum floors: 30
- Minimum elevators: 1
- Maximum elevators: 10
- Elevator capacity: 3-20 passengers

### System States
- `RUNNING`: Normal operation, accepting requests
- `STOPPING`: System shutdown initiated, elevators returning to ground floor
- `OUT_OF_SERVICE`: All elevators at ground floor, doors open

## Implementation Details

### Core Components

1. **Building (`Building.java`)**
   - Central coordinator for elevator system
   - Manages request distribution
   - Controls system state
   - Handles elevator initialization

2. **Elevator (`Elevator.java`)**
   - Individual elevator control
   - Request processing
   - Movement management
   - Door operation
   - Status reporting

3. **Request System**
   - Handles up/down requests
   - Request validation
   - Request distribution to elevators

## Usage

### Building Initialization

## Testing

The system includes comprehensive unit tests covering:
- Building initialization
- Request processing
- Elevator movement
- System state transitions
- Edge cases and error conditions

## Design Patterns

- **Model-View-Controller (MVC)**: Separates the elevator system logic from its representation
- **State Pattern**: Manages elevator and system states
- **Observer Pattern**: Updates system status and notifications

## Contributors

This project was developed as part of a software engineering course assignment, demonstrating practical application of system design principles and object-oriented programming concepts.

## License

This project is for educational purposes only.

## Building and Running

### Creating JAR File

#### Using IntelliJ IDEA
1. Go to `File > Project Structure > Artifacts`
2. Click `+`, select `JAR > From modules with dependencies`
3. Select the main class (e.g., `main.MainConsole`)
4. Click `OK` and `Apply`
5. Go to `Build > Build Artifacts`
6. Select `Build` or `Rebuild`

#### Using Eclipse
1. Right-click on your project
2. Select `Export`
3. Choose `Java > Runnable JAR file`
4. Select the launch configuration with your main class
5. Choose an export destination
6. Click `Finish`

### Running the JAR

1. Open terminal/command prompt
2. Navigate to the JAR location
3. Run the command:
```bash
java -jar elevator-system.jar
```

### Command Line Interface

Once running, you can interact with the system using these commands:
1. Request elevator (up or down)
2. Step elevator system
3. Stop elevator system
4. Exit

Example session:
```
Enter your choice (1-4): 1
Enter start floor: 2
Enter end floor: 3
Building Report:
[Elevator 0: Floor 2, UP, Door CLOSED]
[Elevator 1: Floor 0, STOPPED, Door OPEN]
...
```

## System Requirements

- Java 11 or higher
- Minimum 256MB RAM
- Any operating system supporting Java