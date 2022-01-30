package com.github.jazzschmidt.apubsub.extra

import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.support.GenericMessage

import static org.springframework.messaging.simp.stomp.StompCommand.*

class MessageBuilder {
    static GenericMessage<?> subscribe(String sessionId) {
        prototype(SUBSCRIBE, sessionId)
    }

    static GenericMessage<?> connect(String sessionId) {
        prototype(CONNECT, sessionId)
    }

    static GenericMessage<?> disconnect(String sessionId) {
        prototype(DISCONNECT, sessionId)
    }

    static GenericMessage<?> prototype(StompCommand command, String sessionId, Map<String, Object> headers = [:]) {
        new GenericMessage<>([], [stompCommand: command, simpSessionId: sessionId] + headers)
    }

}
