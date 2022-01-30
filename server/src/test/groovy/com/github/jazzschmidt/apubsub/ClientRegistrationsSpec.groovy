package com.github.jazzschmidt.apubsub

import com.github.jazzschmidt.apubsub.events.ClientRegisteredEvent
import com.github.jazzschmidt.apubsub.events.ClientUnregisteredEvent
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class ClientRegistrationsSpec extends Specification {

    def 'registers new clients'() {
        given:
        def sessionId = '123456'
        def clientName = 'client'

        ApplicationEventPublisher eventPublisher = Mock()
        def registrations = new ClientRegistrations(eventPublisher)

        def initialRegistration = registrations.isClientRegistered(sessionId)

        when:
        registrations.registerClient(sessionId, clientName)

        then:
        def isRegistered = registrations.isClientRegistered(sessionId)

        !initialRegistration
        isRegistered

        and:
        1 * eventPublisher.publishEvent({ ClientRegisteredEvent event ->
            event.sessionId == sessionId
            event.clientName == clientName
        })
    }

    def 'unregisters clients'() {
        given:
        def sessionId = '123456'
        def clientName = 'client'

        ApplicationEventPublisher eventPublisher = Mock()
        def registrations = new ClientRegistrations(eventPublisher)
        registrations.registerClient(sessionId, clientName)

        def initialRegistration = registrations.isClientRegistered(sessionId)

        when:
        registrations.unregisterClient(sessionId)

        then:
        def isRegistered = registrations.isClientRegistered(sessionId)

        initialRegistration
        !isRegistered

        and:
        1 * eventPublisher.publishEvent({ ClientUnregisteredEvent event ->
            event.sessionId == sessionId
            event.clientName == clientName
        })
    }

}
