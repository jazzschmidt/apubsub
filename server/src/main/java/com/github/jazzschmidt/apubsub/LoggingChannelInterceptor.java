package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.config.MessagingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;

/**
 * Logs connecting and disconnecting messages and drops clients from the {@link ClientRegistrationService} when a
 * disconnect occurs.
 */
@Component
public class LoggingChannelInterceptor implements ExecutorChannelInterceptor {

    private final Logger log;
    private ClientRegistrationService clientRegistrations;

    {
        log = Logger.getLogger(getClass().getName());
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // influenced by https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-interceptors
        if (isClientConnecting(message)) {
            log.info("A new client is connecting");
        } else if (isClientDisconnecting(message)) {
            log.info("A client is disconnecting");
            dropRegisteredClient(message);
        }

        return message;
    }

    /**
     * Removes a client name association when the client disconnects and announces thereby its leaving.
     *
     * @param message origin STOMP message
     */
    private void dropRegisteredClient(Message<?> message) {
        String sessionId = getSessionId(message);

        if (!clientRegistrations.isClientRegistered(sessionId)) {
            return;
        }

        try {
            // Produces a broadcast message for registered leaving clients
            clientRegistrations.dropClient(sessionId);
        } catch (NoSuchClientException e) {
            // Virtually impossible exception
            e.printStackTrace();
        }
    }

    /**
     * Determines whether a client is currently trying to connect with the given message.
     *
     * @param message origin STOMP message
     * @return true if connect command is present in STOMP headers
     */
    private boolean isClientConnecting(Message<?> message) {
        return CONNECT == getStompCommand(message);
    }

    /**
     * Determines whether a client is currently trying to disconnect with the given message.
     *
     * @param message origin STOMP message
     * @return true if disconnect command is present in STOMP headers
     */
    private boolean isClientDisconnecting(Message<?> message) {
        return DISCONNECT == getStompCommand(message);
    }

    /**
     * Returns the STOMP command from the message headers.
     *
     * @param message origin STOMP message
     * @return STOMP command type
     */
    private StompCommand getStompCommand(Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        return accessor.getCommand();
    }

    /**
     * Returns the STOMP session id from the message headers.
     *
     * @param message origin STOMP message
     * @return unique STOMP session id
     */
    private String getSessionId(Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        return accessor.getSessionId();
    }

    /**
     * Injects the {@link ClientRegistrationService}.
     * <p>
     * Note: must be called lazy, since this Interceptor and the {@link MessagingConfiguration} would otherwise infer a
     * cyclic dependency.
     *
     * @param clientRegistrations Client Registration Service
     */
    @Autowired
    @Lazy
    public void setClientRegistrations(ClientRegistrationService clientRegistrations) {
        this.clientRegistrations = clientRegistrations;
    }
}
