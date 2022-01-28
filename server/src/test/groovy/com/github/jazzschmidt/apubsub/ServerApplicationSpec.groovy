package com.github.jazzschmidt.apubsub

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ServerApplicationSpec extends Specification {

    @Autowired
    ServerApplication application

    def 'context loads'() {
        expect:
        application != null
    }

}
