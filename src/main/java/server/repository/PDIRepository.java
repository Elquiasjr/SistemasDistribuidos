package server.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import server.entity.PDI;
import server.exceptions.BadRequestException;
import server.exceptions.ResourceNotFoundException;
import server.exceptions.ServerResponseException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PDIRepository {
    private final SessionFactory sessionFactory;

    public PDIRepository() {
        this.sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("server.entity");
    }

    public void create(PDI newInstance) throws ServerResponseException {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(newInstance);
                transaction.commit();
            } catch(RollbackException e) {
                if(transaction != null) {
                    transaction.rollback();
                }
                throw new BadRequestException("Ponto com nome " + newInstance.getNome() + " já existe.");
            }
        }
    }

    public PDI update(Long id, PDI instance) throws ServerResponseException {
        try (Session session = sessionFactory.openSession()) {

            PDI pdi = session.byId(PDI.class)
                    .loadOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Ponto com id " + id + " não existe."));


            var pdiWithName = session.bySimpleNaturalId(PDI.class)
                    .loadOptional(instance.getNome());

            if (pdiWithName.isPresent() && !Objects.equals(pdiWithName.get().getId(), instance.getId())) {
                throw new BadRequestException("Ponto com id " + pdiWithName.get().getNome() + " já existe.");
            }


            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                pdi.update(instance);
                session.merge(pdi);
                tx.commit();
            } catch (RollbackException ignored) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new BadRequestException("PDI com nome " + pdi.getNome() + " já existe.");
            }
            return pdi;
        }
    }

    public Optional<PDI> find(Long id) {
        try(Session session = sessionFactory.openSession()) {
            var pdi = session.find(PDI.class, id);
            return Optional.ofNullable(pdi);
        }
    }

    public List<PDI> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PDI", PDI.class).getResultList();
        }
    }

    public void deleteById(Long id) {
        sessionFactory.inTransaction(session -> {
            PDI pdi = session.find(PDI.class, id);
            session.createMutationQuery("delete from PDI where id = :id")
                    .setParameter("id", pdi.getId())
                    .executeUpdate();
        });
    }
}
