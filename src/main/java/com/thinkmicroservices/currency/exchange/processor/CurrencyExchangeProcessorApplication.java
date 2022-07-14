package com.thinkmicroservices.currency.exchange.processor;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
 
/**
 * 
 * @author cwoodward
 */
 
@SpringBootApplication
@ComponentScan
@Slf4j
public class CurrencyExchangeProcessorApplication {

    @Value("${configuration.source:DEFAULT}")
    String configSource;
    
    @Value("${spring.application.name}")
    private String serviceName;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
      
        SpringApplication.run(CurrencyExchangeProcessorApplication.class, args);
    }

    /**
     *
     */
    @PostConstruct
    private void displayInfo() {
        log.info("Service-Name:{}, configuration.source={}", serviceName, configSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
