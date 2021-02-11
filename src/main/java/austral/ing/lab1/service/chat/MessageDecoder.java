package austral.ing.lab1.service.chat;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

public final class MessageDecoder implements Decoder.Text<MessageWS> {

    @Override
    public void destroy() {
    }

    @Override
    public void init(final EndpointConfig arg0) {
    }

    @Override
    public MessageWS decode(final String arg0) throws DecodeException {
        try {
            return Constants.MAPPER.readValue(arg0, MessageWS.class);
        } catch (IOException e) {
            throw new DecodeException(arg0, "Unable to decode text to Message", e);
        }
    }

    @Override
    public boolean willDecode(final String arg0) {
        return arg0.contains(Constants.USER_NAME_KEY) && arg0.contains(Constants.MESSAGE_KEY);
    }
}
