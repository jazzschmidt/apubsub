package com.github.jazzschmidt.apubsub.messages;

import java.util.Locale;

/**
 * Adds a `type` property to the serialized object. This simplifies parsing of messages on the consuming end.
 */
public interface StereotypeMessage {

    /**
     * Simple type identification name of this object.
     *
     * @return type identification
     */
    default String getType() {
        return getClass().getSimpleName().toLowerCase(Locale.ROOT);
    }

}
