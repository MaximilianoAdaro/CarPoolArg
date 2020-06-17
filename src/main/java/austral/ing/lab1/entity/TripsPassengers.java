package austral.ing.lab1.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TripsPassengers {

    public static void acceptPassenger(Long userId, Long tripId){
        try {
            // create our mysql database connection
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/lab1";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("UPDATE lab1.TRIPS_PASSENGERS t " +
                                                "SET t.STATE = true" +
                                                " WHERE t.TRIP_ID = "+ tripId +
                                                " AND t.PASSENGER_ID = " + userId);
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
