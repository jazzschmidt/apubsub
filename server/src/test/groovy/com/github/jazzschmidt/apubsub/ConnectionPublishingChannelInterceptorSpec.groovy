package com.github.jazzschmidt.apubsub


import org.springframework.context.ApplicationEventPublisher
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import spock.lang.Specification

import static com.github.jazzschmidt.apubsub.extra.MessageBuilder.connect
import static com.github.jazzschmidt.apubsub.extra.MessageBuilder.disconnect

class ConnectionPublishingChannelInterceptorSpec extends Specification {

    def 'publishes an event when a client connects or disconnects'() {
        given:
        ApplicationEventPublisher eventPublisher = Mock()
        ChannelInterceptor interceptor = new ConnectionPublishingChannelInterceptor(eventPublisher)

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
