package mpp.problema5.Repository;

import mpp.problema5.Domain.User;

public interface UserRepository extends Repository<User, Long> {
    public User findByUsername(String username);
}
