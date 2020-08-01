package austral.ing.lab1.repository;

import austral.ing.lab1.entity.NotificationType;
import austral.ing.lab1.entity.Notifications;
import austral.ing.lab1.entity.Ratings;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Notification;
import austral.ing.lab1.model.Rating;
import austral.ing.lab1.model.User;
import austral.ing.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class notificationTest {

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

    @Test
    public void testNotification() {

        Optional<User> optionalUser = Users.findByEmail("admin@gmail.com");
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            List<Notification> notList1 = Notifications.listNotif1Type(user);
            System.out.println("notList1 = " + notList1.size());
            for (Notification notification : notList1) {
                System.out.println(notification);
            }

            List<Notification> notList2 = Notifications.listNotif12Type(user, 1L);
            System.out.println("notList2 = " + notList2.size());
            for (Notification notification : notList2) {
                System.out.println(notification);
            }

            List<Notification> notList3 = Notifications.listNotif12Type(user, 2L);
            System.out.println("notList3 = " + notList3.size());
            for (Notification notification : notList3) {
                System.out.println(notification);
            }

            List<NotificationType> listAll = Notifications.listAllNotif(user);
            System.out.println("listAll = " + listAll.size());
            for (NotificationType notificationType : listAll) {
                System.out.println(notificationType);
            }

            List<Rating> ratingsUserAsDriver = Ratings.ratingsUserAsDriver(user);
            System.out.println("ratingsUserAsDriver = " + ratingsUserAsDriver.size());
            for (Rating rating : ratingsUserAsDriver) {
                System.out.println(rating);
            }

            List<Rating> ratingsUserAsPassenger = Ratings.ratingsUserAsPassenger(user);
            System.out.println("ratingsUserAsPassenger = " + ratingsUserAsPassenger.size());
            for (Rating rating : ratingsUserAsPassenger) {
                System.out.println(rating);
            }
        }
    }

}
