package com.github.jazzschmidt.apubsub.messages;

import java.util.Locale;

public interface StereotypeMessage {

    default String getType() {
        return getClass().getSimpleName().toLowerCase(Locale.ROOT);
    }

}
