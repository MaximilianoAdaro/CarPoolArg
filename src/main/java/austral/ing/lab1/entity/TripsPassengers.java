package austral.ing.lab1.entity;

import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class TripsPassengers {

    public static void acceptPassenger(Long userId, Long tripId) {
        tx(() -> currentEntityManager()
                .createQuery("UPDATE TripPassenger t " +
                        "SET t.state = true" +
                        " WHERE t.trip.tripId = :tripId" +
                        " AND t.passenger.userId = :userId")
                .setParameter("tripId", tripId)
                .setParameter("userId", userId)
                .executeUpdate()
        );
    }

    public static void rejectPassenger(Long userId, Long tripId) {
        tx(() -> currentEntityManager()
                .createQuery("DELETE from TripPassenger t " +
                        " WHERE t.trip.tripId = :tripId" +
                        " AND t.passenger.userId = :userId")
                .setParameter("tripId", tripId)
                .setParameter("userId", userId)
                .executeUpdate()
        );
    }

    public static List<User> listPassengers(Trip trip) {
        return tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t.passenger FROM TripPassenger t " +
                                " where t.trip = :trip")
                        .setParameter("trip", trip)
                        .getResultList())
        );
    }

    public static List<TripsPassengers> listTrips(User user) {
        return tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t FROM TripPassenger t " +
                                " where t.trip.driver = :user" +
                                " and t.state = false")
                        .setParameter("user", user)
                        .getResultList())
        );
    }
}