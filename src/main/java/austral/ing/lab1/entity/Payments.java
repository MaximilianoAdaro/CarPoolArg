package austral.ing.lab1.entity;

import austral.ing.lab1.model.PaymentTrip;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

import javax.persistence.EntityTransaction;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;

public class Payments {

    public static void savePayment(User user, Trip trip, double amount) {
        persist(new PaymentTrip(user, trip, amount));
    }

    public static PaymentTrip persist(PaymentTrip paymentTrip) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();
            currentEntityManager().persist(paymentTrip);
            tx.commit();
            return paymentTrip;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
