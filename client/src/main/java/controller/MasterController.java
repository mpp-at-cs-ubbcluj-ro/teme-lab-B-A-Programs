package controller;

import domain.DTO.ChildDTO;
import domain.DTO.TrialDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import domain.Child;
import services.IMasterService;
import services.IObserver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MasterController implements IObserver {
    @FXML
    private TableView<TrialDTO> trialTable;
    @FXML
    private TableColumn<TrialDTO, String> trialNameColumn;
    @FXML
    private TableColumn<TrialDTO, String> trialAgeGroupColumn;

    @FXML
    private TableView<ChildDTO> childTable;
    @FXML
    private TableColumn<ChildDTO, String> childNameColumn;
    @FXML
    private TableColumn<ChildDTO, String> childCNPColumn;

    @FXML
    private TextField childNameField;
    @FXML
    private TextField childCNPField;
    @FXML
    private Button enrollButton;
    @FXML
    private Button logoutButton;

    private IMasterService masterService;

    private final ObservableList<TrialDTO> trials = FXCollections.observableArrayList();
    private final ObservableList<ChildDTO> children = FXCollections.observableArrayList();

    public void setService(IMasterService masterService) {
        this.masterService = masterService;
    }

    @FXML
    public void initialize() {
        // Initialize Trial Table
        trialNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        trialAgeGroupColumn.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));
        trialTable.setItems(trials);
        trialTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Enable multi-selection

        // Initialize Child Table
        childNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        childCNPColumn.setCellValueFactory(new PropertyValueFactory<>("CNP"));
        childTable.setItems(children);

        // Add selection listener to update children table
        trialTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            List<TrialDTO> selectedTrials = trialTable.getSelectionModel().getSelectedItems();
            if (selectedTrials.size() > 2) {
                trialTable.getSelectionModel().clearSelection();
                showAlert("Selection Error", "You can only select up to 2 trials.");
            } else if (!selectedTrials.isEmpty()) {
                loadChildrenData(selectedTrials);
            }
        });

        // Enroll button action
        enrollButton.setOnAction(event -> handleEnrollChild());
        logoutButton.setOnAction(event -> handleLogout());
    }

    private void handleLogout() {
        // Get the current window and close it
        try {
            masterService.logOut(this);
        } catch (Exception e) {
            System.out.println("Log Out Error");
            return;
        }
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    private void loadTrialData() {
        try {
            trials.setAll(masterService.getTrials());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadChildrenData(List<TrialDTO> selectedTrials) {
        children.clear();
        Set<ChildDTO> uniqueChildren = new HashSet<>();

        for (TrialDTO trial : selectedTrials) {
            uniqueChildren.addAll(trial.getEnrolledChildren());
        }

        children.setAll(uniqueChildren);
    }

    private void handleEnrollChild() {
        String name = childNameField.getText().trim();
        String cnp = childCNPField.getText().trim();
        List<TrialDTO> selectedTrials = trialTable.getSelectionModel().getSelectedItems();

        if (cnp.isEmpty()) {
            showAlert("Input Error", "Child CNP cannot be empty.");
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

        ChildDTO child = null;
        for (TrialDTO trial: trials) {
            for (ChildDTO c : trial.getEnrolledChildren()) {
                if (c.getCNP().equals(cnp)) {
                    child = c;
                    break;
                }
            }
        }

        if (child == null && name.isEmpty()) {
            showAlert("Selection Error", "Child not in database please insert name.");
            return;
        }

        // If child is already enrolled in a trial, he can be enrolled only in one more, so User should only select 1 trial
        if (child != null && child.getEnrollments() == 1 && selectedTrials.size() == 2) {
            showAlert("Selection Error", "Child can only be enrolled in one more trial.");
            return;
        }

        try {
            for (TrialDTO trial : selectedTrials) {
                masterService.enrollChild(trial.getId(), new Child(null, cnp, name));
            }
            showAlert("Success", "Child enrolled successfully.");
            childNameField.clear();
            childCNPField.clear();
            loadChildrenData(selectedTrials);
        } catch (Exception e) {
            showAlert("Enrollment Failed", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void notifyEnrollment() {
        Platform.runLater(() ->
        {
            try {
                loadTrialData();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
