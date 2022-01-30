package com.github.jazzschmidt.apubsub.events;

/**
 * Indicates that a client has been registered.
 */
public class ClientRegisteredEvent extends StompClientEvent {

    private final String clientName;

    /**
     * @param source     Source that raised the event
     * @param sessionId  STOMP session id
     * @param clientName Name of the client
     * @see ClientRegisteredEvent
     */
    public ClientRegisteredEvent(Object source, String sessionId, String clientName) {
        super(source, sessionId);
        this.clientName = clientName;
    }

    /**
     * Returns the name of the registered client
     *
     * @return clients name
     */
    public String getClientName() {
        return clientName;
    }
}
