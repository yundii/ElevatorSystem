package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import scanerzus.Request;


/**
 * This class represents a building.
 */
public class Building implements BuildingInterface {
  private final int numberOfFloors;
  private final int numberOfElevators;
  private final int elevatorCapacity;
  private final Elevator[] elevators;
  private final List<Request> upRequests = new ArrayList<>();
  private final List<Request> downRequests = new ArrayList<>();
  private ElevatorSystemStatus buildingStatus;

  /**
   * The constructor for the building.
   *
   * @param numberOfFloors the number of floors in the building.
   * @param numberOfElevators the number of elevators in the building.
   * @param elevatorCapacity the capacity of the elevators in the building.
   *
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity) {
    if (numberOfFloors < 2 || numberOfFloors > 29) {
      throw new IllegalArgumentException("numberOfFloors must be between 2 and 29");
    } else if (numberOfElevators < 1) {
      throw new IllegalArgumentException("numberOfElevators must be greater than or equal to 1");
    } else if (elevatorCapacity < 3 || elevatorCapacity > 20) {
      throw new IllegalArgumentException("maxOccupancy must be between 3 and 20");
    } else {
      this.numberOfFloors = numberOfFloors;
      this.numberOfElevators = numberOfElevators;
      this.elevatorCapacity = elevatorCapacity;
      this.elevators = new Elevator[numberOfElevators];

      for (int i = 0; i < numberOfElevators; ++i) {
        this.elevators[i] = new Elevator(numberOfFloors, this.elevatorCapacity);
      }

      this.buildingStatus = ElevatorSystemStatus.outOfService;
    }

  }

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  public int getNumberOfFloors() {
    return this.numberOfFloors;
  }


  /**
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building
   */
  public int getNumberOfElevators() {
    return this.numberOfElevators;
  }


  /**
   * This method is used to get the max occupancy of the elevator.
   *
   * @return the max occupancy of the elevator.
   */
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  /**
   * This method is used to get the status of the elevators.
   *
   * @return the status of the elevators.
   */
  public ElevatorSystemStatus getBuildingStatus() {
    return this.buildingStatus;
  }

  /**
   * This method is used to get the up requests for the elevators.
   *
   * @return the requests for the elevators.
   */
  public List<Request> getUpRequests() {
    return this.upRequests;
  }

  /**
   * This method is used to get the down requests for the elevators.
   *
   * @return the requests for the elevators.
   */
  public List<Request> getDownRequests() {
    return this.downRequests;
  }

  public Elevator[] getElevators() {
    return this.elevators;
  }

  /**
   * This method is used to ss.
   */
  public void startElevatorSystem() {
    if (this.buildingStatus != ElevatorSystemStatus.running) {
      if (this.buildingStatus == ElevatorSystemStatus.stopping) {
        throw new IllegalStateException(
            "Elevator system cannot be started until it is out of service");
      } else {
        Elevator[] elevators = this.elevators;

        for (Elevator elevator : elevators) {
          elevator.start();
        }

        this.buildingStatus = ElevatorSystemStatus.running;
      }
    }
  }

  /**
   * This method is used to stop the elevator system.
   */
  public void stopElevatorSystem() {
    if (this.buildingStatus == ElevatorSystemStatus.running) {
      Elevator[] elevators = this.elevators;

      for (Elevator elevator : elevators) {
        elevator.takeOutOfService();
      }
      this.buildingStatus = ElevatorSystemStatus.stopping;
      this.upRequests.clear();
      this.downRequests.clear();
    } else {
      throw new IllegalStateException("Elevator system cannot be stopped until it is running");
    }
  }




  /**
   * This method is used to get the status of the elevators.
   *
   * @return the status of the elevators.
   */
  public BuildingReport getBuildingReport() {
    ElevatorReport[] elevatorReports = new ElevatorReport[this.elevators.length];

    for (int i = 0; i < this.elevators.length; ++i) {
      elevatorReports[i] = this.elevators[i].getElevatorStatus();
    }

    return new BuildingReport(
        this.numberOfFloors, this.numberOfElevators, this.elevatorCapacity,
        elevatorReports, this.upRequests, this.downRequests, this.buildingStatus);
  }

  /**
   * This method is used to add a request to the building.
   *
   * @param request the request to add to the building.
   * @return true if the request was added successfully, false otherwise.
   * @throws IllegalArgumentException if the request is null.
   *     the start floor is less than 0 or greater.
   *     than or equal to the number of floors.
   *     the end floor is less than 0 or greater than or equal to.
   *     the number of floors, or the start floor is equal to the end floor.
   * @throws IllegalStateException if the elevator system is not accepting requests.
   */
  public boolean addRequest(Request request) {
    if (this.buildingStatus == ElevatorSystemStatus.running) {
      if (request == null) {
        throw new IllegalArgumentException("Request cannot be null");
      } else if (request.getStartFloor() >= 0 && request.getStartFloor() < this.numberOfFloors) {
        if (request.getEndFloor() >= 0 && request.getEndFloor() < this.numberOfFloors) {
          if (request.getStartFloor() == request.getEndFloor()) {
            throw new IllegalArgumentException("Start floor and end floor cannot be the same");
          } else {
            if (request.getStartFloor() < request.getEndFloor()) {
              this.upRequests.add(request);
            } else {
              this.downRequests.add(request);
            }
            return true;
          }
        } else {
          throw new IllegalArgumentException(
              "End floor must be between 0 and " + (this.numberOfFloors - 1));
        }
      } else {
        throw new IllegalArgumentException(
            "Start floor must be between 0 and " + (this.numberOfFloors - 1));
      }
    } else {
      throw new IllegalStateException(
          "Elevator system not accepting requests in this state: " + (
              this.buildingStatus.toString()));
    }
  }

  /**
   * This method is used to get requests for an elevator.
   *
   * @return the requests for an elevator.
   */

  private List<Request> getRequests(List<Request> requests) {
    List<Request> requestsToReturn = new ArrayList<>();

    while (!requests.isEmpty() && requestsToReturn.size() < this.elevatorCapacity) {
      requestsToReturn.add((Request) requests.remove(0));
    }

    return requestsToReturn;
  }

  /**
   * This method is used to distribute requests to the elevators.
   */
  private void distributeRequests() {
    if (!this.upRequests.isEmpty() || !this.downRequests.isEmpty()) {
      Elevator[] elevators = this.elevators;

      for (Elevator elevator : elevators) {
        if (elevator.isTakingRequests()) {
          List<Request> requestsForOneElevator;
          if (elevator.getCurrentFloor() == 0) {
            requestsForOneElevator = this.getRequests(this.upRequests);
            elevator.processRequests(requestsForOneElevator);
          } else if (elevator.getCurrentFloor() == this.numberOfFloors - 1) {
            requestsForOneElevator = this.getRequests(this.downRequests);
            elevator.processRequests(requestsForOneElevator);
          }
        }
      }
    }
  }

  /**
   * This method is used to step the elevator system.
   */
  public void stepElevatorSystem() {
    if (this.buildingStatus != ElevatorSystemStatus.outOfService) {
      if (this.buildingStatus == ElevatorSystemStatus.running) {
        this.distributeRequests();
      }

      Elevator[] elevators = this.elevators;

      for (Elevator elevator : elevators) {
        elevator.step();
      }

      if (this.buildingStatus == ElevatorSystemStatus.stopping) {
        boolean allElevatorsOnGroundFloor = true;
        Elevator[] elevators1 = this.elevators;

        for (Elevator elevator : elevators1) {
          if (elevator.getCurrentFloor() != 0) {
            allElevatorsOnGroundFloor = false;
            break;
          }
        }

        if (allElevatorsOnGroundFloor) {
          this.buildingStatus = ElevatorSystemStatus.outOfService;
        }
      }
    }
  }


}


