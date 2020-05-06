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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class CarTest {

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
    public void createCarModel() {
        EntityManager em = EntityManagers.currentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        final User user = new User("numa", "leone", "numa@gmail.com", "", true);
//        if (Users.findByEmail(user.getEmail()).isPresent()) return;

        CarModel carModel = new CarModel("Toyota Corolla");
        Car car = new Car(carModel, "blue", "AA000AA");
        carModel.addCar(car);
        car.setUser(user);
        user.setCar(car);

        em.persist(carModel);
        em.persist(car);
        em.persist(user);

        tx.commit();

        /*
        assertThat(user.getUserId(), greaterThan(0L));
        final Optional<User> persistedUser = Users.findById(user.getUserId());
        assertThat(persistedUser.isPresent(), is(true));
        System.out.println("------------User-----------");
        System.out.println(Users.findByEmail(persistedUser.get().getEmail()));
        System.out.println("------------Car-----------");
        System.out.println(Cars.findById(persistedUser.get().getCar().getCarId()));
        System.out.println("------------CarModel-----------");
        System.out.println(CarModels.findByName(persistedUser.get().getCar().getCarModel().getName()));
        */
    }

}
