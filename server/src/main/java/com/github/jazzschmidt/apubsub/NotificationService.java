package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.config.MessagingConfiguration;
import com.github.jazzschmidt.apubsub.messages.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Broadcasts {@link Notification} messages to all clients. Should be used for informational data.
 */
@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final String broadcastTopic;

    /**
     * @param messagingTemplate STOMP messaging facility
     * @param configuration     Messaging configuration
     * @see NotificationService
     */
    public NotificationService(SimpMessagingTemplate messagingTemplate, MessagingConfiguration configuration) {
        this.messagingTemplate = messagingTemplate;
        this.broadcastTopic = configuration.getBroadcastTopic();
    }

    /**
     * Broadcasts a text message to all clients.
     *
     * @param message informational text message
     */
    public void broadcast(String message) {
        broadcast(new Notification(message));
    }

    /**
     * Broadcasts a text message to all clients.
     *
     * @param message informational text message
     */
    public void broadcast(Notification message) {
        messagingTemplate.convertAndSend(broadcastTopic, message);
    }
}
