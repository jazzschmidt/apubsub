package com.github.jazzschmidt.apubsub;

import com.github.jazzschmidt.apubsub.events.ClientDisconnectedEvent;
import com.github.jazzschmidt.apubsub.events.ClientRegisteredEvent;
import com.github.jazzschmidt.apubsub.events.ClientUnregisteredEvent;
import com.github.jazzschmidt.apubsub.events.StompClientEvent;
import com.github.jazzschmidt.apubsub.messages.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Main {@link ApplicationListener} that routes the {@link ClientRegisteredEvent}, {@link ClientUnregisteredEvent}, and
 * {@link ClientDisconnectedEvent} either to the {@link NotificationService} or the {@link ClientRegistrations} in order
 * to announce connecting and disconnecting clients as {@link Notification}.
 */
@Component
public final class ClientEventListener implements ApplicationListener<StompClientEvent> {

    private final NotificationService notificationService;
    private final ClientRegistrations clientRegistrations;

    private final Logger log;

    {
        log = Logger.getLogger(getClass().getName());
    }

    /**
     * @param notificationService The notification server
     * @param registrations       Client registration container
     * @see ClientEventListener
     */
    @Autowired
    public ClientEventListener(NotificationService notificationService, ClientRegistrations registrations) {
        this.notificationService = notificationService;
        clientRegistrations = registrations;
    }

    @Override
    public void onApplicationEvent(StompClientEvent event) {
        log.fine(String.format("Received %s from session %s", event.getClass().getSimpleName(), event.getSessionId()));

        if (event instanceof ClientRegisteredEvent) {
            announceNewClient((ClientRegisteredEvent) event);
            return;
        }

        if (event instanceof ClientUnregisteredEvent) {
            announceLeavingClient((ClientUnregisteredEvent) event);
            return;
        }

        if (event instanceof ClientDisconnectedEvent) {
            unregisterClient((ClientDisconnectedEvent) event);
        }
    }

    /**
     * Announces a new registered client.
     *
     * @param event details of the event
     */
    private void announceNewClient(ClientRegisteredEvent event) {
        notificationService.broadcast(String.format("Client %s has entered the chat", event.getClientName()));
    }

    /**
     * Announces the leaving of a client
     *
     * @param event details of the event
     */
    private void announceLeavingClient(ClientUnregisteredEvent event) {
        notificationService.broadcast(String.format("Client %s has left the chat", event.getClientName()));
    }

    /**
     * Unregisters a client and thus leads to it being announced as leaving afterwards.
     *
     * @param event details of the event
     */
    private void unregisterClient(ClientDisconnectedEvent event) {
        String sessionId = event.getSessionId();

        if (!clientRegistrations.isClientRegistered(sessionId)) {
            return; // Ignore unregistered clients
        }

        try {
            clientRegistrations.unregisterClient(sessionId);
        } catch (UnregisteredClientException e) {
            e.printStackTrace(); // Virtually impossible
        }
    }

}
