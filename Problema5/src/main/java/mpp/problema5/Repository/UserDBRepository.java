package mpp.problema5.Repository;

import mpp.problema5.Domain.User;
import mpp.problema5.Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDBRepository implements UserRepository {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public UserDBRepository(Properties props) {
        logger.info("Initializing UserDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public User add(User user) {
        logger.traceEntry("Adding user: {}", user);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO User (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            int result = stmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        logger.traceEntry("Deleting user with id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public void update(User user, Long id) {
        logger.traceEntry("Updating user with id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE User SET username = ?, password = ? WHERE id = ?")) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public User getById(Long id) {
        logger.traceEntry("Getting user by id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        logger.traceEntry("Finding user by username: {}", username);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        logger.traceEntry("Getting all users");
        Connection conn = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getLong("id"), rs.getString("username"), rs.getString("password")));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return users;
    }
}
