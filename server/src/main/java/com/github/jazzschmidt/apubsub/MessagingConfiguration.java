package com.github.jazzschmidt.apubsub;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration(value = "messaging")
@ConfigurationProperties(prefix = "messaging")
@Validated
public class MessagingConfiguration {

    @Valid
    @NotNull(message = "You must declare topic names")
    private TopicConfiguration topics;

    private String stompEndpoint, appPrefix, topicPrefix;
    private List<String> allowedOriginPatterns;

    @Min(1_000L)
    private long disconnectDelay;

    {
        stompEndpoint = "/stomp";
        appPrefix = "/app";
        topicPrefix = "/topic";

        allowedOriginPatterns = List.of("*");

        disconnectDelay = 10_000L;
    }

    public String getStompEndpoint() {
        return stompEndpoint;
    }

    public void setStompEndpoint(String stompEndpoint) {
        this.stompEndpoint = stompEndpoint;
    }

    public String getAppPrefix() {
        return appPrefix;
    }

    public void setAppPrefix(String appPrefix) {
        this.appPrefix = appPrefix;
    }

    public String getTopicPrefix() {
        return topicPrefix;
    }

    public void setTopicPrefix(String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public String getRegistrationTopic() {
        return topicPrefix + "/" + topics.registration;
    }

    public String getBroadcastTopic() {
        return topicPrefix + "/" + topics.broadcast;
    }

    public List<String> getAllowedOriginPatterns() {
        return allowedOriginPatterns;
    }

    public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    public long getDisconnectDelay() {
        return disconnectDelay;
    }

    public void setDisconnectDelay(long disconnectDelay) {
        this.disconnectDelay = disconnectDelay;
    }

    public TopicConfiguration getTopics() {
        return topics;
    }

    public void setTopics(TopicConfiguration topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        String format = """
                {
                    stompEndpoint: %s
                    registration: %s
                    broadcast: %s
                }""";
        
        return String.format(format,
                stompEndpoint, getRegistrationTopic(), getBroadcastTopic());
    }

    public static class TopicConfiguration {
        @NotBlank
        private String registration;
        @NotBlank
        private String broadcast;

        public String getRegistration() {
            return registration;
        }

        public void setRegistration(String registration) {
            this.registration = registration;
        }

        public String getBroadcast() {
            return broadcast;
        }

        public void setBroadcast(String broadcast) {
            this.broadcast = broadcast;
        }
    }
}
