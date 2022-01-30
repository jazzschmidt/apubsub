package com.github.jazzschmidt.apubsub

import com.github.jazzschmidt.apubsub.config.MessagingConfiguration
import com.github.jazzschmidt.apubsub.messages.Notification
import org.springframework.messaging.simp.SimpMessagingTemplate
import spock.lang.Specification

class NotificationServiceSpec extends Specification {

    SimpMessagingTemplate messagingTemplate = Mock()

    def 'broadcasts a notification'() {
        given:
        def configuration = new MessagingConfiguration().tap {
            it.topics = new MessagingConfiguration.TopicConfiguration(broadcast: 'broadcast')
        }
        NotificationService service = new NotificationService(messagingTemplate, configuration)

        when:
        service.broadcast('hello world')

        then:
        1 * messagingTemplate.convertAndSend('/topic/broadcast', { Notification message ->
            message.message == 'hello world'
        })
    }

}
