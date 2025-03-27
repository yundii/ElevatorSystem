# Elevator Project

## About/Overview
This project aims to design a elevator system for controlling one or more elevators in a building. The system simulates the behavior of elevators in a modern building, managing requests and controlling elevator movements efficiently.


## List of features
* GUI using Java Swing to visualize and control the elevator system
* MVC architecture for efficient communication between model, view, and controller
* Real-time updates of elevator status and building state
* Start, stop, and put out of service functionalities for the elevator system
* Ability to customize simulation parameters (number of floors, elevators, capacity)
* Step button to advance simulation time


## How To Run
java -jar ElevatorVersionPlusPlus.jar


## How to Use the Program
* Upon running the program, the GUI will display the elevator control system.
* Each elevator's status, including position, current direction, out of service status, and scheduled stops, will be displayed.
* Use the request button to submit new elevator requests, specifying the source and destination floors.
* Use the start button to initiate the elevator system and begin accepting requests.
* Use the stop button to halt the elevator system and prevent further requests from being accepted.
* Use the step button to simulate and observe elevator movements.

## Design/Model Changes
* Implemented the elevator model provided by Up and Down Machines.
* Extended the model to include functionalities such as starting, stopping, and putting elevators out of service.

## Assumptions
* All elevators in the system have the same capacity and follow the rules specified by Up and Down Machines.
* The GUI will accurately reflect the state of the elevator system and respond to user actions in real-time.

## Limitations
* The GUI may not include advanced visualizations or animations.
* Error handling for invalid user inputs may be limited in the current implementation.

## Citations
* The project follows the guidelines of MVC architecture and Java Swing GUI development.
