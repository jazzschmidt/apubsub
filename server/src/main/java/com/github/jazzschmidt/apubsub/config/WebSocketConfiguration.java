// Influenced by https://spring.io/guides/gs/messaging-stomp-websocket/
package com.github.jazzschmidt.apubsub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.logging.Logger;

/**
 * Configures STOMP, WebSocket support and logs the most important settings upon instantiation.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final List<ChannelInterceptor> interceptors;
    private final MessagingConfiguration messagingConfiguration;

    private final Logger log;

    {
        log = Logger.getLogger(getClass().getName());
    }

    @Autowired
    public WebSocketConfiguration(List<ChannelInterceptor> interceptors, MessagingConfiguration configuration) {
        this.interceptors = interceptors;
        this.messagingConfiguration = configuration;

        // Partially output the messaging configuration
        log.info("Initialized messaging with the following properties: \n" + messagingConfiguration.toString());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(messagingConfiguration.getStompEndpoint())
                .setAllowedOriginPatterns(messagingConfiguration.getAllowedOriginPatterns().toArray(new String[]{}))
                .withSockJS()
                .setDisconnectDelay(messagingConfiguration.getDisconnectDelay());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(messagingConfiguration.getAppPrefix());
        config.enableSimpleBroker(messagingConfiguration.getTopicPrefix());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // https://stackoverflow.com/a/9863752/1087447
        registration.interceptors(interceptors.toArray(new ChannelInterceptor[0]));
    }

}
