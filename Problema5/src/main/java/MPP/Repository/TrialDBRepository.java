package MPP.Repository;

import MPP.Domain.Child;
import MPP.Domain.Trial;
import MPP.Domain.AgeGroup;
import MPP.Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TrialDBRepository implements TrialRepository {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public TrialDBRepository(Properties props) {
        logger.info("Initializing TrialDBRepository with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Trial add(Trial trial) {
        logger.traceEntry("Adding trial: {}", trial);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Trial (name, age_group) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, trial.getName());
            stmt.setString(2, trial.getAgeCategory().toString());
            int result = stmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        trial.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return trial;
    }

    @Override
    public void delete(Long id) {
        logger.traceEntry("Deleting trial with id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Trial WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public void update(Trial trial, Long id) {
        logger.traceEntry("Updating trial with id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Trial SET name = ?, age_group = ? WHERE id = ?")) {
            stmt.setString(1, trial.getName());
            stmt.setString(2, trial.getAgeCategory().toString());
            stmt.setLong(3, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public Trial getById(Long id) {
        logger.traceEntry("Getting trial by id: {}", id);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Trial WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Trial(rs.getLong("id"), rs.getString("name"), AgeGroup.valueOf(rs.getString("age_group").toUpperCase()), new ArrayList<>());
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public List<Trial> getAll() {
        logger.traceEntry("Getting all trials");
        Connection conn = dbUtils.getConnection();
        List<Trial> trials = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Trial")) {
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

    @Override
    public void addChild(Trial trial, Child child) {
        logger.traceEntry("Enrolling child in trial: {}", child);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Child_Trial (child_id, trial_id) VALUES (?, ?)")) {
            stmt.setLong(1, child.getId());
            stmt.setLong(2, trial.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}
