package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.messages.Broadcast;
import com.github.jazzschmidt.apubsub.messages.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Intensively influenced by https://spring.io/guides/gs/messaging-stomp-websocket/ (Getting Started | Using WebSocket
 * to build an interactive web application)
 */
@Controller
public class MessageController {

    private final ClientRegistrations registrations;

    @Autowired
    public MessageController(ClientRegistrations registrations) {
        this.registrations = registrations;
    }

    @MessageMapping("#{@messaging.topics.broadcast}")
    public Broadcast broadcast(Message<Broadcast> message) {
        String sessionId = getStompSessionId(message);
        String clientId = registrations.getClientName(sessionId);

        // Associate client name with the broadcast message
        Broadcast broadcast = message.getPayload();
        broadcast.clientName = clientId;

        return broadcast;
    }

    @MessageMapping("#{@messaging.topics.registration}")
    public void registerClient(Message<Registration> message) {
        String clientName = message.getPayload().clientName;
        registrations.registerClient(getStompSessionId(message), clientName);
    }

    private String getStompSessionId(Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        return accessor.getSessionId();
    }
}
