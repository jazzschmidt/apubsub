package com.github.jazzschmidt.apubsub

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ClientApplicationSpec extends Specification {

    @Autowired
    ClientApplication application

    def 'context loads'() {
        expect:
        application != null
    }

}
