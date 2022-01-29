package com.github.jazzschmidt.apubsub;

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

        String topic = configuration.getBroadcastTopic();
        String message = String.format(MESSAGE_REGISTER, clientName);

        messagingTemplate.convertAndSend(topic, message);
    }

    public void dropClient(String stompSessionId) {
        String clientName = clientIds.remove(stompSessionId);

        String topic = configuration.getBroadcastTopic();
        String message = String.format(MESSAGE_DROP, clientName);

        messagingTemplate.convertAndSend(topic, message);
    }

    public String getClientName(String stompSessionId) {
        return clientIds.getOrDefault(stompSessionId, UNREGISTERED_CLIENT);
    }
}
