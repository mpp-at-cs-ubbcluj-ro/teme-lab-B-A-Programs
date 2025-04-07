package repository;

import domain.AgeGroup;
import domain.Child;
import domain.Trial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ChildDBRepository implements ChildRepository {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ChildDBRepository(Properties props) {
        logger.info("Initializing ChildDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Child add(Child child) {
        logger.traceEntry("Adding child: {}", child);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Child (CNP, name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, child.getCNP());
            stmt.setString(2, child.getName());
            int result = stmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        child.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return child;
    }

    @Override
    public void delete(Long id) {
        logger.traceEntry("Deleting child with id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Child WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public void update(Child child, Long id) {
        logger.traceEntry("Updating child with id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Child SET CNP = ?, name = ? WHERE id = ?")) {
            stmt.setString(1, child.getCNP());
            stmt.setString(2, child.getName());
            stmt.setLong(3, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public Child getById(Long id) {
        logger.traceEntry("Getting child by id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Child WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Child(rs.getLong("id"), rs.getString("CNP"), rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public List<Child> getAll() {
        logger.traceEntry("Getting all children");
        Connection conn = dbUtils.getConnection();
        List<Child> children = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Child")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    children.add(new Child(rs.getLong("id"), rs.getString("CNP"), rs.getString("name")));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return children;
    }

    @Override
    public Child findByCnp(String cnp) {
        logger.traceEntry("Getting child by cnp: {}", cnp);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Child WHERE CNP = ?")) {
            stmt.setString(1, cnp);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Child(rs.getLong("id"), rs.getString("CNP"), rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public List<Trial> getTrialsForChild(Child child) {
        logger.traceEntry("Getting trials by child: {}", child);
        Connection conn = dbUtils.getConnection();
        List<Trial> trials = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Trial INNER JOIN Child_Trial CT on Trial.id = CT.trial_id WHERE CT.child_id = ?"
        )) {
            stmt.setLong(1, child.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    trials.add(new Trial(rs.getLong("id"), rs.getString("name"), AgeGroup.valueOf(rs.getString("age_group").toUpperCase()), new ArrayList<>()));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return trials;
    }
}
