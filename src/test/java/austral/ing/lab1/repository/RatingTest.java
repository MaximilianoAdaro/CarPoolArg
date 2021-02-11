package austral.ing.lab1.repository;

import austral.ing.lab1.entity.Ratings;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;
import austral.ing.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class RatingTest {

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
////    @Test
////    public void setRating() {
////        Ratings.setRating();
////    }
//
//    @Test
//    public void testRatingUser() {
//        Optional<User> optionalUser = Users.findByEmail("admin@gmail.com");
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            System.out.println(Ratings.rateUser(user));
//            System.out.println(Ratings.getSizeRate(user));
//        }
//    }

}
