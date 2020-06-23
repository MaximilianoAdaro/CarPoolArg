package austral.ing.lab1.entity;

import austral.ing.lab1.model.*;

import javax.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Notifications {

    public static void newNotification(User idUser, Trip idTrip, TypeNotification type, String date) {

        persist(new Notification(idUser, idTrip, type, date));

    }

    //todo: terminar
    public static List<Notification> listNotifications(User user) {
        return tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT n FROM Notification n " +
                                " where n.idTrip.driver = :user")
                        .setParameter("user", user)
                        .getResultList())
        );
    }

    //todo: verificar esto
    public static List<Notification> listNotificationsType(User user, Long type) {
        Optional<TypeNotification> types = Notifications.findById(type);
        if (types.isPresent()) {
            return tx(() ->
                    checkedList(currentEntityManager()
                            .createQuery("SELECT n FROM Notification n " +
                                    " where n.idTrip.driver = :user" +
                                    " and n.type = :type")
                            .setParameter("user", user)
                            .setParameter("type", types.get())
                            .getResultList())
            );
        }
        return null;
    }

    public static Optional<TypeNotification> findById(Long id) {
        return tx(() ->
                Optional.of(currentEntityManager().find(TypeNotification.class, id))
        );
    }

    public static Notification persist(Notification notification) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(notification);

            tx.commit();
            return notification;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


}
