package com.github.jazzschmidt.apubsub;

/**
 * Indicates that no client name is associated with the given STOMP session id
 */
public class UnregisteredClientException extends Exception {

    private final String sessionId;

    /**
     * @param sessionId STOMP session id
     * @see UnregisteredClientException
     */
    public UnregisteredClientException(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getMessage() {
        return "No client registered with session id " + sessionId;
    }

    /**
     * @return STOMP session id
     */
    public String getSessionId() {
        return sessionId;
    }
}
