package server.repository;

import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import server.entity.User;
import server.exceptions.BadRequestException;
import server.exceptions.ResourceNotFoundException;
import server.exceptions.ServerResponseException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserRepository implements Repository<User, Long>{

    private final SessionFactory sessionFactory;

    public UserRepository(){
        sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("server.entity");
    }
    public Optional<User> login(String email){
        try(Session session = sessionFactory.openSession()){
            return session.bySimpleNaturalId(User.class)
                    .loadOptional(email);
        }
    }

    @Override
    public Optional<User> find(Long id) {

        try (Session session = sessionFactory.openSession()) {
            var user = session.find(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class)
                    .getResultList();
        }
    }

    @Override
    public void create(User newInstance) throws BadRequestException {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(newInstance);
                tx.commit();
            } catch (RollbackException ignored) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new BadRequestException("User with email " + newInstance.getEmail() + " already exists");
            }
        }
    }
}
