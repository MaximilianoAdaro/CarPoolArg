package austral.ing.lab1.service.chat;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static austral.ing.lab1.service.chat.Constants.CHAT_KEY;
import static austral.ing.lab1.service.chat.Constants.USER_NAME_KEY;

final class ChatSessionManager {

    private static final Lock LOCK = new ReentrantLock();
    private static final Set<Session> SESSIONS = new CopyOnWriteArraySet<>();

    private ChatSessionManager() {
        throw new IllegalStateException(Constants.INSTANTIATION_NOT_ALLOWED);
    }

    static void publish(final MessageWS messageWS, final Session origin) {
        assert !Objects.isNull(messageWS) && !Objects.isNull(origin);

        long chatKey = (long) origin.getUserProperties().get(CHAT_KEY);
        String userNameKey = (String) origin.getUserProperties().get(USER_NAME_KEY);
        SESSIONS.stream().filter(session -> {
            long userChatId = (long) session.getUserProperties().get(CHAT_KEY);
            String userNameId = (String) session.getUserProperties().get(USER_NAME_KEY);
            return userChatId == chatKey && !userNameId.equals(userNameKey);
        }).forEach(session -> {
            try {
                session.getBasicRemote().sendObject(messageWS);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    public static void publishToMe(final List<MessageWS> messageWS, final Session origin) {
        assert !Objects.isNull(messageWS) && !Objects.isNull(origin);

        String userNameKey = (String) origin.getUserProperties().get(USER_NAME_KEY);
        SESSIONS.stream().filter(session -> {
            String userNameId = (String) session.getUserProperties().get(USER_NAME_KEY);
            return userNameId.equals(userNameKey);
        }).forEach(session -> {
            try {
                for (MessageWS message : messageWS) {
                    session.getBasicRemote().sendObject(message);
                }
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    static boolean register(final Session session) {
        assert !Objects.isNull(session);

        boolean result = false;
        try {
            LOCK.lock();

            result = !SESSIONS.contains(session)
                    && SESSIONS.stream().noneMatch(elem -> elem.getUserProperties().get(USER_NAME_KEY).equals(session.getUserProperties().get(USER_NAME_KEY)))
                    && SESSIONS.add(session);
        } finally {
            LOCK.unlock();
        }

        return result;
    }

    static void close(final Session session, final CloseCodes closeCode, final String message) {
        assert !Objects.isNull(session) && !Objects.isNull(closeCode);

        try {
            session.close(new CloseReason(closeCode, message));
        } catch (IOException e) {
            throw new RuntimeException("Unable to close session", e);
        }
    }

    static boolean remove(final Session session) {
        assert !Objects.isNull(session);

        return SESSIONS.remove(session);
    }
}
