// Intensively influenced by https://spring.io/guides/gs/messaging-stomp-websocket/
package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.messages.Broadcast;
import com.github.jazzschmidt.apubsub.messages.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Handles incoming STOMP messages that were routed to the app path.
 * <p>
 * More specific, it receives broadcast messages and pushes them to the broadcast topic along with a client name, if
 * that client was registered beforehand.
 */
@Controller
public class MessageController {

    private final ClientRegistrations registrations;

    /**
     * @param registrations Client registration container
     * @see MessageController
     */
    @Autowired
    public MessageController(ClientRegistrations registrations) {
        this.registrations = registrations;
    }

    /**
     * Registers a client in order to associate messages from a specific client. <strong>This step is required before
     * subscribing to the broadcast topic.</strong>
     *
     * @param message the {@link Registration} message
     */
    @MessageMapping("#{@messaging.topics.registration}")
    public void registerClient(Message<Registration> message) {
        String clientName = message.getPayload().clientName;
        registrations.registerClient(getStompSessionId(message), clientName);
    }

    /**
     * Broadcasts a message to all connected clients.
     *
     * @param message The {@link Broadcast} message
     * @return the same message enriched with the sending clients name
     */
    @MessageMapping("#{@messaging.topics.broadcast}")
    public Broadcast broadcast(Message<Broadcast> message) {
        try {
            setSendingClientName(message);
            return message.getPayload();
        } catch (UnregisteredClientException e) {
            // Translate internal exception
            throw new MessagingException(message, e);
        }
    }

    /**
     * Sets the client name on the {@link Broadcast} message.
     *
     * @param message the received message
     * @throws UnregisteredClientException when no client name is associated with that session id
     */
    private void setSendingClientName(Message<Broadcast> message) throws UnregisteredClientException {
        String sessionId = getStompSessionId(message);
        String clientName = registrations.getClientName(sessionId);

        message.getPayload().clientName = clientName;
    }

    /**
     * Retrieves the STOMP session id
     *
     * @param message the received message
     * @return its session id
     */
    private String getStompSessionId(Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        return accessor.getSessionId();
    }
}
