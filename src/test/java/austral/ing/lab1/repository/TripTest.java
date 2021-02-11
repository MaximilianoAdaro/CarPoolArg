package austral.ing.lab1.repository;

import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;
import austral.ing.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Time;
import java.util.List;

public class TripTest {

    private EntityManagerFactory emf;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("test");
        EntityManagers.setFactory(emf);
    }

    @After
    public void tearDown() {
        emf.close();
    }

    /*@Test
    public void testTrip() {
        if (Users.findByEmail("driverEmail@austral").isPresent()) return;
        if (Users.findByEmail("passengerEmail@austral").isPresent()) return;
        User driver = new User("driverFirstName", "driverLastName",
                "driverEmail@austral", "", true);
        User passenger = new User("passengerFirstName", "passengerLastName",
                "passengerEmail@austral", "", true);
        String date = "2020-07-18";
        Time time = new Time(20, 0, 0);
        String comment = "this is a new trip";
        int seats = 3;
        String from = "Chacarita";
        String to = "Caballito";
        Trip trip = new Trip(driver, date, from, to, time, comment, seats);

        Users.persist(driver);
        Users.persist(passenger);

        trip.addPassenger(passenger);
        Trips.persist(trip);
    }*/


    @Test
    public void testAllTrips(){
        List<Trip> trips = Trips.listCurrentTrips(4L);
        System.out.println(trips.size());
        for (Trip trip : trips) {
            System.out.println(trip.getDate());
            System.out.println(trip.toString());
        }
    }

}
