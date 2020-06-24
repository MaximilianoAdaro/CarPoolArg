package austral.ing.lab1.entity;

import austral.ing.lab1.model.Rating;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.TripPassenger;
import austral.ing.lab1.model.User;
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

    public static List<Rating> ratingsUserAsDriver(User user) {
        List<Rating> rate = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT r FROM Rating r " +
                        " where r.isRated = false" +
                        " and r.idDriver = :user " +
                        "and r.isDriver = true")
                .setParameter("user", user)
                .getResultList()
        ));
        sortRating(rate);
        return rate;
    }

    public static List<Rating> ratingsUserAsPassenger(User user) {
        List<Rating> rate = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT r FROM Rating r " +
                        " where r.isRated = false" +
                        " and r.idPassenger = :user " +
                        "and r.isDriver = false")
                .setParameter("user", user)
                .getResultList()
        ));
        sortRating(rate);
        return rate;
    }

    private static void sortRating(List<Rating> ratings) {
        if (ratings.isEmpty()) return;
        ratings.sort((o1, o2) -> {
            if (o2.getIdTrip().getDate().equals(o1.getIdTrip().getDate()))
                return (o2.getIdTrip().getTime().before(o1.getIdTrip().getTime()) ? -1 : (o2.getIdTrip().getTime().after(o1.getIdTrip().getTime())) ? 1 : 0);
            return o2.getIdTrip().getDate().compareTo(o1.getIdTrip().getDate());
        });
    }

    public static Optional<Rating> findRate(Trip idTrip, User idDriver, User idPassenger, boolean isDriver) {
        return tx(() -> LangUtils.<Rating>checkedList(currentEntityManager()
                .createQuery("SELECT r FROM Rating r " +
                        "WHERE r.idTrip =  :idTrip" +
                        " AND r.idDriver = :idDriver" +
                        " AND r.isDriver =  :isDriver" +
                        " AND r.idPassenger = :idPassenger")
                .setParameter("idTrip", idTrip)
                .setParameter("idDriver", idDriver)
                .setParameter("isDriver", isDriver)
                .setParameter("idPassenger", idPassenger)
                .getResultList()).stream()
                .findFirst()
        );
    }

    public static void rate(Long idTrip, Long idDriver, Long idPassenger, boolean isDriver, int value) {

        try {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/lab1";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE ratings r " +
                    " SET r.VALUE = " + value +
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
                            false));
                    Ratings.persist(new Rating(trip, trip.getDriver(), passenger.getPassenger(), false, 0,
                            false));
                }
            }
        }
    }

    public static int rateUser(User user) {
        Double rating = 0.0;
        List<Integer> rate = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT r.value FROM Rating r " +
                        " where r.isRated = true" +
                        " and ((r.idPassenger = :user and r.isDriver = false)" +
                        " or (r.idDriver = :user and r.isDriver = true))")
                .setParameter("user", user)
                .getResultList()
        ));
        for (Integer aDouble : rate) {
            rating += aDouble;
        }
        if (!rate.isEmpty())
            rating /= rate.size();
        System.out.println("rating = " + rating);
        return rating.intValue();
    }

    public static int getSizeRate(User user) {
        List<Integer> rate = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT r.value FROM Rating r " +
                        " where r.isRated = true" +
                        " and ((r.idPassenger = :user and r.isDriver = false)" +
                        " or (r.idDriver = :user and r.isDriver = true))")
                .setParameter("user", user)
                .getResultList()
        ));
        return rate.size();
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
