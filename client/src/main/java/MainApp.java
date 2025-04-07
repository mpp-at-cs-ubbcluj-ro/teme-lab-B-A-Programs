import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.*;
import services.IMasterService;
import networking.proxy.MasterJsonProxy;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static final int defaultServerPort = 55555;
    @SuppressWarnings("FieldCanBeLocal")
    private static final String defaultServerIP = "localhost";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-page.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Login");
        LoginController loginController = loader.getController();

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("master-page.fxml"));

        Properties props = new Properties();
        try {
            props.load(MainApp.class.getResourceAsStream("client.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config or client.properties " + e);
        }
        String serverIP = props.getProperty("server.host", defaultServerIP);
        int serverPort = defaultServerPort;

        try {
            serverPort = Integer.parseInt(props.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultServerPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        Parent parent = loader2.load();
        MasterController masterController = loader2.getController();
        IMasterService service = new MasterJsonProxy(serverIP, serverPort);
        loginController.setService(service);
        loginController.setUser(masterController);
        loginController.setParent(parent);
        masterController.setService(service);
        stage.show();
    }
}
