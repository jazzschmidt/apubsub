package com.github.jazzschmidt.apubsub

import com.github.jazzschmidt.apubsub.extra.MessageBuilder
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessagingException
import org.springframework.messaging.support.ChannelInterceptor
import spock.lang.Specification

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

}
