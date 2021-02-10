package austral.ing.lab1.service.chat;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public final class MessageEncoder implements Encoder.Text<MessageWS> {

    @Override
    public void destroy() {
    }

    @Override
    public void init(final EndpointConfig arg0) {
    }

    @Override
    public String encode(final MessageWS message) throws EncodeException {
        try {
            return Constants.MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new EncodeException(message, "Unable to encode message", e);
        }
    }
}
