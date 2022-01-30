package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.messages.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Associates client names with their STOMP session id and announces new connections and disconnects via the standard
 * messaging topic.
 */
@Service
public class ClientRegistrationService {

    private static final String UNREGISTERED_CLIENT = "unregistered";
    private static final String MESSAGE_REGISTER = "Client %s has entered the chat";
    private static final String MESSAGE_DROP = "Client %s has left the chat";

    /**
     * Associations of session ids and their respective client names
     */
    private final Map<String, String> clientIds = new HashMap<>();

    private final SimpMessagingTemplate messagingTemplate;
    private final MessagingConfiguration configuration;

    @Autowired
    public ClientRegistrationService(SimpMessagingTemplate messagingTemplate, MessagingConfiguration configuration) {
        this.messagingTemplate = messagingTemplate;
        this.configuration = configuration;
    }

    /**
     * Associates a distinct client name with a STOMP session id
     *
     * @param stompSessionId STOMP session id
     * @param clientName     client name
     */
    public void registerClient(String stompSessionId, String clientName) {
        clientIds.put(stompSessionId, clientName);
        broadcastNotification(String.format(MESSAGE_REGISTER, clientName));
    }

    /**
     * Disassociates a client name from a STOMP session id and announces the leave.
     *
     * @param stompSessionId STOMP session id
     * @throws NoSuchClientException when no client name is associated with that session id
     */
    public void dropClient(String stompSessionId) throws NoSuchClientException {
        if (!isClientRegistered(stompSessionId)) {
            throw new NoSuchClientException(stompSessionId);
        }

        String clientName = clientIds.remove(stompSessionId);
        broadcastNotification(String.format(MESSAGE_DROP, clientName));
    }

    /**
     * Broadcasts a {@link Notification} message.
     *
     * @param message informational message
     */
    private void broadcastNotification(String message) {
        String topic = configuration.getBroadcastTopic();
        Notification notification = new Notification(message);

        messagingTemplate.convertAndSend(topic, notification);
    }

    /**
     * Determines whether a STOMP session id is associated with a client name.
     *
     * @param stompSessionId STOMP session id
     * @return true if a client name is associated with given STOMP session id
     */
    public boolean isClientRegistered(String stompSessionId) {
        return clientIds.containsKey(stompSessionId);
    }

    /**
     * Retrieves the associated client name of the STOMP session id or a default value in case of an unregistered
     * client.
     *
     * @param stompSessionId STOMP session id
     * @return name of the client or `unregistered`
     */
    public String getClientName(String stompSessionId) {
        return clientIds.getOrDefault(stompSessionId, UNREGISTERED_CLIENT);
    }
}
