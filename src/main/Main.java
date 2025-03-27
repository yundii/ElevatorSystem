package main;

import building.Building;
import controller.BuildingController;
import view.BuildingView;

/**
 * Main class for the Building Elevator System. This class creates the model, view, and controller
 */
public class Main {
  /**
   * Main method for the Building Elevator System.
   * This method creates the model, view, and controller.
   * and starts the controller.
   * @param args Command line arguments
   */
  public static void main(String[] args) {

    Building model = new Building(11, 8, 3);

    BuildingView view = new BuildingView(model);

    BuildingController controller = new BuildingController(model, view);


    controller.go();

  }
}
