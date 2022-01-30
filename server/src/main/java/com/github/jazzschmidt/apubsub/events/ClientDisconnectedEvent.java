package com.github.jazzschmidt.apubsub.events;

public class ClientDisconnectedEvent extends StompClientEvent {
    public ClientDisconnectedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }
}
