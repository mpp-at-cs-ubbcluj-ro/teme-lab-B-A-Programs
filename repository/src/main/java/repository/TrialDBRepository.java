package repository;

import domain.AgeGroup;
import domain.Child;
import domain.Trial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class TrialDBRepository implements TrialRepository {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Trial add(Trial trial) {
        logger.traceEntry("Adding trial: {}", trial);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            session.persist(trial);
        });

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Trial where id=(select max(id) from Trial)", Trial.class).uniqueResult();
        }
    }

    @Override
    public void delete(Long id) {
        logger.traceEntry("Deleting trial with id: {}", id);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Trial trial = session.createQuery("from Trial where id=?1", Trial.class)
                    .setParameter(1, id)
                    .uniqueResult();
            if (trial != null) {
                session.remove(trial);
            }
        });
    }

    @Override
    public void update(Trial trial, Long id) {
        logger.traceEntry("Updating trial with id: {}", id);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            trial.setId(id);
            session.merge(trial);
        });
    }

    @Override
    public Trial getById(Long id) {
        logger.traceEntry("Getting trial by id: {}", id);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Load the trial entity
            Trial trial = session.get(Trial.class, id);

            // Trigger eager loading of enrolledChildren
            if (trial != null) {
                trial.getEnrolledChildren().size();  // Forces Hibernate to load the collection
            }

            return trial;
        }
    }

    @Override
    public List<Trial> getAll() {
        logger.traceEntry("Getting all trials");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Trial> trials = session.createQuery("from Trial", Trial.class).getResultList();

            for (Trial trial : trials) {
                System.out.println(trial);
                trial.getEnrolledChildren().size();  // Forces loading of enrolledChildren for each trial
            }

            return trials;
        }
    }

    @Override
    public void addChild(Long trialId, Child child) {
        logger.traceEntry("Enrolling child in trial: {}", child);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Trial trial = session.get(Trial.class, trialId);
            Child childEntity = session.get(Child.class, child.getId());
            if (trial != null && childEntity != null) {
                trial.getEnrolledChildren().add(childEntity);
                session.merge(trial);
            }
        });
    }
}
