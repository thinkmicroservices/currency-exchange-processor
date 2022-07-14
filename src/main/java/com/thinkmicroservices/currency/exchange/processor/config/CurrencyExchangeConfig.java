package com.thinkmicroservices.currency.exchange.processor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyExchangeConfig {

    public static final String MESSAGE_QUEUE = "currency_msg_queue";
    public static final String REPLY_MESSAGE_QUEUE = "currency_reply_msg_queue";
    public static final String EXCHANGE = "currency_exchange";

    /* outbound messae queue */
    @Bean
    Queue msgQueue() {
        return new Queue(MESSAGE_QUEUE);
    }

    /* reply message queue */
    @Bean
    Queue replyQueue() {
        return new Queue(REPLY_MESSAGE_QUEUE);
    }

    /* exchange */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    /* bind outbound message queue to exchange
     */
    @Bean
    Binding msgBinding() {
        return BindingBuilder.bind(msgQueue())
                .to(topicExchange())
                .with(MESSAGE_QUEUE);
    }

    /* bind reply message queue to exchange
     */
    @Bean
    Binding replyBinding() {
        return BindingBuilder.bind(replyQueue())
                .to(topicExchange())
                .with(REPLY_MESSAGE_QUEUE);
    }
}
