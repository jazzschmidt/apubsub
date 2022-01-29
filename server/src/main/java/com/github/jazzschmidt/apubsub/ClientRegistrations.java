package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.messages.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientRegistrations {

    private static final String UNREGISTERED_CLIENT = "unregistered";
    private static final String MESSAGE_REGISTER = "Client %s has entered the chat";
    private static final String MESSAGE_DROP = "Client %s has left the chat";

    private final Map<String, String> clientIds = new HashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    private final MessagingConfiguration configuration;

    @Autowired
    public ClientRegistrations(SimpMessagingTemplate messagingTemplate, MessagingConfiguration configuration) {
        this.messagingTemplate = messagingTemplate;
        this.configuration = configuration;
    }

    public void registerClient(String stompSessionId, String clientName) {
        clientIds.put(stompSessionId, clientName);
        broadcastNotification(String.format(MESSAGE_REGISTER, clientName));
    }

    public void dropClient(String stompSessionId) throws NoSuchClientException {
        if (!isClientRegistered(stompSessionId)) {
            throw new NoSuchClientException(stompSessionId);
        }

        String clientName = clientIds.remove(stompSessionId);
        broadcastNotification(String.format(MESSAGE_DROP, clientName));
    }

    private void broadcastNotification(String message) {
        String topic = configuration.getBroadcastTopic();
        Notification notification = new Notification(message);

        messagingTemplate.convertAndSend(topic, notification);
    }

    public boolean isClientRegistered(String stompSessionId) {
        return clientIds.containsKey(stompSessionId);
    }

    public String getClientName(String stompSessionId) {
        return clientIds.getOrDefault(stompSessionId, UNREGISTERED_CLIENT);
    }
}
