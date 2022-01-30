package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.events.ClientRegisteredEvent;
import com.github.jazzschmidt.apubsub.events.ClientUnregisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Associates client names with their STOMP session id and announces new connections and disconnects via the standard
 * messaging topic.
 */
@Component
public class ClientRegistrations {

    /**
     * Associations of session ids and their respective client names
     */
    private final Map<String, String> clientIds = new HashMap<>();
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ClientRegistrations(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Associates a distinct client name with a STOMP session id
     *
     * @param stompSessionId STOMP session id
     * @param clientName     client name
     */
    public void registerClient(String stompSessionId, String clientName) {
        clientIds.put(stompSessionId, clientName);
        eventPublisher.publishEvent(new ClientRegisteredEvent(this, stompSessionId, clientName));
    }

    /**
     * Disassociates a client name from a STOMP session id and announces the leave.
     *
     * @param stompSessionId STOMP session id
     * @throws UnregisteredClientException when no client name is associated with that session id
     */
    public void unregisterClient(String stompSessionId) throws UnregisteredClientException {
        validateRegistration(stompSessionId);

        String clientName = clientIds.remove(stompSessionId);
        eventPublisher.publishEvent(new ClientUnregisteredEvent(this, stompSessionId, clientName));
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
    public String getClientName(String stompSessionId) throws UnregisteredClientException {
        validateRegistration(stompSessionId);
        return clientIds.get(stompSessionId);
    }

    private void validateRegistration(String stompSessionId) throws UnregisteredClientException {
        if (!isClientRegistered(stompSessionId)) {
            throw new UnregisteredClientException(stompSessionId);
        }
    }
}
