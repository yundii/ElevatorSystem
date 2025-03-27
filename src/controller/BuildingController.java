package controller;

import building.Building;
import view.BuildingView;


/**
 * Controller component in the MVC architecture of the Building Elevator System.
 * This class defines the interactions between the model and the view
 * handling user input and updating the view based on
 * the building state.
 */
public class BuildingController {
  private Building model;
  private BuildingView view;

  public BuildingController(Building model, BuildingView view) {
    this.model = model;
    this.view = view;
  }

  public void go() {
    view.updateDisplay();
  }
}
