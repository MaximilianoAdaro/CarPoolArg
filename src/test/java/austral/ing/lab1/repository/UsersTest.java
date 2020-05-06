package austral.ing.lab1.repository;

import austral.ing.lab1.entity.CarModels;
import austral.ing.lab1.entity.Cars;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Car;
import austral.ing.lab1.model.CarModel;
import austral.ing.lab1.model.User;
import austral.ing.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class UsersTest {
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

    /*public void deleteUser() {
        try {
            EntityManager em = EntityManagers.currentEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            User user = em.find(User.class,Users.findByEmail("fulanito@gmail.com"));
            em.remove(user);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }*/
/*
    @Test
    public void createAdmin() {
        if (Users.findByEmail("admin@gmail.com").isPresent()) return;

        User userAdmin = new User("admin", "istrator", "admin@gmail.com", "123", true);
        userAdmin.setAdministrator(true);
        Users.persist(userAdmin);
    }
*/

    @Test
    public void createUser() {
//        Optional<User> userFind = Users.findByEmail("fulanito@gmail.com");
//        if (userFind.isPresent()) deleteUser();
//        if (userFind.isPresent()) return;

        final User user = new User("tuhermana", "lopez", "entangao@gmail.com", "", true);
        user.setCar(new Car(new CarModel("Fitito"), "pink", "AA012AA"));

        Users.persist(user);

    }
/*
        assertThat(user.getId(), greaterThan(0L));
        final Optional<User> persistedUser = Users.findById(user.getId());
        assertThat(persistedUser.isPresent(), is(true));
        assertThat(persistedUser.get().getEmail(), is("fulanito@gmail.com"));
        assertThat(persistedUser.get().getFirstName(), is("fulanito"));
        assertThat(persistedUser.get().getLastName(), is("lopez"));
        assertThat(persistedUser.get().getPassword(), is(""));
        assertThat(persistedUser.get().getActive(), is(true));

        Optional<User> byEmail = Users.findByEmail(persistedUser.get().getEmail());
        System.out.println(byEmail);
    }

*/
    /*@Test
    public void updateUser() {;
        final Optional<User> persistedUser = Users.findById(1L);

        Users.modifyUserName(persistedUser, "otrofulano");
    }

    @Test
    public void dropUser() {
        final Optional<User> persistedUser = Users.findById(2L);

        assertThat(persistedUser.isPresent(), is(true));

        Users.deleteUser(persistedUser.get().getId());

        //assertThat(Users.findById(2L).isEmpty(), is(true)); como verifico esto?
    }*/
}