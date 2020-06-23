package austral.ing.lab1.entity;

import austral.ing.lab1.model.Rating;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.TripPassenger;
import austral.ing.lab1.util.LangUtils;

import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Ratings {

    //todo: usar
    public static Optional<Rating> findRating(Long idTrip, Long idDriver, Long idPassenger, boolean isDriver) {
        return tx(() -> LangUtils.<Rating>checkedList(currentEntityManager()
                .createQuery("SELECT r FROM Rating r " +
                        "WHERE r.idTrip = :idTrip " +
                        "and r.idDriver = :driver " +
                        "and r.idPassenger = :passenger " +
                        "and r.isDriver = :isDriver")
                .setParameter("idTrip", idTrip)
                .setParameter("driver", idDriver)
                .setParameter("passenger", idPassenger)
                .setParameter("isDriver", isDriver).getResultList()).stream()
                .findFirst()
        );
    }

    public static void rate(Long idTrip, Long idDriver, Long idPassenger, boolean isDriver, int value, String message) {
        try {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/lab1";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE ratings r " +
                    " SET r.VALUE = " + value +
                    " and r.MESSAGE = " + message +
                    " and r.IS_RATED = true " +
                    " WHERE r.ID_TRIP = " + idTrip +
                    " AND r.ID_DRIVER = " + idDriver +
                    " AND r.IS_DRIVER = " + isDriver +
                    " AND r.ID_PASSENGER = " + idPassenger);
            st.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static Rating persist(Rating rating) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();
            currentEntityManager().persist(rating);
            tx.commit();
            return rating;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static void setRating() {
        List<Trip> lastTrips = tripsNotRated();
        for (Trip trip : lastTrips) {
            if (trip.getPassengers() != null) {
                for (TripPassenger passenger : trip.getPassengers()) {
                    Ratings.persist(new Rating(trip, trip.getDriver(), passenger.getPassenger(), true, 0,
                            "", false));
                    Ratings.persist(new Rating(trip, trip.getDriver(), passenger.getPassenger(), false, 0,
                            "", false));
                }
            }
        }
    }

    private static List<Trip> tripsNotRated() {
        List<Trip> rated = tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT r.idTrip FROM Rating r")
                        .getResultList())
        );
        List<Trip> before = Trips.listBeforeTrips();
        if (rated.size() >= before.size()) return new ArrayList<>();
        for (Trip tripRated : rated) {
            before.remove(tripRated);
        }
        return before;
    }

}
