package austral.ing.lab1.entity;

import austral.ing.lab1.model.Trip;
import austral.ing.lab1.util.EntityManagers;

import javax.persistence.EntityManager;
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

    static LocalDate date = LocalDate.now();

    public static Optional<Trip> findById(Long id) {
        return tx(() ->
                Optional.of(currentEntityManager().find(Trip.class, id))
        );
    }

    public static List<Trip> searchList(String fromTrip, String toTrip, Long driverID) {
        List<Trip> trips = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT t " +
                        "FROM Trip t " +
                        "WHERE t.fromTrip LIKE :param " +
                        "and t.toTrip LIKE :param2 " +
                        "and t.date >= :localDate " +
                        "and t.availableSeats > 0 " +
                        "and t.driver.userId <> :driverID")
                .setParameter("localDate", date.toString())
                .setParameter("param", "%" + fromTrip + "%")
                .setParameter("param2", "%" + toTrip + "%")
                .setParameter("driverID", driverID)
                .getResultList()
        ));
        sortTrip(trips);
        removeSameDayButBeforeHour(trips);

        return trips;
    }

//    public static Optional<Trip> findByEmail(String email) {
//        return tx(() -> LangUtils.<Trip>checkedList(currentEntityManager()
//                .createQuery("SELECT t FROM Trip t WHERE t.fromTrip LIKE :email")
//                .setParameter("email", email).getResultList()).stream()
//                .findFirst()
//        );
//    }

    public static List<Trip> listDriverTrips(Long driverID) {
        List<Trip> trips = tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t FROM Trip t " +
                                "where t.date >= :localDate " +
                                "and t.driver.userId = :driverID")
                        .setParameter("localDate", date.toString())
                        .setParameter("driverID", driverID)
                        .getResultList())
        );
        sortTrip(trips);
        removeSameDayButBeforeHour(trips);

        return trips;
    }

    public static List<Trip> listPassengerTrips(Long driverID) {
        List<Trip> trips = new ArrayList<>();
        try {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/lab1";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT j.TRIP_ID FROM trip_passenger_table j where PASSENGER_ID = " + driverID);
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
        removeSameDayButBeforeHour(trips);

        return trips;
    }

    public static List<Trip> listCurrentTrips(Long driverID) {
        LocalDate date = LocalDate.now();
        List<Trip> trips = tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT t FROM Trip t " +
                                "where t.date >= :localDate " +
                                "and t.availableSeats > 0 " +
                                "and t.driver.userId <> :driverID")
                        .setParameter("localDate", date.toString())
                        .setParameter("driverID", driverID)
                        .getResultList())
        );
        sortTrip(trips);
        removeSameDayButBeforeHour(trips);

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

    private static void sortTrip(List<Trip> trips) {
        trips.sort((o1, o2) -> {
            if (o1.getDate().equals(o2.getDate()))
                return (o1.getTime().before(o2.getTime()) ? -1 : (o1.getTime().after(o2.getTime())) ? 1 : 0);
            return o1.getDate().compareTo(o2.getDate());
        });
    }

    private static void removeSameDayButBeforeHour(List<Trip> trips) {
        int i = 0;
        if (trips.isEmpty()) return;
        if (trips.get(i).getDate().equals(date.toString())) {

            LocalDateTime localDateTime = LocalDateTime.now();
            int realHour = localDateTime.getHour();
            int realMinute = localDateTime.getMinute();

            int tripHour = trips.get(i).getTime().getHours();
            int tripMinute = trips.get(i).getTime().getMinutes();

            if (tripHour < realHour) {
                trips.remove(i);
            } else {
                if (tripHour == realHour) {
                    if (tripMinute < realMinute) {
                        trips.remove(i);
                    }
                }
            }
            removeSameDayButBeforeHour(trips);
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
