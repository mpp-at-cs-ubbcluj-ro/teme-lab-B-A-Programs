package repository;

import domain.User;

public interface UserRepository extends Repository<User, Long> {
    public User findByUsername(String username);
}
