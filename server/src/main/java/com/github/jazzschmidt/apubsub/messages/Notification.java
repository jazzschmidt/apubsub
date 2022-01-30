package com.github.jazzschmidt.apubsub.messages;

/**
 * Notification message that will be sent to all clients.
 */
public class Notification implements StereotypeMessage {
    public String message;

    public Notification(String message) {
        this.message = message;
    }
}
