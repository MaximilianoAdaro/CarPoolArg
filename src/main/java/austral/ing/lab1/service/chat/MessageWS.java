package austral.ing.lab1.service.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class MessageWS {

    @JsonProperty("username")
    private final String userName;
    @JsonProperty("chatId")
    private final long chatId;
    @JsonProperty
    private final String message;

    @JsonCreator
    public MessageWS(@JsonProperty("username") final String userName, @JsonProperty("chatId") final long chatId, @JsonProperty("message") final String message) {
        Objects.requireNonNull(userName);
        Objects.requireNonNull(message);

        this.userName = userName;
        this.chatId = chatId;
        this.message = message;
    }

    public String getUserName() {
        return this.userName;
    }

    public long getChatId() {
        return this.chatId;
    }

    public String getMessage() {
        return this.message;
    }
}
