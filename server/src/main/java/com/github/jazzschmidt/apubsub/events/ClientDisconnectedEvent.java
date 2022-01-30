package com.github.jazzschmidt.apubsub.events;

/**
 * Indicates that a client disconnected from the server.
 */
public class ClientDisconnectedEvent extends StompClientEvent {
    /**
     * @param source    Source that raised the event
     * @param sessionId STOMP session id
     * @see ClientDisconnectedEvent
     */
    public ClientDisconnectedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }
}
