package com.github.jazzschmidt.apubsub.messages;

/**
 * Registration message that informs about a newly registered - and thus named - client
 */
public class Registration implements StereotypeMessage {
    public String clientName;
}
