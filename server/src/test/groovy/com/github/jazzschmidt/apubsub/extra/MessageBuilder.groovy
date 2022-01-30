package com.github.jazzschmidt.apubsub.extra

import com.github.jazzschmidt.apubsub.messages.Broadcast
import com.github.jazzschmidt.apubsub.messages.Registration
import com.github.jazzschmidt.apubsub.messages.StereotypeMessage
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.support.GenericMessage

import java.util.function.Consumer

import static org.springframework.messaging.simp.stomp.StompCommand.*

/**
 * Assembles common used {@link Message}s in tests.
 */
class MessageBuilder {
    /**
     * Creates a {@link StompCommand#SUBSCRIBE} message.
     *
     * @param sessionId the session id
     * @return message object
     */
    static Message<?> subscribe(String sessionId) {
        prototype(SUBSCRIBE, sessionId)
    }

    /**
     * Creates a {@link StompCommand#CONNECT} message.
     *
     * @param sessionId the session id
     * @return message object
     */
    static Message<?> connect(String sessionId) {
        prototype(CONNECT, sessionId)
    }

    /**
     * Creates a {@link StompCommand#DISCONNECT} message.
     *
     * @param sessionId the session id
     * @return message object
     */
    static Message<?> disconnect(String sessionId) {
        prototype(DISCONNECT, sessionId)
    }

    /**
     * Creates a {@link Registration} message.
     *
     * @param sessionId the session id
     * @param clientName name of the client
     * @return message object
     */
    static Message<Registration> registration(String sessionId, String clientName) {
        prototype(sessionId, Registration, { it.clientName = clientName })
    }

    /**
     * Creates a {@link Broadcast} message.
     *
     * @param sessionId the session id
     * @param message text content
     * @param clientName name of the client
     * @return message object
     */
    static Message<Broadcast> broadcast(String sessionId, String message, String clientName = null) {
        prototype(sessionId, Broadcast, { it.message = message; it.clientName = clientName })
    }

    /**
     * Creates a prototype STOMP message that is populated with a STOMP command and a session id.
     *
     * @param command STOMP command
     * @param sessionId session id
     * @param headers Additional headers; may be skipped
     * @return message object
     */
    static Message<?> prototype(StompCommand command, String sessionId, Map<String, Object> headers = [:]) {
        new GenericMessage<>([], [stompCommand: command, simpSessionId: sessionId] + headers)
    }

    /**
     * Creates a prototype STOMP message that is populated with a {@link StereotypeMessage}, a session id and can be
     * configured directly with a {@link Closure} or {@link Consumer}.
     *
     * @param sessionId session id
     * @param type Class of the message content
     * @param cl Configuration of the content
     * @return message object
     */
    static <T extends StereotypeMessage> Message<T> prototype(String sessionId, Class<T> type, Consumer<T> cl) {
        T instance = type.getDeclaredConstructor().newInstance()
        cl.accept(instance)

        new GenericMessage<T>(instance, [simpSessionId: sessionId])
    }

}
