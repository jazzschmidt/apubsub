package com.github.jazzschmidt.apubsub.messages;

public class Notification implements StereotypeMessage {
    public String message;

    public Notification(String message) {
        this.message = message;
    }
}
