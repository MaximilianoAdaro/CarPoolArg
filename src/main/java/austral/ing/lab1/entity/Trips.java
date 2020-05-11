package austral.ing.lab1.entity;

import austral.ing.lab1.model.Trip;
import austral.ing.lab1.util.EntityManagers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
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

    public static List<Trip> searchList(String fromTrip, String toTrip) {
        LocalDate date = LocalDate.now();
        List<Trip> trips = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT t " +
                        "FROM Trip t " +
                        "WHERE t.fromTrip LIKE :param " +
                        "and t.toTrip LIKE :param2 " +
                        "and t.date >= :localDate")
                .setParameter("localDate", date.toString())
                .setParameter("param", "%" + fromTrip + "%")
                .setParameter("param2", "%" + toTrip + "%")
                .getResultList()
        ));
        trips.sort((o1, o2) -> {
            if (o1.getDate().equals(o2.getDate()))
                return (o1.getTime().before(o2.getTime()) ? -1 : (o1.getTime().after(o2.getTime())) ? 1 : 0);
            return o1.getDate().compareTo(o2.getDate());
        });
        return trips;
    }

//    public static Optional<Trip> findByEmail(String email) {
//        return tx(() -> LangUtils.<Trip>checkedList(currentEntityManager()
//                .createQuery("SELECT t FROM Trip t WHERE t.fromTrip LIKE :email")
//                .setParameter("email", email).getResultList()).stream()
//                .findFirst()
//        );
//    }

    public static List<Trip> listCurrentTrips() {
        LocalDate date = LocalDate.now();
        List<Trip> trips = tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t FROM Trip t " +
                                "where t.date >= :localDate")
                        .setParameter("localDate", date.toString())
                        .getResultList())
        );
        trips.sort((o1, o2) -> {
            if (o1.getDate().equals(o2.getDate()))
                return (o1.getTime().before(o2.getTime()) ? -1 : (o1.getTime().after(o2.getTime())) ? 1 : 0);
            return o1.getDate().compareTo(o2.getDate());
        });
        return trips;
    }

    public static List<Trip> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT t FROM Trip t").getResultList()));
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

    public static void deleteTrip(Long id) {
        EntityManager em = EntityManagers.currentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Optional<Trip> trip = findById(id);
        trip.ifPresent(em::remove);
        tx.commit();
    }

}
