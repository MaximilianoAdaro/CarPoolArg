package austral.ing.lab1.entity;

import austral.ing.lab1.model.Location;

import javax.persistence.EntityTransaction;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;

public class Locations {

    public static Location persist(Location location) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(location);

            tx.commit();
            return location;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
