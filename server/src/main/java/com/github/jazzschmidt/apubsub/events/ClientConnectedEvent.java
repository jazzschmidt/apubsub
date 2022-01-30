package com.github.jazzschmidt.apubsub.events;

/**
 * Indicates that a new client connected to the server.
 */
public class ClientConnectedEvent extends StompClientEvent {
    /**
     * @param source    Source that raised the event
     * @param sessionId STOMP session id
     * @see ClientConnectedEvent
     */
    public ClientConnectedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }
}
