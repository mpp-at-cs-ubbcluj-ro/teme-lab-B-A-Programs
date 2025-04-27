package repository;

import domain.Child;
import domain.Trial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class ChildDBRepository implements ChildRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Child add(Child child) {
        logger.traceEntry("Adding child: {}", child);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            session.persist(child);  // Persist the child entity to the database
        });

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Child where id=(select max(id) from Child)", Child.class).uniqueResult();
        }
    }

    @Override
    public void delete(Long id) {
        logger.traceEntry("Deleting child with id: {}", id);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            Child child = session.createQuery("from Child where id=?1", Child.class)
                    .setParameter(1, id)
                    .uniqueResult();
            if (child != null) {
                session.remove(child);  // Remove the child entity
            }
        });
    }

    @Override
    public void update(Child child, Long id) {
        logger.traceEntry("Updating child with id: {}", id);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            child.setId(id);
            session.merge(child);
        });
    }

    @Override
    public Child getById(Long id) {
        logger.traceEntry("Getting child by id: {}", id);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Child where id=?1", Child.class)
                    .setParameter(1, id)
                    .uniqueResult();
        }
    }

    @Override
    public List<Child> getAll() {
        logger.traceEntry("Getting all children");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Child", Child.class).getResultList();
        }
    }

    @Override
    public Child findByCnp(String cnp) {
        logger.traceEntry("Getting child by CNP: {}", cnp);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Child where CNP = :cnp", Child.class)
                    .setParameter("cnp", cnp)
                    .uniqueResult();
        }
    }

    @Override
    public List<Trial> getTrialsForChild(Child child) {
        logger.traceEntry("Getting trials by child: {}", child);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select t from Trial t join t.enrolledChildren c where c.id = :childId", Trial.class)
                    .setParameter("childId", child.getId())
                    .getResultList();
        }
    }
}
