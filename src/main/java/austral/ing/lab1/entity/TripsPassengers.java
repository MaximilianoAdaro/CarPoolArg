package austral.ing.lab1.entity;

import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class TripsPassengers {

    public static void acceptPassenger(Long userId, Long tripId) {
        try {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/lab1";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE trips_passengers t " +
                    "SET t.STATE = true" +
                    " WHERE t.TRIP_ID = " + tripId +
                    " AND t.PASSENGER_ID = " + userId);
            st.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void rejectPassenger(Long userId, Long tripId) {
        try {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/lab1";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM trips_passengers t" +
                    " WHERE t.TRIP_ID = " + tripId +
                    " AND t.PASSENGER_ID = " + userId);
            st.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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

}