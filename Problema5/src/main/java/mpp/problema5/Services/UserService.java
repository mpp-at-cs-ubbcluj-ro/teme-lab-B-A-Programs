package mpp.problema5.Services;

import mpp.problema5.Domain.User;
import mpp.problema5.Repository.UserRepository;

public class UserService {
    private UserRepository userRepository;
    private User authenticatedUser = null;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user
     * @param user the user to be authenticated
     */
    public void LogIn(User user) throws Exception {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            authenticatedUser = existingUser;
            return;
        }

        throw new Exception("Invalid username or password");
    }
}
