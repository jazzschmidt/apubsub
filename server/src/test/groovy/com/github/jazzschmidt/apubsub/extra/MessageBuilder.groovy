package com.github.jazzschmidt.apubsub.extra

import com.github.jazzschmidt.apubsub.messages.Broadcast
import com.github.jazzschmidt.apubsub.messages.Registration
import com.github.jazzschmidt.apubsub.messages.StereotypeMessage
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.support.GenericMessage

import java.util.function.Consumer

import static org.springframework.messaging.simp.stomp.StompCommand.*

class MessageBuilder {
    static Message<?> subscribe(String sessionId) {
        prototype(SUBSCRIBE, sessionId)
    }

    static Message<?> connect(String sessionId) {
        prototype(CONNECT, sessionId)
    }

    static Message<?> disconnect(String sessionId) {
        prototype(DISCONNECT, sessionId)
    }

    static Message<Registration> registration(String sessionId, String clientName) {
        prototype(sessionId, Registration, { it.clientName = clientName })
    }

    static Message<Broadcast> broadcast(String sessionId, String message, String clientName = null) {
        prototype(sessionId, Broadcast, { it.message = message; it.clientName = clientName })
    }

    static Message<?> prototype(StompCommand command, String sessionId, Map<String, Object> headers = [:]) {
        new GenericMessage<>([], [stompCommand: command, simpSessionId: sessionId] + headers)
    }

    static <T extends StereotypeMessage> Message<T> prototype(String sessionId, Class<T> type, Consumer<T> cl) {
        T instance = type.getDeclaredConstructor().newInstance()
        cl.accept(instance)

        new GenericMessage<T>(instance, [simpSessionId: sessionId])
    }

}
