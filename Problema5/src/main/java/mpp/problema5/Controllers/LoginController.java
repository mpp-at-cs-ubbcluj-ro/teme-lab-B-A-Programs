package mpp.problema5.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mpp.problema5.Domain.User;
import mpp.problema5.Services.EnrollmentService;
import mpp.problema5.Services.TrialService;
import mpp.problema5.Services.UserService;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserService userService;
    private EnrollmentService enrollmentService;
    private TrialService trialService;

    public void setServices(UserService userService, EnrollmentService enrollmentService, TrialService trialService) {
        this.userService = userService;
        this.enrollmentService = enrollmentService;
        this.trialService = trialService;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            userService.LogIn(new User(null, username, password));
            openMasterPage();
        } catch (Exception e) {
            showAlert("Login Failed", e.getMessage());
        }
    }

    private void openMasterPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mpp/problema5/master-page.fxml"));
            Parent root = loader.load();

            MasterController controller = loader.getController();
            controller.setServices(enrollmentService, trialService);

            Stage masterStage = new Stage();
            masterStage.setTitle("Master Page");
            masterStage.setScene(new Scene(root, 600, 400));
            masterStage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to open master page: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
