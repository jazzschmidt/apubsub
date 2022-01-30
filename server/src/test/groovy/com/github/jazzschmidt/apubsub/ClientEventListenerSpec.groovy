package com.github.jazzschmidt.apubsub

import com.github.jazzschmidt.apubsub.events.ClientDisconnectedEvent
import com.github.jazzschmidt.apubsub.events.ClientRegisteredEvent
import com.github.jazzschmidt.apubsub.events.ClientUnregisteredEvent
import spock.lang.Specification

class ClientEventListenerSpec extends Specification {

    ClientEventListener eventListener

    NotificationService notificationService = Mock(NotificationService)
    ClientRegistrations clientRegistrations = Mock()

    def setup() {
        eventListener = new ClientEventListener(notificationService, clientRegistrations)
    }

    def 'announces new clients'() {
        when:
        eventListener.onApplicationEvent(new ClientRegisteredEvent(this, '123456', 'client'))

        then:
        1 * notificationService.broadcast({ String message -> message.contains("client has entered") })
    }

    def 'announces leaving clients'() {
        when:
        eventListener.onApplicationEvent(new ClientUnregisteredEvent(this, '123456', 'test'))

        then:
        1 * notificationService.broadcast({ String message -> message.contains("test has left") })
    }

    def 'unregisters disconnecting clients'() {
        when:
        eventListener.onApplicationEvent(new ClientDisconnectedEvent(this, '123456'))

        then:
        clientRegistrations.isClientRegistered('123456') >> true
        1 * clientRegistrations.unregisterClient('123456')
    }

    def 'ignores unregistered disconnecting clients'() {
        when:
        eventListener.onApplicationEvent(new ClientDisconnectedEvent(this, '123456'))

        then:
        clientRegistrations.isClientRegistered('123456') >> false
        0 * clientRegistrations.unregisterClient('123456')
    }

}
