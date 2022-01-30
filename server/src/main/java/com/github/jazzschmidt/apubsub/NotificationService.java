package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.config.MessagingConfiguration;
import com.github.jazzschmidt.apubsub.messages.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final String broadcastTopic;

    public NotificationService(SimpMessagingTemplate messagingTemplate, MessagingConfiguration configuration) {
        this.messagingTemplate = messagingTemplate;
        this.broadcastTopic = configuration.getBroadcastTopic();
    }

    public void broadcast(String message) {
        broadcast(new Notification(message));
    }

    public void broadcast(Notification message) {
        messagingTemplate.convertAndSend(broadcastTopic, message);
    }
}
