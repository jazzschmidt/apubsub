package com.github.jazzschmidt.apubsub;

/**
 * Indicates that no client name is associated with the given STOMP session id
 */
public class NoSuchClientException extends Exception {

    private final String sessionId;

    public NoSuchClientException(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getMessage() {
        return "No client registered with session id " + sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
