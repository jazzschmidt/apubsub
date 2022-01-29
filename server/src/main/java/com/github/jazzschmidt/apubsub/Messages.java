package com.github.jazzschmidt.apubsub;

@SuppressWarnings("unused")
public abstract class Messages {

    public static class Registration {
        public String clientName;
    }

    public static class Broadcast {
        public String message, clientName;
    }

}
