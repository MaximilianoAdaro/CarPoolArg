package austral.ing.lab1.repository;

import austral.ing.lab1.entity.Cars;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Car;
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
        emf = Persistence.createEntityManagerFactory("databaseLab1");
        EntityManagers.setFactory(emf);
    }

    @After
    public void tearDown() {
        emf.close();
    }

    @Test
    public void testCar(){
        User user = new User("testing", "car", "testc@gmail.com", "", true);
        user.setCar(new Car("Fitito2"));
        Users.persist(user);
    }

    /*
    @Test
    public void createUser() {
        Optional<User> userFind = Users.findByEmail("fulanito@gmail.com");
        if (userFind.isPresent()) deleteUser();
//        if (userFind.isPresent()) return;

        final User user = new User("fulanito", "lopez", "fulanito@gmail.com", "", true);
        user.setId(1L);
        user.setCar(new Car("Ford"));


        EntityManagers.currentEntityManager().merge(user);
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

    public void deleteUser() {
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
    }
    */

     /*
     @Test
      public void updateUser(){
        final Optional<User> persistedUser = Users.findById(3L);

        Users.modifyUserName(persistedUser, "otrofulano");
      }
*/

      @Test
      public void dropUser(){
        final Optional<User> persistedUser = Users.findById(2L);

        assertThat(persistedUser.isPresent(), is(true));

        Users.deleteUser(persistedUser.get().getId());

        //assertThat(Users.findById(2L).isEmpty(), is(true)); como verifico esto?
      }

}