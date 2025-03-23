package mpp.problema5.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mpp.problema5.Domain.Child;
import mpp.problema5.Domain.Trial;
import mpp.problema5.Services.EnrollmentService;
import mpp.problema5.Services.TrialService;

import java.util.List;
import java.util.stream.Collectors;

public class MasterController {
    @FXML
    private TableView<Trial> trialTable;
    @FXML
    private TableColumn<Trial, String> trialNameColumn;
    @FXML
    private TableColumn<Trial, String> trialAgeGroupColumn;

    @FXML
    private TableView<Child> childTable;
    @FXML
    private TableColumn<Child, String> childNameColumn;
    @FXML
    private TableColumn<Child, String> childCNPColumn;

    @FXML
    private TextField childNameField;
    @FXML
    private TextField childCNPField;
    @FXML
    private Button enrollButton;

    private TrialService trialService;
    private EnrollmentService enrollmentService;

    private final ObservableList<Trial> trials = FXCollections.observableArrayList();
    private final ObservableList<Child> children = FXCollections.observableArrayList();

    public void setServices(EnrollmentService enrollmentService, TrialService trialService) {
        this.enrollmentService = enrollmentService;
        this.trialService = trialService;
        loadTrialData();
    }

    @FXML
    public void initialize() {
        // Initialize Trial Table
        trialNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        trialAgeGroupColumn.setCellValueFactory(new PropertyValueFactory<>("ageCategory"));
        trialTable.setItems(trials);
        trialTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Enable multi-selection

        // Initialize Child Table
        childNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        childCNPColumn.setCellValueFactory(new PropertyValueFactory<>("CNP"));
        childTable.setItems(children);

        // Add selection listener to update children table
        trialTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            List<Trial> selectedTrials = trialTable.getSelectionModel().getSelectedItems();
            if (selectedTrials.size() > 2) {
                trialTable.getSelectionModel().clearSelection();
                showAlert("Selection Error", "You can only select up to 2 trials.");
            } else if (!selectedTrials.isEmpty()) {
                loadChildrenData(selectedTrials);
            }
        });

        // Enroll button action
        enrollButton.setOnAction(event -> handleEnrollChild());
    }

    private void loadTrialData() {
        trials.setAll(trialService.getTrials());
    }

    private void loadChildrenData(List<Trial> selectedTrials) {
        children.clear();
        for (Trial trial : selectedTrials) {
            children.addAll(trial.getEnrolledChildren());
        }
    }

    private void handleEnrollChild() {
        String name = childNameField.getText().trim();
        String cnp = childCNPField.getText().trim();
        List<Trial> selectedTrials = trialTable.getSelectionModel().getSelectedItems();

        if (name.isEmpty() || cnp.isEmpty()) {
            showAlert("Input Error", "Child name and CNP cannot be empty.");
            return;
        }
        if (selectedTrials.isEmpty()) {
            showAlert("Selection Error", "Please select at least one trial.");
            return;
        }
        if (cnp.length() != 13) {
            showAlert("Input Error", "Invalid CNP.");
            return;
        }

        Child child = new Child(0L, cnp, name);

        // If child is already enrolled in a trial, he can be enrolled only in one more, so User should only select 1 trial
        if (enrollmentService.getChildEnrollmentsNumber(child) == 1 && selectedTrials.size() == 2) {
            showAlert("Selection Error", "Child can only be enrolled in one more trial.");
            return;
        }

        try {
            for (Trial trial : selectedTrials) {
                enrollmentService.enrollChild(trial.getId(), child);
            }
            showAlert("Success", "Child enrolled successfully.");
            childNameField.clear();
            childCNPField.clear();
            loadChildrenData(selectedTrials);
        } catch (Exception e) {
            showAlert("Enrollment Failed", e.getMessage());
        }

        // Reload data
        loadTrialData();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
