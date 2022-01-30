package com.github.jazzschmidt.apubsub.interceptor;

import com.github.jazzschmidt.apubsub.ClientRegistrations;
import com.github.jazzschmidt.apubsub.events.ClientConnectedEvent;
import com.github.jazzschmidt.apubsub.events.ClientDisconnectedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;

/**
 * Logs connecting and disconnecting messages and drops clients from the {@link ClientRegistrations} when a disconnect
 * occurs.
 */
@Component
public class ConnectionEventPublisher implements ChannelInterceptor {

    private final Logger log;
    private final ApplicationEventPublisher eventPublisher;

    {
        log = Logger.getLogger(getClass().getName());
    }

    @Autowired
    public ConnectionEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // influenced by https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-interceptors
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        String sessionId = accessor.getSessionId();

        if (command == CONNECT) {
            log.info("A new client is connecting");
            eventPublisher.publishEvent(new ClientConnectedEvent(this, sessionId));
        } else if (command == DISCONNECT) {
            log.info("A client is disconnecting");
            eventPublisher.publishEvent(new ClientDisconnectedEvent(this, sessionId));
        }

        return message;
    }

}