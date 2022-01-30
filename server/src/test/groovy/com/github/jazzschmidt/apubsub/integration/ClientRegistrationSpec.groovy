package com.github.jazzschmidt.apubsub.integration

import com.github.jazzschmidt.apubsub.MessageController
import com.github.jazzschmidt.apubsub.NotificationService
import com.github.jazzschmidt.apubsub.UnregisteredClientException
import com.github.jazzschmidt.apubsub.interceptor.ConnectionEventPublisher
import com.github.jazzschmidt.apubsub.messages.Broadcast
import com.github.jazzschmidt.apubsub.messages.Registration
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessagingException
import org.springframework.messaging.simp.SimpMessagingTemplate
import spock.lang.Specification

import static com.github.jazzschmidt.apubsub.extra.MessageBuilder.*

@SpringBootTest
class ClientRegistrationSpec extends Specification {

    @Autowired
    MessageController controller

    // Used to simulate disconnect message
    @Autowired
    ConnectionEventPublisher connectionEventPublisher

    @Value('#{@messaging.broadcastTopic}')
    String broadcastTopic

    @Autowired
    SimpMessagingTemplate messagingTemplate

    @SpringBean
    NotificationService notificationService = Mock()

    def 'rejects unregistered clients'() {
        given:
        String sessionId = '123456'
        Message<Broadcast> message = broadcast(sessionId, 'Hello world')

        when:
        controller.broadcast(message)

        then:
        def e = thrown(MessagingException)
        e.cause instanceof UnregisteredClientException
    }

    def 'lets registered clients broadcast messages'() {
        given:
        String sessionId = '123456'
        Message<Registration> register = registration(sessionId, 'client1')
        Message<Broadcast> message = broadcast(sessionId, 'Hello world')

        when:
        controller.registerClient(register)
        controller.broadcast(message)

        then:
        noExceptionThrown()
    }

    def 'announces client connections and leavings'() {
        given:
        String sessionId = '123456'
        Message<Registration> register = registration(sessionId, 'client1')
        Message<?> disconnect = disconnect(sessionId)

        when:
        controller.registerClient(register)
        connectionEventPublisher.preSend(disconnect, Mock(MessageChannel))

        then:
        noExceptionThrown()

        and:
        1 * notificationService.broadcast({ String notification ->
            notification == 'Client client1 has entered the chat'
        })
        1 * notificationService.broadcast({ String notification ->
            notification == 'Client client1 has left the chat'
        })
    }

}
