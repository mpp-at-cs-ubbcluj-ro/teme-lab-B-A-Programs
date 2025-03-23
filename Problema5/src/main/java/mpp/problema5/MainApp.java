package mpp.problema5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mpp.problema5.Controllers.LoginController;
import mpp.problema5.Repository.*;
import mpp.problema5.Services.EnrollmentService;
import mpp.problema5.Services.TrialService;
import mpp.problema5.Services.UserService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mpp/problema5/login-page.fxml"));
        Parent root = loader.load();

        UserRepository userRepository = new UserDBRepository(props);
        TrialRepository trialRepository = new TrialDBRepository(props);
        ChildRepository childRepository = new ChildDBRepository(props);
        UserService userService = new UserService(userRepository);
        EnrollmentService enrollmentService = new EnrollmentService(childRepository, trialRepository);
        TrialService trialService = new TrialService(trialRepository);

        LoginController controller = loader.getController();
        controller.setServices(userService, enrollmentService, trialService);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
