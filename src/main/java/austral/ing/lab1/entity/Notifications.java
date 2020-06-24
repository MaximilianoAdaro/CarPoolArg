package austral.ing.lab1.entity;

import austral.ing.lab1.model.Notification;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.TypeNotification;
import austral.ing.lab1.model.User;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Notifications {

    public static void newNotification(User idUser, Trip idTrip, Long typeId, String date) {
        Optional<TypeNotification> types = Notifications.findById(typeId);
        types.ifPresent(typeNotification -> persist(new Notification(idUser, idTrip, typeNotification, date)));
    }

    public static List<NotificationType> listAllNotif(User user) {
        List<NotificationType> notificationList = new ArrayList<>();
        final List<Notification> notificationList1 = listNotif1Type(user);
        final List<Notification> notificationList2 = listNotif12Type(user, 1L);
        final List<Notification> notificationList3 = listNotif12Type(user, 2L);

        for (Notification notification : notificationList1) {
            notificationList.add(new NotificationType(notification, 1));
        }

        for (Notification notification : notificationList2) {
            notificationList.add(new NotificationType(notification, 2));
        }

        for (Notification notification : notificationList3) {
            notificationList.add(new NotificationType(notification, 3));
        }
//        notificationList.sort(Comparator.comparingLong(o -> o.getNotification().getIdNotification()));
        notificationList.sort((o1, o2) -> Long.compare(o2.getNotification().getIdNotification(), o1.getNotification().getIdNotification()));

        return notificationList;
    }

    public static List<Notification> listNotif1Type(User user) {
        Optional<TypeNotification> types = Notifications.findById(1L);
        return types.<List<Notification>>map(typeNotification -> tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT n FROM Notification n " +
                                " where n.idTrip.driver = :user" +
                                " and n.type = :type" +
                                " and n.idUser <> :user")
                        .setParameter("user", user)
                        .setParameter("type", typeNotification)
                        .getResultList())
        )).orElse(null);
    }

    public static List<Notification> listNotif12Type(User user, Long type) {
        Optional<TypeNotification> types = Notifications.findById(type);
        return types.<List<Notification>>map(typeNotification -> tx(() ->
                checkedList(currentEntityManager()
                        .createQuery("SELECT n FROM Notification n " +
                                " where n.idUser = :user" +
                                " and n.type = :type" +
                                " and n.idTrip.driver <> :user")
                        .setParameter("user", user)
                        .setParameter("type", typeNotification)
                        .getResultList())
        )).orElse(null);
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
