package austral.ing.lab1.entity;

import austral.ing.lab1.model.Trip;

import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public static List<Trip> searchList(String fromTrip, String toTrip, Long driverID) {
        List<Trip> trips = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT t FROM Trip t " +
                        "left join TripPassenger tp on t.tripId = tp.trip.tripId" +
                        " where tp.passenger is NULL" +
                        " and tp.trip is NULL" +
                        " and t.fromTrip LIKE :param " +
                        " and t.toTrip LIKE :param2 " +
                        " and t.availableSeats > 0 " +
                        " and t.driver.userId <> :driverID")
                .setParameter("param", "%" + fromTrip + "%")
                .setParameter("param2", "%" + toTrip + "%")
                .setParameter("driverID", driverID)
                .getResultList()
        ));
        sortTrip(trips);
        removeBeforeTrips(trips);
        removeSameDayButBeforeHour(trips);

        return trips;
    }

    public static List<Trip> listDriverTrips(Long driverID, boolean nextTrip) {
        List<Trip> trips = tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t " +
                                " FROM Trip t " +
                                " where t.driver.userId = :driverID")
                        .setParameter("driverID", driverID)
                        .getResultList())
        );
        sortTrip(trips);
        if (nextTrip) {
            removeBeforeTrips(trips);
            removeSameDayButBeforeHour(trips);
        } else {
            removeAfterTrips(trips);
            removeSameDayButAfterHour(trips);
        }

        return trips;
    }

    public static List<Trip> listPassengerTrips(Long driverID, boolean nextTrip) {
        List<Trip> trips = new ArrayList<>();
        try {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/lab1";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT j.TRIP_ID " +
                            "FROM TRIPS_PASSENGERS j " +
                            "where PASSENGER_ID = " + driverID);
            while (rs.next()) {
                int id = rs.getInt("TRIP_ID");
                Optional<Trip> optionalTrip = Trips.findById((long) id);
                optionalTrip.ifPresent(trips::add);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        sortTrip(trips);
        if (nextTrip) {
            removeBeforeTrips(trips);
            removeSameDayButBeforeHour(trips);
        } else {
            removeAfterTrips(trips);
            removeSameDayButAfterHour(trips);
        }

        return trips;
    }

/*  "SELECT t FROM Trip t " +
    "left join TripPassenger tp on t.tripId = tp.trip.tripId" +
    " where tp.passenger is NULL" +
    " and tp.trip is NULL" +
    " and t.availableSeats > 0 " +
    " and t.driver.userId <> :driverID"


    "SELECT t FROM Trip t " +
    "where t.availableSeats > 0 " +
    "and t.driver.userId <> :driverID"
*/
    public static List<Trip> listCurrentTrips(Long driverID) {
        List<Trip> trips = tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t FROM Trip t " +
                                "left join TripPassenger tp on t.tripId = tp.trip.tripId" +
                                " where tp.passenger is NULL" +
                                " and tp.trip is NULL" +
                                " and t.availableSeats > 0 " +
                                " and t.driver.userId <> :driverID ")
                        .setParameter("driverID", driverID)
                        .getResultList())
        );
        sortTrip(trips);
        removeBeforeTrips(trips);
        removeSameDayButBeforeHour(trips);

        return trips;
    }

    public static List<Trip> listBeforeTrips() {
        List<Trip> trips = tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t FROM Trip t")
                        .getResultList())
        );
        sortTrip(trips);
        removeAfterTrips(trips);
        removeSameDayButAfterHour(trips);

        return trips;
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

    private static void sortTrip(List<Trip> trips) {
        if (trips.isEmpty()) return;
        trips.sort((o1, o2) -> {
            if (o1.getDate().equals(o2.getDate()))
                return (o1.getTime().before(o2.getTime()) ? -1 : (o1.getTime().after(o2.getTime())) ? 1 : 0);
            return o1.getDate().compareTo(o2.getDate());
        });
    }

    private static void removeSameDayButBeforeHour(List<Trip> trips) {
        LocalDate date = LocalDate.now();
        if (trips.isEmpty()) return;
        int i = 0;
        if (trips.get(i).getDate().equals(date.toString())) {

            LocalDateTime localDateTime = LocalDateTime.now();
            int realHour = localDateTime.getHour();
            int realMinute = localDateTime.getMinute();

            int tripHour = trips.get(i).getTime().getHours();
            int tripMinute = trips.get(i).getTime().getMinutes();

            if (tripHour < realHour) {
                trips.remove(i);
                removeSameDayButBeforeHour(trips);
            } else {
                if (tripHour == realHour) {
                    if (tripMinute < realMinute) {
                        trips.remove(i);
                        removeSameDayButBeforeHour(trips);
                    }
                }
            }

        }
    }

    private static void removeSameDayButAfterHour(List<Trip> trips) {
        LocalDate date = LocalDate.now();
        int i = trips.size() - 1;
        if (trips.isEmpty()) return;
        if (trips.get(i).getDate().equals(date.toString())) {

            LocalDateTime localDateTime = LocalDateTime.now();
            int realHour = localDateTime.getHour();
            int realMinute = localDateTime.getMinute();

            int tripHour = trips.get(i).getTime().getHours();
            int tripMinute = trips.get(i).getTime().getMinutes();

            if (tripHour > realHour) {
                trips.remove(i);
                removeSameDayButBeforeHour(trips);
            } else {
                if (tripHour == realHour) {
                    if (tripMinute > realMinute) {
                        trips.remove(i);
                        removeSameDayButBeforeHour(trips);
                    }
                }
            }
        }
    }

    private static void removeAfterTrips(List<Trip> trips) {
        LocalDate localDate = LocalDate.now();
        while (trips.size() > 0) {
            if (trips.get(trips.size() - 1).getDate().compareTo(localDate.toString()) > 0)
                trips.remove(trips.size() - 1);
            else
                return;
        }
    }

    private static void removeBeforeTrips(List<Trip> trips) {
        LocalDate date = LocalDate.now();
        while (trips.size() > 0) {
            if (trips.get(0).getDate().compareTo(date.toString()) < 0)
                trips.remove(0);
            else
                return;
        }
    }

}