package building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * This class is used to test the Building class.
 */
public class BuildingTest {
  private Building building;

  /**
   * This method is used to set up the test fixture.
   */
  @Before
  public void setUp() {
    building = new Building(10, 2, 5);
  }


  /**
   * Test the constructor exceptions.
   * Number of floors must be between 2 and 29.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buildingConstructorThrowsExceptionForInvalidNumberOfFloors() {
    System.out.println("Testing: buildingConstructorThrowsExceptionForInvalidNumberOfFloors");
    Building building = new Building(1, 5, 4);
  }

  /**
   * Test the constructor exceptions.
   * Number of elevators must be greater than or equal to 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buildingConstructorThrowsExceptionForInvalidNumberOfElevators() {
    System.out.println("Testing: buildingConstructorThrowsExceptionForInvalidNumberOfElevators");
    Building building = new Building(10, 0, 4);
  }

  /**
   * Test the constructor exceptions.
   * Elevator capacity must be between 3 and 20.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buildingConstructorThrowsExceptionForInvalidElevatorCapacity() {
    System.out.println("Testing: buildingConstructorThrowsExceptionForInvalidElevatorCapacity");
    Building building = new Building(10, 5, 2);
  }

  /**
   * Test the constructor.
   */
  @Test
  public void buildingConstructor() {
    System.out.println("Testing: buildingConstructor");
    Building building = new Building(10, 5, 4);
    assertEquals(10, building.getNumberOfFloors());
    assertEquals(5, building.getNumberOfElevators());
    assertEquals(4, building.getElevatorCapacity());
    assertEquals(ElevatorSystemStatus.outOfService, building.getBuildingStatus());
    assertEquals(0, building.getUpRequests().size());
    assertEquals(0, building.getDownRequests().size());
  }

  /**
   * Test the getNumberOfFloors method.
   */
  @Test
  public void getNumberOfFloors() {
    System.out.println("Testing: getNumberOfFloors");
    assertEquals(10, building.getNumberOfFloors());
  }

  /**
   * Test the getNumberOfElevators method.
   */
  @Test
  public void getNumberOfElevators() {
    System.out.println("Testing: getNumberOfElevators");
    assertEquals(2, building.getNumberOfElevators());
  }

  /**
   * Test the getElevatorCapacity method.
   */
  @Test
  public void getElevatorCapacity() {
    System.out.println("Testing: getElevatorCapacity");
    assertEquals(5, building.getElevatorCapacity());
  }

  /**
   * Test the getBuildingStatus method.
   */
  @Test
  public void getBuildingStatus() {
    System.out.println("Testing: getBuildingStatus");
    assertEquals(ElevatorSystemStatus.outOfService, building.getBuildingStatus());
  }

  /**
   * Test the getUpRequests method.
   */
  @Test
  public void getUpRequests() {
    System.out.println("Testing: getUpRequests");
    assertEquals(0, building.getUpRequests().size());
  }

  /**
   * Test the getDownRequests method.
   */
  @Test
  public void getDownRequests() {
    System.out.println("Testing: getDownRequests");
    assertEquals(0, building.getDownRequests().size());
  }

  /**
   * Test the getElevators method.
   */
  @Test
  public void getElevators() {
    System.out.println("Testing: getElevators");
    Elevator[] elevators = building.getElevators();
    assertEquals(2, elevators.length);
  }

  /**
   * Test the startElevatorSystem method.
   * When elevator system is out of service.
   */
  @Test
  public void testStartElevatorSystemWhenOutOfService() {
    System.out.println("Testing: testStartElevatorSystemWhenOutOfService");
    building.startElevatorSystem();
    Elevator[] elevators = building.getElevators();

    for (Elevator elevator : elevators) {
      assertTrue(elevator.isTakingRequests());
    }
    assertEquals(ElevatorSystemStatus.running, building.getBuildingStatus());
  }

  /**
   * Test the startElevatorSystem method.
   * When elevator system is stopping.
   */
  @Test
  public void testStartElevatorSystemWhenStopping() {
    try {
      System.out.println("Testing: testStartElevatorSystemWhenStopping");
      building.startElevatorSystem();
      building.stopElevatorSystem();
      building.startElevatorSystem();
    } catch (IllegalStateException e) {
      assertEquals("Elevator system cannot be started until it is out of service", e.getMessage());
    }
  }

  /**
   * Test the stopElevatorSystem method.
   * When elevator system is running.
   */
  @Test
  public void testStopElevatorSystemWhenRunning() {
    System.out.println("Testing: testStopElevatorSystemWhenRunning");
    building.startElevatorSystem();
    building.stopElevatorSystem();
    Elevator[] elevators = building.getElevators();
    for (Elevator elevator : elevators) {
      assertFalse(elevator.isTakingRequests());
    }
    assertEquals(ElevatorSystemStatus.stopping, building.getBuildingStatus());
  }

  /**
   * Test the stopElevatorSystem method.
   * When elevator system is out of service.
   */
  @Test
  public void testStopElevatorSystemWhenOutOfService() {
    try {
      System.out.println("Testing: testStopElevatorSystemWhenOutOfService");
      building.stopElevatorSystem();
    } catch (IllegalStateException e) {
      assertEquals("Elevator system cannot be stopped until it is running", e.getMessage());
    }
  }

  /**
   * Test the stopElevatorSystem method.
   * When a elevator has a door open.
   */
  @Test
  public void testStopElevatorSystemWhenDoorOpen() {
    System.out.println("Testing: testStopElevatorSystemWhenDoorOpen");
    building.startElevatorSystem();
    building.addRequest(new Request(2, 3));
    for (int i = 0; i < 4; i++) {
      building.stepElevatorSystem();
    }

    Elevator[] elevators = building.getElevators();

    // Elevator 0 comes to floor 2, opens the door for 3 steps.
    assertEquals(2, elevators[0].getCurrentFloor());
    building.stopElevatorSystem();
    for (Elevator elevator : elevators) {
      assertFalse(elevator.isTakingRequests());
    }
    assertEquals(ElevatorSystemStatus.stopping, building.getBuildingStatus());

  }

  /**
   * Test the getBuildingReport method.
   */
  @Test
  public void testGetBuildingReport() {
    System.out.println("Testing: testGetBuildingReport");
    BuildingReport buildingReport = building.getBuildingReport();
    assertEquals(10, buildingReport.getNumFloors());
    assertEquals(2, buildingReport.getNumElevators());
    assertEquals(5, buildingReport.getElevatorCapacity());
    assertEquals(ElevatorSystemStatus.outOfService, buildingReport.getSystemStatus());
    assertEquals(0, buildingReport.getUpRequests().size());
    assertEquals(0, buildingReport.getDownRequests().size());
  }

  /**
   * Test the addRequest method throw exception.
   * When the request is null.
   */
  @Test
  public void testAddRequestThrowsExceptionForNullRequest() {
    try {
      System.out.println("Testing: testAddRequestThrowsExceptionForNullRequest");
      building.startElevatorSystem();
      building.addRequest(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Request cannot be null", e.getMessage());
    }
  }

  /**
   * Test the addRequest method throw exception.
   * When Start floor and end floor is the same.
   */
  @Test
  public void testAddRequestThrowsExceptionForSameStartAndEndFloor() {
    try {
      System.out.println("Testing: testAddRequestThrowsExceptionForSameStartAndEndFloor");
      building.startElevatorSystem();
      building.addRequest(new Request(0, 0));
    } catch (IllegalArgumentException e) {
      assertEquals("Start floor and end floor cannot be the same", e.getMessage());
    }
  }

  /**
   * Test the addRequest method throw exception.
   * When start floor is less than 0.
   */
  @Test
  public void testAddRequestThrowsExceptionForStartFloorLessThanZero() {
    try {
      System.out.println("Testing: testAddRequestThrowsExceptionForStartFloorLessThanZero");
      building.startElevatorSystem();
      building.addRequest(new Request(-1, 2));
    } catch (IllegalArgumentException e) {
      assertEquals("Start floor must be between 0 and 9", e.getMessage());
    }
  }

  /**
   * Test the addRequest method throw exception.
   * When end floor is greater than or equal to the number of floors.
   */
  @Test
  public void testAddRequestThrowsExceptionForEndFloorGreaterThanNumberOfFloors() {
    try {
      System.out.println(
          "Testing: testAddRequestThrowsExceptionForEndFloorGreaterThanNumberOfFloors");
      building.startElevatorSystem();
      building.addRequest(new Request(0, 10));
    } catch (IllegalArgumentException e) {
      assertEquals("End floor must be between 0 and 9", e.getMessage());
    }
  }

  /**
   * Test the addRequest method throw exception.
   * When elevator system is out of service.
   */
  @Test
  public void testAddRequestThrowsExceptionForElevatorSystemOutOfService() {
    try {
      System.out.println("Testing: testAddRequestThrowsExceptionForElevatorSystemOutOfService");
      building.addRequest(new Request(0, 2));
    } catch (IllegalStateException e) {
      assertEquals(
          "Elevator system not accepting requests in this state: Out Of Service", e.getMessage());
    }
  }


  /**
   * Test the addRequest method.
   */
  @Test
  public void testAddRequest() {
    System.out.println("Testing: testAddRequest");
    building.startElevatorSystem();
    building.addRequest(new Request(0, 2));
    building.addRequest(new Request(6, 3));
    assertEquals(1, building.getUpRequests().size());
    assertEquals(1, building.getDownRequests().size());
  }
  /**
   * Test the addRequest method correctly store up and down requests.
   * in the order that it receives them.
   */

  @Test
  public void testAddRequestCorrectlyStoreRequests() {
    System.out.println("Testing: testAddRequestCorrectlyStoreUpRequests");
    building.startElevatorSystem();
    building.addRequest(new Request(0, 2));
    building.addRequest(new Request(3, 6));
    building.addRequest(new Request(4, 1));
    building.addRequest(new Request(7, 5));
    assertEquals("0->2", building.getUpRequests().get(0).toString());
    assertEquals("3->6", building.getUpRequests().get(1).toString());
    assertEquals("4->1", building.getDownRequests().get(0).toString());
    assertEquals("7->5", building.getDownRequests().get(1).toString());
  }

  /**
   * Test the stepElevatorSystem  method will never allocate more requests.
   * to an elevator than its max capacity.
   */
  @Test
  public void testStepElevatorSystemWillNeverAllocateMoreRequests() {
    System.out.println("Testing: testStepElevatorSystemWillNeverAllocateMoreRequests");
    building.startElevatorSystem();
    building.addRequest(new Request(1, 2));
    building.addRequest(new Request(2, 4));
    building.addRequest(new Request(4, 6));
    building.addRequest(new Request(6, 8));
    building.addRequest(new Request(4, 8));
    building.addRequest(new Request(3, 7));
    building.stepElevatorSystem();
    Elevator[] elevators = building.getElevators();
    assertEquals(1, elevators[0].getCurrentFloor());
    // Elevator 0 is allocated 5 requests.
    assertTrue(elevators[0].getFloorRequests()[1]);
    assertTrue(elevators[0].getFloorRequests()[2]);
    assertFalse(elevators[0].getFloorRequests()[3]);
    assertTrue(elevators[0].getFloorRequests()[4]);
    assertTrue(elevators[0].getFloorRequests()[6]);
    assertFalse(elevators[0].getFloorRequests()[7]);
    assertTrue(elevators[0].getFloorRequests()[8]);

    // Elevator 1 is allocated 1 request.
    assertTrue(elevators[1].getFloorRequests()[3]);
    assertTrue(elevators[1].getFloorRequests()[7]);
  }


  /**
   * Test the stepElevatorSystem method.
   */
  @Test
  public void testStepElevatorSystem() {
    System.out.println("Testing: testStepElevatorSystem");
    building.startElevatorSystem();
    building.addRequest(new Request(1, 2));
    building.stepElevatorSystem();
    Elevator[] elevators = building.getElevators();
    assertEquals(1, elevators[0].getCurrentFloor());
    assertEquals(Direction.UP, elevators[0].getDirection());
    for (int i = 0; i < 5; i++) {
      building.stepElevatorSystem();
    }
    // Elevator 0 opens the door for 3 steps at floor 1.
    // closes the door for 1 step, and moves to floor 2.
    assertEquals(2, elevators[0].getCurrentFloor());
    // Elevator 1 waits for 5 steps at floor 0, then moves to floor 1.
    assertEquals(1, elevators[1].getCurrentFloor());

  }

  /**
   * Test the stepElevatorSystem method.
   * When elevator system is stopping.
   */
  @Test
  public void testStepElevatorSystemWhenStopping() {
    System.out.println("Testing: testStepElevatorSystemWhenStopping");
    building.startElevatorSystem();
    building.stopElevatorSystem();
    building.stepElevatorSystem();
    Elevator[] elevators = building.getElevators();
    assertEquals(0, elevators[0].getCurrentFloor());
    assertEquals(0, elevators[1].getCurrentFloor());
    assertEquals(ElevatorSystemStatus.outOfService, building.getBuildingStatus());
  }
}
