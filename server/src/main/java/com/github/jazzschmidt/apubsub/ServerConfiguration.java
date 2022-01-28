package com.github.jazzschmidt.apubsub;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Intensively influenced by https://spring.io/guides/gs/messaging-stomp-websocket/ (Getting Started | Using WebSocket
 * to build an interactive web application)
 */
@Configuration
@ConfigurationProperties(prefix = "messaging")
@EnableWebSocketMessageBroker
public class ServerConfiguration implements WebSocketMessageBrokerConfigurer {

    public String registerTopic;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").withSockJS();
    }
}
