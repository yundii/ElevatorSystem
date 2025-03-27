package building;

import static org.junit.Assert.assertEquals;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * This class tests the BuildingReport class.
 */
public class BuildingReportTest {
  BuildingReport report;

  /**
   * This method sets up the test fixture.
   */
  @Before
  public void setUp() {
    List<Request> upRequests = new ArrayList<>();
    upRequests.add(new Request(1, 3));
    List<Request> downRequests = new ArrayList<>();
    ElevatorReport[] elevatorReports = new ElevatorReport[1];
    elevatorReports[0] = new ElevatorReport(
        1, 0, Direction.STOPPED, true, new boolean[]{
            false, false, false}, 0, 5, false, false);
    report = new BuildingReport(
        3, 1, 3, elevatorReports, upRequests, downRequests, ElevatorSystemStatus.running);
  }

  /**
   * This method tests the getNumFloors method.
   */
  @Test
  public void getNumFloors() {
    assertEquals(3, report.getNumFloors());
  }

  /**
   * This method tests the getNumElevators method.
   */
  @Test
  public void getNumElevators() {
    assertEquals(1, report.getNumElevators());
  }

  /**
   * This method tests the getElevatorCapacity method.
   */
  @Test
  public void getElevatorCapacity() {
    assertEquals(3, report.getElevatorCapacity());
  }

  /**
   * This method tests the getElevatorReports method.
   */
  @Test
  public void getElevatorReports() {
    ElevatorReport[] elevatorReports = report.getElevatorReports();
    assertEquals(1, elevatorReports.length);
    assertEquals(1, elevatorReports[0].getElevatorId());
  }

  /**
   * This method tests the getUpRequests method.
   */
  @Test
  public void getUpRequests() {
    List<Request> upRequests = report.getUpRequests();
    assertEquals(1, upRequests.size());
  }

  /**
   * This method tests the getDownRequests method.
   */
  @Test
  public void getDownRequests() {
    List<Request> downRequests = report.getDownRequests();
    assertEquals(0, downRequests.size());
  }

  /**
   * This method tests the getSystemStatus method.
   */
  @Test
  public void getSystemStatus() {
    assertEquals(ElevatorSystemStatus.running, report.getSystemStatus());
  }

  /**
   * This method tests the toString method.
   */
  @Test
  public void testToString() {
    String expected = "BuildingReport{numFloors=3\n"
        + "numElevators=1\nelevatorCapacity=3\nelevatorReports=[Waiting[Floor 0, Time 5]]\n"
        + "upRequests=[1->3]\ndownRequests=[]\nsystemStatus=Running}";
    assertEquals(expected, report.toString());
  }

}
