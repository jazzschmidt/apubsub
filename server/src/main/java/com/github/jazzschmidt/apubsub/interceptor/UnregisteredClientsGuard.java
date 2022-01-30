package com.github.jazzschmidt.apubsub.interceptor;

import com.github.jazzschmidt.apubsub.ClientRegistrations;
import com.github.jazzschmidt.apubsub.UnregisteredClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;

@Component
public class UnregisteredClientsGuard implements ChannelInterceptor {

    private final ClientRegistrations clientRegistrations;

    @Autowired
    public UnregisteredClientsGuard(ClientRegistrations clientRegistrations) {
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
