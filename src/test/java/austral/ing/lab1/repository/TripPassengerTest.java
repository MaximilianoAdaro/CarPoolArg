package austral.ing.lab1.repository;

import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.TripsPassengers;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;
import austral.ing.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class TripPassengerTest {

//    private EntityManagerFactory emf;
//
//    @Before
//    public void setUp() {
//        emf = Persistence.createEntityManagerFactory("test");
//        EntityManagers.setFactory(emf);
//    }
//
//    @After
//    public void tearDown() {
//        emf.close();
//    }
//
//    @Test
//    public void passengerTest() {
//
//        Optional<Trip> optionalTrip = Trips.findById(15L);
//        if (optionalTrip.isPresent()) {
//            List<User> pass = TripsPassengers.listPassengers(optionalTrip.get());
//            for (User user : pass) {
//                System.out.println(user);
//            }
//        }
//    }

}