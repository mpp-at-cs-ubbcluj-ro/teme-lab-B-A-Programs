import networking.server.AbstractServer;
import networking.server.JsonConcurrentServer;
import repository.*;
import server.EnrollmentService;
import server.MasterService;
import server.TrialService;
import server.UserService;
import services.IMasterService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("FieldCanBeLocal")
public class Main {
    private static final int defaultServerPort = 55555;

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
            props.load(Main.class.getResourceAsStream("client.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config or client.properties " + e);
        }
        int serverPort = defaultServerPort;
        try {
            serverPort = Integer.parseInt(props.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultServerPort);
        }
        System.out.println("Using server port " + serverPort);
        UserRepository userRepository = new UserDBRepository();
		ChildRepository childRepository = new ChildDBRepository();
		TrialDBRepository trialRepository = new TrialDBRepository();
        UserService userService = new UserService(userRepository);
        EnrollmentService enrollmentService = new EnrollmentService(childRepository, trialRepository);
        TrialService trialService = new TrialService(trialRepository, childRepository);
        IMasterService service = new MasterService(enrollmentService, trialService, userService);
        AbstractServer server = new JsonConcurrentServer(serverPort, service);
        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
