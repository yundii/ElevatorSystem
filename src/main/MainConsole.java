package main;

import building.Building;
import java.util.Scanner;
import scanerzus.Request;

/**
 * The driver for the elevator system.
 * This class will create the elevator system and run it.
 * this is for testing the elevator system.
 * <p>
 * It provides a user interface to the elevator system.
 */
public class MainConsole {

  /**
   * The main method for the elevator system.
   * This method creates the elevator system and runs it.
   *
   * @param args the command line arguments
   */

  public static void main(String[] args) {

    // the number of floors, the number of elevators, and the number of people.

    final int numFloors = 11;
    final int numElevators = 8;
    final int numPeople = 3;


    String[] introText = {
        "Welcome to the Elevator System!",
        "This system will simulate the operation of an elevator system.",
        "The system will be initialized with the following parameters:",
        "Number of floors: " + numFloors,
        "Number of elevators: " + numElevators,
        "Number of people: " + numPeople,
        "The system will then be run and the results will be displayed.",
        "",
        "Press enter to start elevator system."
    };

    for (String line : introText) {
      System.out.println(line);

    }
    Scanner scanner = new Scanner(System.in);
    Building building = new Building(numFloors, numElevators, numPeople);

    System.out.println("Would you like to start the elevator system? (y/n)");
    String option = scanner.next();
    if ("y".equals(option)) {
      System.out.println("Starting elevator system...");
      building.startElevatorSystem();
      System.out.println("Building Report:");
      System.out.println(building.getBuildingReport().toString());
    } else {
      System.out.println("Exiting program.");
      System.exit(0);
    }

    while (true) {
      System.out.println("\nAvailable commands:");
      System.out.println("1. Request elevator (up or down)");
      System.out.println("2. Step elevator system");
      System.out.println("3. Stop elevator system");
      System.out.println("4. Exit");

      System.out.print("Enter your choice (1-4): ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          System.out.print("Enter start floor: ");
          int startFloor = scanner.nextInt();
          System.out.print("Enter end floor: ");
          int endFloor = scanner.nextInt();
          building.addRequest(new Request(startFloor, endFloor));
          System.out.println("Building Report:");
          System.out.println(building.getBuildingReport().toString());
          break;
        case 2:
          building.stepElevatorSystem();
          System.out.println("Building Report:");
          System.out.println(building.getBuildingReport().toString());
          break;
        case 3:
          building.stopElevatorSystem();
          System.out.println("Building Report:");
          System.out.println(building.getBuildingReport().toString());
          break;
        case 4:
          System.out.println("Exiting program.");
          scanner.close();
          System.exit(0);
          break;
        default:
          System.out.println("Invalid choice. Please enter a number between 1 and 4.");
      }
    }

  }


}