package com.github.jazzschmidt.apubsub.events;

public class ClientConnectedEvent extends StompClientEvent {
    public ClientConnectedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }
}
