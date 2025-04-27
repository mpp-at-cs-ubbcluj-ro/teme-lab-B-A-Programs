package repository;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class UserDBRepository implements UserRepository {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public User add(User user) {
        logger.traceEntry("Adding user: {}", user);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            session.persist(user);
        });

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User where id=(select max(id) from User)", User.class).uniqueResult();
        }
    }

    @Override
    public void delete(Long id) {
        logger.traceEntry("Deleting user with id: {}", id);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            User user = session.createQuery("from User where id=?1", User.class)
                    .setParameter(1, id)
                    .uniqueResult();
            if (user != null) {
                session.remove(user);
            }
        });
    }

    @Override
    public void update(User user, Long id) {
        logger.traceEntry("Updating user with id: {}", id);
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            user.setId(id);
            session.merge(user);
        });
    }

    @Override
    public User getById(Long id) {
        logger.traceEntry("Getting user by id: {}", id);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User where id=?1", User.class)
                    .setParameter(1, id)
                    .uniqueResult();
        }
    }

    @Override
    public User findByUsername(String username) {
        logger.traceEntry("Finding user by username: {}", username);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User where username=?1", User.class)
                    .setParameter(1, username)
                    .uniqueResult();
        }
    }

    @Override
    public List<User> getAll() {
        logger.traceEntry("Getting all users");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }
}
