package com.github.jazzschmidt.apubsub;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;

@Component
public class LoggingChannelInterceptor implements ExecutorChannelInterceptor {

    private final Logger log;

    {
        log = Logger.getLogger(getClass().getName());
    }

    // https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-interceptors
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        if (isClientConnecting(message)) {
            log.info("A new client is connecting");
        } else if (isClientDisconnecting(message)) {
            log.info("A client is disconnecting");
        }

        return message;
    }

    private boolean isClientConnecting(Message<?> message) {
        return CONNECT == getStompCommand(message);
    }

    private boolean isClientDisconnecting(Message<?> message) {
        return DISCONNECT == getStompCommand(message);
    }

    private StompCommand getStompCommand(Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        return accessor.getCommand();
    }
}
