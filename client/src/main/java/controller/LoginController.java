package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IMasterService;
import services.IObserver;
import domain.User;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private IMasterService service;
    public IObserver user;

    Parent mainParent;

    public void setParent(Parent p){
        mainParent=p;
    }

    public void setService(IMasterService service) {
        this.service = service;
    }

    public void setUser(IObserver user) {
        this.user = user;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            service.logIn(new User(null, username, password), user);
            openMasterPage();
        } catch (Exception e) {
            showAlert("Login Failed", e.getMessage());
        }
    }

    private void openMasterPage() {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(mainParent);

            stage.setTitle("User menu");
            stage.setScene(scene);
            stage.show();
            user.notifyEnrollment();
            ((Stage) (usernameField.getScene().getWindow())).close();
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
