package com.github.jazzschmidt.apubsub.messages

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class StereotypeMessageSpec extends Specification {

    def 'serializes messages with a type identifier'() {
        given:
        ObjectMapper serializer = new ObjectMapper()

        when:
        String jsonString = serializer.writeValueAsString(new TestMessage())
        Map<String, Object> json = serializer.readValue(jsonString, Map)

        then:
        json.get('type') == 'testmessage'
    }

    private class TestMessage implements StereotypeMessage {}

}
