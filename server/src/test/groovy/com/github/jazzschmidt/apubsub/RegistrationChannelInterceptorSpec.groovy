package com.github.jazzschmidt.apubsub

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessagingException
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.GenericMessage
import spock.lang.Specification

import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE

class RegistrationChannelInterceptorSpec extends Specification {

    def 'rejects subscriptions from unregistered client'() {
        given:
        def sessionId = 'abc123'
        Message<?> subscribe = MessageBuilder.subscribe(sessionId)

        ClientRegistrationService registrationService = Mock()
        ChannelInterceptor interceptor = new RegistrationChannelInterceptor(registrationService)

        when:
        interceptor.preSend(subscribe, Stub(MessageChannel))

        then:
        1 * registrationService.isClientRegistered(sessionId) >> false
        thrown(MessagingException)
    }

    def 'permits subscriptions from registered clients'() {
        given:
        def sessionId = 'abc123'
        Message<?> subscribe = MessageBuilder.subscribe(sessionId)

        ClientRegistrationService registrationService = Mock()
        ChannelInterceptor interceptor = new RegistrationChannelInterceptor(registrationService)

        when:
        interceptor.preSend(subscribe, Stub(MessageChannel))

        then:
        1 * registrationService.isClientRegistered(sessionId) >> true
        noExceptionThrown()
    }

    class MessageBuilder {
        static GenericMessage<?> subscribe(String sessionId) {
            prototype(SUBSCRIBE, sessionId)
        }

        static GenericMessage<?> prototype(StompCommand command, String sessionId, Map<String, Object> headers = [:]) {
            new GenericMessage<>([], [stompCommand: command, simpSessionId: sessionId] + headers)
        }
    }

}
