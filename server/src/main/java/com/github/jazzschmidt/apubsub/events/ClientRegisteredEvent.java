package com.github.jazzschmidt.apubsub.events;

public class ClientRegisteredEvent extends StompClientEvent {

    private final String clientName;

    public ClientRegisteredEvent(Object source, String sessionId, String clientName) {
        super(source, sessionId);
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
