package com.github.jazzschmidt.apubsub;

public class ClientConnectedEvent extends StompClientEvent {
    public ClientConnectedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }
}
