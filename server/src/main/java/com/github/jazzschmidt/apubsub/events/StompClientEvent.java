package com.github.jazzschmidt.apubsub.events;

import org.springframework.context.ApplicationEvent;

/**
 * Indicates an event that affects a specific client.
 *
 * @see ClientConnectedEvent
 * @see ClientDisconnectedEvent
 * @see ClientRegisteredEvent
 * @see ClientUnregisteredEvent
 */
public abstract class StompClientEvent extends ApplicationEvent {

    private final String sessionId;

    /**
     * @param source    Source that raised the event
     * @param sessionId STOMP session id
     * @see StompClientEvent
     */
    protected StompClientEvent(Object source, String sessionId) {
        super(source);
        this.sessionId = sessionId;
    }

    /**
     * Returns the session id of the client
     *
     * @return session id
     */
    public final String getSessionId() {
        return sessionId;
    }
}
