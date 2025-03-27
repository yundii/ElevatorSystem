package view;

import building.Building;
import building.BuildingReport;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import scanerzus.Request;

/**
 * View component in the MVC architecture of the Building Elevator System.
 * This class defines the visual.
 */
public class BuildingView extends JFrame {
  private Building model;
  private JTextArea displayArea;
  private JButton startButton;
  private JButton stepButton;
  private JButton stopButton;
  private JButton requestButton;
  private JTextField startFloorField;
  private JTextField endFloorField;
  private BuildingViewDisplay viewDisplay;
  private boolean systemStarted = false;

  /**
   * Constructor for the BuildingView class.
   *
   * @param model the building model
   */
  public BuildingView(Building model) {
    this.model = model;
    setTitle("Elevator Control System");
    setSize(1000, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Main panel to hold components
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // Top panel to display elevator system parameters
    JPanel topPanel = new JPanel();
    topPanel.add(new JLabel(
        "Floors: " + model.getNumberOfFloors()
            + ", Elevators: " + model.getNumberOfElevators()
            + ", Capacity: " + model.getElevatorCapacity()));
    mainPanel.add(topPanel, BorderLayout.NORTH);

    // Display area to show building report
    displayArea = new JTextArea(20, 40);
    displayArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(displayArea);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    // Bottom panel for buttons
    JPanel bottomPanel;
    bottomPanel = new JPanel();
    startButton = new JButton("Start");
    stepButton = new JButton("Step");
    stopButton = new JButton("Stop");

    // Request panel
    requestButton = new JButton("Request");
    startFloorField = new JTextField(5);
    endFloorField = new JTextField(5);

    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(
            null,
            "Would you like to start the elevator system? (y/n)",
            "Start Elevator System", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
          model.startElevatorSystem();
          systemStarted = true;
          updateDisplay();
        }
      }
    });

    stepButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!systemStarted) {
          JOptionPane.showMessageDialog(
              null,
              "Please start the elevator system first",
              "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
        model.stepElevatorSystem();
        updateDisplay();
      }
    });

    stopButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!systemStarted) {
          JOptionPane.showMessageDialog(
              null, "Please start the elevator system first",
              "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
        model.stopElevatorSystem();
        updateDisplay();
      }
    });

    requestButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!systemStarted) {
          JOptionPane.showMessageDialog(null,
              "Please start the elevator system first", "Error",
              JOptionPane.ERROR_MESSAGE);
          return;
        }
        try {
          int startFloor = Integer.parseInt(startFloorField.getText());
          int endFloor = Integer.parseInt(endFloorField.getText());
          model.addRequest(new Request(startFloor, endFloor));
          updateDisplay();
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(
              null, "Invalid floor input",
              "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    bottomPanel.add(startButton);
    bottomPanel.add(stepButton);
    bottomPanel.add(stopButton);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    // Request panel
    JPanel requestPanel = new JPanel();
    requestPanel.add(new JLabel("Start Floor: "));
    requestPanel.add(startFloorField);
    requestPanel.add(new JLabel("End Floor: "));
    requestPanel.add(endFloorField);
    requestPanel.add(requestButton);
    mainPanel.add(requestPanel, BorderLayout.EAST);

    add(mainPanel);
    setVisible(true);
  }

  /**
   * Updates the display area with the current building report.
   */
  public void updateDisplay() {
    BuildingReport buildingReport = model.getBuildingReport();
    viewDisplay = new BuildingViewDisplay();
    displayArea.setText(viewDisplay.display(buildingReport));
  }

}
