package com.github.jazzschmidt.apubsub.interceptor

import com.github.jazzschmidt.apubsub.events.ClientConnectedEvent
import com.github.jazzschmidt.apubsub.events.ClientDisconnectedEvent
import com.github.jazzschmidt.apubsub.events.StompClientEvent
import com.github.jazzschmidt.apubsub.interceptor.ConnectionEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import spock.lang.Specification

import static com.github.jazzschmidt.apubsub.extra.MessageBuilder.connect
import static com.github.jazzschmidt.apubsub.extra.MessageBuilder.disconnect

class ConnectionEventPublisherSpec extends Specification {

    def 'publishes an event when a client connects or disconnects'() {
        given:
        ApplicationEventPublisher eventPublisher = Mock()
        ChannelInterceptor interceptor = new ConnectionEventPublisher(eventPublisher)

        when:
        interceptor.preSend(message, Stub(MessageChannel))

        then:
        1 * eventPublisher.publishEvent({ StompClientEvent event ->
            event.class == eventType
            event.sessionId == sessionId
        })

        where:
        message              | eventType               | sessionId
        connect('abc123')    | ClientConnectedEvent    | 'abc123'
        disconnect('123456') | ClientDisconnectedEvent | '123456'
    }

}
