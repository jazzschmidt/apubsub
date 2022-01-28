package com.github.jazzschmidt.apubsub;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Intensively influenced by https://spring.io/guides/gs/messaging-stomp-websocket/ (Getting Started | Using WebSocket
 * to build an interactive web application)
 */
@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String registerClient(Message message) {
        return String.format("Hello %s", message.getClientId());
    }

}
