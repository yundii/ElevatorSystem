package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import java.util.List;
import scanerzus.Request;

/**
 * This interface is used to represent a building.
 */
public interface BuildingInterface {
  int getNumberOfFloors();

  int getNumberOfElevators();

  int getElevatorCapacity();

  ElevatorSystemStatus getBuildingStatus();

  Elevator[] getElevators();

  List<Request> getUpRequests();

  List<Request> getDownRequests();

  void startElevatorSystem();

  void stopElevatorSystem();

  BuildingReport getBuildingReport();

  boolean addRequest(Request request);

  void stepElevatorSystem();


}
