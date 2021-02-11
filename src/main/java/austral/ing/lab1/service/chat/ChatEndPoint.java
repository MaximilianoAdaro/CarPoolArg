package austral.ing.lab1.service.chat;

import austral.ing.lab1.entity.Messages;

import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static austral.ing.lab1.service.chat.Constants.CHAT_KEY;
import static austral.ing.lab1.service.chat.Constants.USER_NAME_KEY;

@ServerEndpoint(value = "/chat/{username}/{chatId}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public final class ChatEndPoint {

    @OnOpen
    public void onOpen(@PathParam(USER_NAME_KEY) final String userName, @PathParam(CHAT_KEY) final long chatId, final Session session) {
        if (Objects.isNull(userName) || userName.isEmpty()) {
            throw new RegistrationFailedException("User name is required");
        } else {
            session.getUserProperties().put(USER_NAME_KEY, userName);
            session.getUserProperties().put(CHAT_KEY, chatId);
            if (ChatSessionManager.register(session)) {
                System.out.printf("Session opened for %s\n", userName);
                List<MessageWS> messages = Messages.getFromChat(chatId).stream().map(Messages::convert).collect(Collectors.toList());
                if (!messages.isEmpty())
                    ChatSessionManager.publishToMe(messages, session);
                MessageWS messageWS = new MessageWS(
                        (String) session.getUserProperties().get(USER_NAME_KEY),
                        (long) session.getUserProperties().get(CHAT_KEY),
                        "***joined the chat***"
                );
                ChatSessionManager.publish(messageWS, session);
            } else {
                throw new RegistrationFailedException("Unable to register, username already exists, try another");
            }
        }
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
        if (throwable instanceof RegistrationFailedException) {
            ChatSessionManager.close(session, CloseCodes.VIOLATED_POLICY, throwable.getMessage());
        }
    }

    @OnMessage
    public void onMessage(final MessageWS messageWS, final Session session) {
        //Save message to DB
        Messages.saveMessage(messageWS);
        ChatSessionManager.publish(messageWS, session);
    }

    @OnClose
    public void onClose(final Session session) {
        if (ChatSessionManager.remove(session)) {
            System.out.printf("Session closed for %s\n", session.getUserProperties().get(USER_NAME_KEY));

            ChatSessionManager.publish(new MessageWS(
                    (String) session.getUserProperties().get(USER_NAME_KEY),
                    (long) session.getUserProperties().get(CHAT_KEY),
                    "***left the chat***"
            ), session);
        }
    }

    private static final class RegistrationFailedException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public RegistrationFailedException(final String message) {
            super(message);
        }
    }
}
