package com.github.jazzschmidt.apubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.logging.Logger;

/**
 * Intensively influenced by https://spring.io/guides/gs/messaging-stomp-websocket/ (Getting Started | Using WebSocket
 * to build an interactive web application)
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final ChannelInterceptor greetingChannelInterceptor;
    private final MessagingConfiguration messagingConfiguration;

    private final Logger log;

    @Autowired
    public WebSocketConfiguration(ChannelInterceptor greetingChannelInterceptor, MessagingConfiguration configuration) {
        this.greetingChannelInterceptor = greetingChannelInterceptor;
        this.messagingConfiguration = configuration;

        {
            log = Logger.getLogger(getClass().getName());
            log.info("Initialized messaging with the following properties: \n" + messagingConfiguration.toString());
        }
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
        registration.interceptors(greetingChannelInterceptor);
    }

}
