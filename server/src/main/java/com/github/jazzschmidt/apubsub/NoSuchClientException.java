package com.github.jazzschmidt.apubsub;

public class NoSuchClientException extends Exception {

    private final String clientName;

    public NoSuchClientException(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String getMessage() {
        return "No client registered with session id " + clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
