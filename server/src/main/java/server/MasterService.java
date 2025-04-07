package server;

import domain.Child;
import domain.DTO.TrialDTO;
import domain.User;
import services.IMasterService;
import services.IObserver;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MasterService implements IMasterService {
    private final EnrollmentService enrollmentService;
    private final TrialService trialService;
    private final UserService userService;

    private final Map<String, IObserver> loggedUsers;

    public MasterService(EnrollmentService enrollmentService, TrialService trialService, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.trialService = trialService;
        this.userService = userService;
        this.loggedUsers = new ConcurrentHashMap<>();
    }

    @Override
    public void enrollChild(long trialId, Child child) throws Exception {
        enrollmentService.enrollChild(trialId, child);
        notifyEnrollment();
    }

    @Override
    public List<TrialDTO> getTrials() throws Exception {
        return trialService.getTrials();
    }

    @Override
    public void logIn(User user, IObserver observer) throws Exception {
        userService.logIn(user);
        loggedUsers.put(user.getUsername(), observer);
    }

    @Override
    public void logOut(IObserver observer) throws Exception {
        String username = null;
        for (Map.Entry<String, IObserver> entry : loggedUsers.entrySet())
            if (Objects.equals(observer, entry.getValue()))
                username = entry.getKey();
        if (username == null)
            throw new Exception("User not logged in");
        loggedUsers.remove(username);
    }

    private final int defaultThreadsNo=3;
    private void notifyEnrollment() throws Exception {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        System.out.println(loggedUsers.toString());
        for (IObserver observer : loggedUsers.values()) {
            executor.execute(observer::notifyEnrollment);
        }

        executor.shutdown();
    }
}
