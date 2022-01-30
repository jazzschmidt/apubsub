package com.github.jazzschmidt.apubsub.messages;

/**
 * Broadcast message from a client that will be sent to all clients.
 */
@SuppressWarnings("unused")
public class Broadcast implements StereotypeMessage {
    public String message, clientName;
}
