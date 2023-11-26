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
                throw new BadRequestException(409, "User with email " + newInstance.getEmail() + " already exists");
            }
        }
    }

    public boolean userRegistred(Long registro){
        try (Session session = sessionFactory.openSession()) {
            long quantity = session.createSelectionQuery("select count(*) from User user where user.registro = :registro", Long.class)
                    .setParameter("registro", registro)
                    .uniqueResult();
            return quantity != 0;
        }
    }

    @Override
    public void delete(User instance) {
        sessionFactory.inTransaction(session -> session.remove(instance));
    }

    public boolean tryDelete(Long id){
        try(var session = sessionFactory.openSession()){
            long numberOfAdmins = session.createSelectionQuery("select count(*) from User user where user.tipo = true", Long.class)
                .uniqueResult();
            if(numberOfAdmins < 2){
                return false;
            }
            deleteById(id);
            return true;
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        sessionFactory.inTransaction(session -> {
            User user = session.find(User.class, id);
            session.createMutationQuery("delete from User where id = :id")
                    .setParameter("id", user.getRegistro())
                    .executeUpdate();
        });
    }

    public boolean uniqueAdmin(){
        try(Session session = sessionFactory.openSession()) {
            long quantAdmins = session.createSelectionQuery("select count(*) from User user where user.tipo = true", Long.class)
                    .uniqueResult();
            return quantAdmins == 1;
        }
    }
    @Override
    public User update(Long id, User instance) throws ServerResponseException {
        try (Session session = sessionFactory.openSession()) {
            User user = session.byId(User.class)
                    .loadOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario com id: " + id + " não existe"));

            var userWithEmail = session.bySimpleNaturalId(User.class)
                    .loadOptional(instance.getEmail());

            if(userWithEmail.isPresent() && !Objects.equals(userWithEmail.get().getRegistro(), instance.getRegistro())){
                throw new BadRequestException("usuario com email " + userWithEmail.get().getEmail() + " ja existe");
            }

            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                user.update(instance);
                session.merge(user);
                tx.commit();
            } catch (RollbackException ignored){
                if(tx != null) {
                    tx.rollback();
                }
                throw new BadRequestException("user with email " + user.getEmail() + " already exists");
            }
            return user;
        }
    }
}
