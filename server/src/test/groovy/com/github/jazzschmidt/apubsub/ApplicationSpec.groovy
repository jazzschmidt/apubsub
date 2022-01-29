package com.github.jazzschmidt.apubsub


import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationSpec extends Specification {

    @Value('${messaging.topics.registration}')
    String registration

    @Value('${messaging.topics.broadcast}')
    String broadcast

    def 'context loads'() {
        expect:
        registration == 'hello'
        broadcast == 'world'
    }

}
