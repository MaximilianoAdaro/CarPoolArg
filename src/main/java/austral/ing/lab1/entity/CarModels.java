package austral.ing.lab1.entity;

import austral.ing.lab1.model.CarModel;
import austral.ing.lab1.util.LangUtils;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class CarModels {

    public static Optional<CarModel> findById(Long id) {
        return tx(() ->
                Optional.of(currentEntityManager().find(CarModel.class, id))
        );
    }

    public static Optional<CarModel> findByName(String name) {
        return tx(() -> LangUtils.<CarModel>checkedList(currentEntityManager()
                .createQuery("SELECT cm FROM CarModel cm WHERE cm.name LIKE :name")
                .setParameter("name", name).getResultList()).stream()
                .findFirst()
        );
    }

    public static List<CarModel> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT cm FROM CarModel cm").getResultList())
        );
    }

    public static CarModel persist(CarModel carModel) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(carModel);

            tx.commit();
            return carModel;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
