package austral.ing.lab1.entity;

import austral.ing.lab1.model.Chat;
import austral.ing.lab1.model.Message;
import austral.ing.lab1.model.User;
import austral.ing.lab1.service.chat.MessageWS;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

import static austral.ing.lab1.util.EntityManagers.currentEntityManager;
import static austral.ing.lab1.util.LangUtils.checkedList;
import static austral.ing.lab1.util.Transactions.tx;

public class Messages {

    public static void saveMessage(MessageWS messageWS) {
        persist(Messages.convert(messageWS));
    }

    public static Message convert(MessageWS messageWS) {
        Optional<User> userName = Users.findByEmail(messageWS.getUserName());
        assert userName.isPresent();

        Optional<Chat> chat = Chats.findById(messageWS.getChatId());
        assert chat.isPresent();
        return new Message(userName.get(), messageWS.getMessage(), chat.get());
    }

    public static MessageWS convert(Message message) {
        return new MessageWS(message.getSender().getEmail(), message.getChat().getId(), message.getText());
    }

    public static Message persist(Message message) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();
            currentEntityManager().persist(message);
            tx.commit();
            return message;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static List<Message> getFromChat(long chatId) {
        return tx(() -> checkedList(currentEntityManager()
                .createQuery("SELECT m FROM Message m " +
                        " where m.chat.id = :chatId")
                .setParameter("chatId", chatId)
                .getResultList())
        );
    }
}
