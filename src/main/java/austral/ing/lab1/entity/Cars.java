package austral.ing.lab1.entity;

import austral.ing.lab1.model.Car;

import javax.persistence.EntityTransaction;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;

public class Cars {

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
