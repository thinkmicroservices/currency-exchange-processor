package com.thinkmicroservices.currency.exchange.processor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkmicroservices.currency.exchange.processor.config.CurrencyExchangeConfig;
import com.thinkmicroservices.currency.exchange.processor.model.CurrencyExchangeRequest;
import com.thinkmicroservices.currency.exchange.processor.model.CurrencyExchangeResponse;
import com.thinkmicroservices.currency.exchange.processor.service.CurrencyExchangeException;
import com.thinkmicroservices.currency.exchange.processor.service.CurrencyExchangeService;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExchangeProcessor {

    ObjectMapper jsonMapper = new ObjectMapper();

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CurrencyExchangeService currencyExchangeService;

    @RabbitListener(queues = CurrencyExchangeConfig.MESSAGE_QUEUE)
    public void process(Message message) throws Exception {

        String incomingMessageJson = new String(message.getBody());
        log.trace("{} processing incoming json: {}", serviceName, incomingMessageJson);

        CurrencyExchangeRequest request = jsonMapper.readValue(message.getBody(), CurrencyExchangeRequest.class);

        log.trace("deserialized request: {}", request);

        CurrencyExchangeResponse currencyExchangeResponseMessage = new CurrencyExchangeResponse(request);

        BigDecimal exchangeRate = null;

        try {
            // lookup the exchange rate
            log.trace("lookup exchange rate");
            exchangeRate = currencyExchangeService.convert(request.getLocalCurrency(), request.getTargetCurrency(), request.getLocalCurrencyValue());
            log.trace("exchange rate =>{}", exchangeRate);
            
        } catch (CurrencyExchangeException ex) {
            
            log.error("exception looking up exchange rate {}", ex);
            throw new Exception("Currency Exchange Service exception: " + ex);
        }

        currencyExchangeResponseMessage.setTargetCurrencyValue(request.getLocalCurrencyValue().multiply(exchangeRate));

        log.trace("response: {}", currencyExchangeResponseMessage);

        String deserializedMessage = jsonMapper.writeValueAsString(currencyExchangeResponseMessage);
        log.trace("serialized response: {}", currencyExchangeResponseMessage);
        
        Message responseMessage = MessageBuilder
                .withBody(deserializedMessage.getBytes()).build();
                log.trace("request correlationId:{}", message.getMessageProperties().getCorrelationId());
                
        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());

       
        rabbitTemplate.sendAndReceive(CurrencyExchangeConfig.EXCHANGE, CurrencyExchangeConfig.REPLY_MESSAGE_QUEUE, responseMessage, correlationData);

        log.trace("responseMessage:{}", responseMessage);
        
        
    }
}
