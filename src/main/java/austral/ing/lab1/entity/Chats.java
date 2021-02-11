package austral.ing.lab1.entity;

import austral.ing.lab1.jsonModel.ChatDto;
import austral.ing.lab1.model.Chat;
import austral.ing.lab1.model.User;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Chats {

    public static Optional<Chat> findById(Long idChat) {
        return tx(() -> Optional.of(currentEntityManager().find(Chat.class, idChat)));
    }

    public static Chat persist(Chat chat) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();
            currentEntityManager().persist(chat);
            tx.commit();
            return chat;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static List<ChatDto> getCurrentChats(Long userId) {
        List<Chat> chats = tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT c FROM Chat c " +
                        " where c.trip.driver.userId = :userId")
                .setParameter("userId", userId)
                .getResultList())
        );

        chats.addAll(tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT c FROM Chat c " +
                        "left join TripPassenger tp on tp.trip.tripId = c.trip.tripId " +
                        "where tp.passenger.userId = :userId")
                .setParameter("userId", userId)
                .getResultList())
        ));

        return chats.stream().distinct().map(ChatDto::convert).collect(Collectors.toList());
    }
}
