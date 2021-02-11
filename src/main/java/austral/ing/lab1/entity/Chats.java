package austral.ing.lab1.entity;

import austral.ing.lab1.model.Chat;

import javax.persistence.EntityTransaction;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
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
}
