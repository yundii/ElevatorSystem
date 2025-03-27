package view;

import building.BuildingReport;
import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.Iterator;
import java.util.List;
import scanerzus.Request;

/**
 * This class is used to display the building status in a text-based format.
 */
public class BuildingViewDisplay {
  public BuildingViewDisplay() {
  }

  private String centreString(String s, int width) {
    int leftPadding = (width - 2 - s.length()) / 2;
    int rightPadding = width - leftPadding - s.length();
    return " " + " ".repeat(leftPadding) + s + " ".repeat(rightPadding) + " ";
  }

//  private String emptyLine(int width) {
//    return "*" + " ".repeat(width - 2) + "*";
//  }

  private String leftString(String s, int width) {
    return width - s.length() - 1 < 0 ? s : " " + s + " ".repeat(width - s.length() - 1) + " ";
  }

  private String bar(int width) {
    return "*".repeat(width) + "\n";
  }

  private String requestsToString(String title, List<Request> requests) {
    StringBuilder sb;
    sb = new StringBuilder();
    StringBuilder line = new StringBuilder();
    line.append(title);
    if (line.length() < 4) {
      line.append(" ".repeat(4 - line.length()));
    }

    line.append(String.format("[(%03d)]", requests.size()));
    line.append(" ");
    int width = 65;
    boolean requestsProcessed = false;
    Iterator var7 = requests.iterator();

    while (var7.hasNext()) {
      Request request = (Request) var7.next();
      if (line.length() + request.toString().length() > width) {
        line.append("...");
        break;
      }

      line.append(request.toString());
      line.append(" ");
    }

    if (line.length() > 0) {
      sb.append(this.leftString(line.toString(), 78));
      sb.append("\n");
    }

    return sb.toString();
  }

  private String[] computeElevatorDisplayArray(BuildingReport buildingStatus) {
    ElevatorReport[] elevatorReports = buildingStatus.getElevatorReports();
    int numFloors = buildingStatus.getNumFloors();
    String[] displayArray = new String[numFloors];

    for (int floor = numFloors - 1; floor >= 0; --floor) {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%2d]", floor));

      int j;
      for (j = 0; j < elevatorReports.length; ++j) {
        ElevatorReport elevatorReport = elevatorReports[j];
        if (elevatorReport.getCurrentFloor() == floor) {
          sb.append(String.format("%2d", j));
        } else {
          sb.append("  ");
        }
      }

      j = numFloors - floor - 1;
      displayArray[j] = sb.toString();
    }

    return displayArray;
  }

  private String[] elevatorWithGraphic(BuildingReport buildingReport) {
    String[] displayArray = this.computeElevatorDisplayArray(buildingReport);
    int rows = displayArray.length;
    String[] elevatorStatus = new String[rows];


    for (int i = 0; i < rows; ++i) {
      String display = displayArray[i];
      StringBuilder sb = new StringBuilder();
      sb.append(display);
      elevatorStatus[i] = sb.toString();
    }


    return elevatorStatus;
  }

  private String[] elevatorStatusDetail(BuildingReport buildingReport) {
    String[] elevators = new String[buildingReport.getElevatorReports().length];

    for (int i = 0; i < buildingReport.getElevatorReports().length; ++i) {
      String elevatorStatus = String.format(
          "Elevator %d: %s", i,
          buildingReport.getElevatorReports()[i].toString());
      elevators[i] = elevatorStatus;
    }

    return elevators;
  }
  private String buildingDisplay(BuildingReport buildingReport) {
    StringBuilder sb = new StringBuilder();
    sb.append(this.bar(80));
    String title = String.format(
        "Floors: %d, Elevators: %d, Capacity: %d",
        buildingReport.getNumFloors(),
        buildingReport.getNumElevators(),
        buildingReport.getElevatorCapacity());
    sb.append(this.centreString(title, 78));
    sb.append("\n");
    ElevatorSystemStatus sysStatus = buildingReport.getSystemStatus();
    switch (sysStatus) {
      case stopping:
        sb.append(this.centreString("Elevator System Stopping", 78));
        break;
      case outOfService:
        sb.append(this.centreString("Elevator System Out of Service", 78));
        break;
      case running:
        sb.append(this.centreString("Elevator System Running", 78));
        break;
      default:
        sb.append(this.centreString("Elevator System Unknown", 78));
    }

    sb.append("\n");
    sb.append(this.bar(80));
    sb.append(this.requestsToString("Up", buildingReport.getUpRequests()));
    sb.append(this.requestsToString("Down", buildingReport.getDownRequests()));
    sb.append(this.bar(80));
    sb.append(this.centreString("Elevator Status", 78));
    sb.append("\n");

    // Elevator status details
    String[] elevatorStatus = this.elevatorStatusDetail(buildingReport);
    for (String status : elevatorStatus) {
      sb.append(status).append("\n");
    }

    // Elevator graphic
    String[] elevatorGraphic = this.elevatorWithGraphic(buildingReport);
    for (String graphic : elevatorGraphic) {
      sb.append(graphic).append("\n");
    }

    sb.append(this.bar(80));
    return sb.toString();
  }

  public String display(BuildingReport buildingReport) {
    return this.buildingDisplay(buildingReport);
  }


}
