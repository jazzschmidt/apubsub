package com.github.jazzschmidt.apubsub;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;

public class RegistrationChannelInterceptor implements ChannelInterceptor {

    private final ClientRegistrationService clientRegistrations;

    public RegistrationChannelInterceptor(ClientRegistrationService clientRegistrations) {
        this.clientRegistrations = clientRegistrations;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        String sessionId = accessor.getSessionId();

        if (SUBSCRIBE == command && !clientRegistrations.isClientRegistered(sessionId)) {
            // TODO: Custom exception
            throw new MessagingException("Unregistered client!");
        }

        return message;
    }
}
