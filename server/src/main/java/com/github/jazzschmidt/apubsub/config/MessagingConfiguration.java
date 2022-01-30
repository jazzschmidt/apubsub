package com.github.jazzschmidt.apubsub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration(value = "messaging")
@ConfigurationProperties(prefix = "messaging")
@Validated
public class MessagingConfiguration {

    /**
     * Configuration of both the registration and broadcast topic names
     */
    @Valid
    @NotNull(message = "You must declare topic names")
    private TopicConfiguration topics;

    private String stompEndpoint, appPrefix, topicPrefix;

    /**
     * Supports flexible patterns for specifying the origins for which cross-origin requests are allowed from a browser.
     * Please, refer to {@link CorsConfiguration#setAllowedOriginPatterns(List)} for format details and other
     * considerations.
     *
     * @see StompWebSocketEndpointRegistration#setAllowedOriginPatterns(java.lang.String...)
     */
    private List<String> allowedOriginPatterns;

    @Min(1_000L)
    private long disconnectDelay;

    // Default values
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

    /**
     * Returns the whole registration topic path
     *
     * @return topic path
     */
    public String getRegistrationTopic() {
        return topicPrefix + "/" + topics.registration;
    }

    /**
     * Returns the whole broadcast topic path
     *
     * @return topic path
     */
    public String getBroadcastTopic() {
        return topicPrefix + "/" + topics.broadcast;
    }

    /**
     * See {@link #allowedOriginPatterns}
     *
     * @return allowed origin patterns
     */
    public List<String> getAllowedOriginPatterns() {
        return allowedOriginPatterns;
    }

    /**
     * See {@link #allowedOriginPatterns}
     *
     * @param allowedOriginPatterns allowed origin patterns
     */
    public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    public long getDisconnectDelay() {
        return disconnectDelay;
    }

    public void setDisconnectDelay(long disconnectDelay) {
        this.disconnectDelay = disconnectDelay;
    }

    /**
     * See {@link #topics}
     *
     * @return Topic configuration
     */
    public TopicConfiguration getTopics() {
        return topics;
    }


    /**
     * @param topics Topic configuration
     * @see #topics
     */
    public void setTopics(TopicConfiguration topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        // Returns the most important settings
        String format = """
                {
                    stompEndpoint: %s
                    registration: %s
                    broadcast: %s
                }""";

        return String.format(format, stompEndpoint, getRegistrationTopic(), getBroadcastTopic());
    }

    public static class TopicConfiguration {
        /**
         * Topic name for registrations
         */
        @NotBlank
        private String registration;

        /**
         * Topic name for broadcast messages
         */
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
