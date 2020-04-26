package austral.ing.lab1.entity;

import austral.ing.lab1.model.Car;
import austral.ing.lab1.util.LangUtils;

import javax.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Cars {

    public static Optional<Car> findById(Long id) {
        return tx(() ->
                Optional.of(currentEntityManager().find(Car.class, id))
        );
    }

    public static Optional<Car> findByName(String name) {
        return tx(() -> LangUtils.<Car>checkedList(currentEntityManager()
                .createQuery("SELECT u FROM Car u WHERE u.name LIKE :name")
                .setParameter("name", name).getResultList()).stream()
                .findFirst()
        );
    }

    public static List<Car> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT u FROM Car u").getResultList())
        );
    }

    public static Car persist(Car car) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(car);

            tx.commit();
            return car;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
