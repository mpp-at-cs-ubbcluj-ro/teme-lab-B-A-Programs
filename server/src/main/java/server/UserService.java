package server;

import domain.User;
import repository.UserRepository;
import services.IUserService;

public class UserService {
    private UserRepository userRepository;
    private User authenticatedUser = null;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logIn(User user) throws Exception {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            authenticatedUser = existingUser;
            return;
        }

        throw new Exception("Invalid username or password");
    }
}
