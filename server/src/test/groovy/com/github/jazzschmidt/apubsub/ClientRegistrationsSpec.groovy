package com.github.jazzschmidt.apubsub

import com.github.jazzschmidt.apubsub.messages.Notification
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.simp.SimpMessagingTemplate

@SpringBootTest
class ClientRegistrationsSpec extends ApplicationSpec {

    @Autowired
    ClientRegistrations registrations

    @SpringBean
    SimpMessagingTemplate messagingTemplate = Mock()

    @SuppressWarnings('GroovyAccessibility')
    def 'registers and announces new clients'() {
        given:
        def sessionId = '123456'
        def clientName = 'client'

        def initialClients = registrations.clientIds.size()

        when:
        registrations.registerClient(sessionId, clientName)

        then:
        1 * messagingTemplate.convertAndSend("/topic/$broadcast",
                { Notification n -> n.message.contains("$clientName has entered") })

        and:
        initialClients == 0
        registrations.clientIds.size() == 1
    }

    @SuppressWarnings('GroovyAccessibility')
    def 'unregisters and announces leaving clients'() {
        given:
        def sessionId = '123456'
        def clientName = 'client'

        registrations.registerClient(sessionId, clientName)
        def initialClients = registrations.clientIds.size()

        when:
        registrations.dropClient(sessionId)

        then:
        1 * messagingTemplate.convertAndSend("/topic/$broadcast",
                { Notification n -> n.message.contains("$clientName has left") })

        and:
        initialClients == 1
        registrations.clientIds.size() == 0
    }

}
