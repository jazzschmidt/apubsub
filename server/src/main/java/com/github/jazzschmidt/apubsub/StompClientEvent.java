package com.github.jazzschmidt.apubsub;

import org.springframework.context.ApplicationEvent;

public abstract class StompClientEvent extends ApplicationEvent {

    private final String sessionId;

    protected StompClientEvent(Object source, String sessionId) {
        super(source);
        this.sessionId = sessionId;
    }

    public final String getSessionId() {
        return sessionId;
    }
}
