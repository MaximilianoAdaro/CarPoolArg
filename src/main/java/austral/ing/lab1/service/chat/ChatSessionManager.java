package austral.ing.lab1.service.chat;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class ChatSessionManager {

    private static final Lock LOCK = new ReentrantLock();
    private static final Set<Session> SESSIONS = new CopyOnWriteArraySet<>();

    private ChatSessionManager() {
        throw new IllegalStateException(Constants.INSTANTIATION_NOT_ALLOWED);
    }

    static void publish(final Message message, final Session origin) {
        assert !Objects.isNull(message) && !Objects.isNull(origin);

        SESSIONS.stream().filter(session -> !session.equals(origin)).forEach(session -> {
            try {
                session.getBasicRemote().sendObject(message);
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
                    && SESSIONS.stream().noneMatch(elem -> elem.getUserProperties().get(Constants.USER_NAME_KEY).equals(session.getUserProperties().get(Constants.USER_NAME_KEY)))
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
