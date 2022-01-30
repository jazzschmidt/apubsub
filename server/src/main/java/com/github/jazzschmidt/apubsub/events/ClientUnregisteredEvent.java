package com.github.jazzschmidt.apubsub.events;

public class ClientUnregisteredEvent extends StompClientEvent {

    private final String clientName;

    public ClientUnregisteredEvent(Object source, String sessionId, String clientName) {
        super(source, sessionId);
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
