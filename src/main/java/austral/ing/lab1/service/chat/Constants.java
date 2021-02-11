package austral.ing.lab1.service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;

final class Constants {

    static final String INSTANTIATION_NOT_ALLOWED = "Instantiation not allowed";
    static final String USER_NAME_KEY = "username";
    static final String CHAT_KEY = "chatId";
    static final String MESSAGE_KEY = "message";
    static final ObjectMapper MAPPER = new ObjectMapper();

    private Constants() {
        throw new IllegalStateException(INSTANTIATION_NOT_ALLOWED);
    }
}