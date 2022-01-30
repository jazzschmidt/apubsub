package com.github.jazzschmidt.apubsub.events;

/**
 * Indicates that a client has been unregistered.
 */
public class ClientUnregisteredEvent extends StompClientEvent {

    private final String clientName;

    /**
     * @param source     Source that raised the event
     * @param sessionId  STOMP session id
     * @param clientName Name of the client
     * @see ClientUnregisteredEvent
     */
    public ClientUnregisteredEvent(Object source, String sessionId, String clientName) {
        super(source, sessionId);
        this.clientName = clientName;
    }

    /**
     * Returns the name of the unregistered client
     *
     * @return clients name
     */
    public String getClientName() {
        return clientName;
    }
}
