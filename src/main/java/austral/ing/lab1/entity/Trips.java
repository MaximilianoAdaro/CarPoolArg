package austral.ing.lab1.entity;

import austral.ing.lab1.model.Trip;
import austral.ing.lab1.util.LangUtils;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Trips {

    public static Optional<Trip> findById(Long id) {
        return tx(() ->
                Optional.of(currentEntityManager().find(Trip.class, id))
        );
    }

    public static List<Trip> searchList(String param1) {
        return tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT t FROM Trip t WHERE t.to LIKE concat(:param,'%')")
                .setParameter("param", param1)
                .getResultList()
        ));
    }

    public static Optional<Trip> findByEmail(String email) {
        return tx(() -> LangUtils.<Trip>checkedList(currentEntityManager()
                .createQuery("SELECT t FROM Trip t WHERE t.from LIKE :email")
                .setParameter("email", email).getResultList()).stream()
                .findFirst()
        );
    }

    public static List<Trip> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT t FROM Trip t").getResultList())
        );
    }

    public static Trip persist(Trip trip) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(trip);

            tx.commit();
            return trip;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
