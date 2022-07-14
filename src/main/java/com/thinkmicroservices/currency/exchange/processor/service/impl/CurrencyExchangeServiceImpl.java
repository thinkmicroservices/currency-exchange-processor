package com.thinkmicroservices.currency.exchange.processor.service.impl;

/**
 *
 * @author cwoodward
 */
import com.thinkmicroservices.currency.exchange.processor.model.CurrencyExchangeConversionResponse;
import com.thinkmicroservices.currency.exchange.processor.model.ISO4217CurrencyCode;
import com.thinkmicroservices.currency.exchange.processor.service.CurrencyExchangeException;
import com.thinkmicroservices.currency.exchange.processor.service.CurrencyExchangeService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    @Value("${currency.exchange.api.url}")
    String currencyExchangeAPIURL;

    @Value("${currency.exchange.api.key}")
    String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    
    /**
     * get the conversion map for the given currency code
     */
    public Map<String, BigDecimal> getConversionMap(ISO4217CurrencyCode currencyCode) throws CurrencyExchangeException {
        log.trace("Currency exchange api url:{}", currencyExchangeAPIURL);
        log.trace("apiKey: {}", apiKey);

        // guard that the api url exist
        if (currencyExchangeAPIURL == null) {
            throw new CurrencyExchangeException("currency exchange api url is NULL");
        }

        // guard that the api key exist
        if (apiKey == null) {
            throw new CurrencyExchangeException("currency exchange api key is NULL");
        }

        // call the https://www.exchangerate-api.com/ api
        try {
            // format the url with the api key and the currency code
            String url = String.format(currencyExchangeAPIURL, apiKey, currencyCode);
            log.trace("Currency exchange url:{}", url);

            // call the exchange rate api
            CurrencyExchangeConversionResponse response = restTemplate.getForObject(url, CurrencyExchangeConversionResponse.class);
            return response.getConversion_rates();

        } catch (Exception ex) {
            throw new CurrencyExchangeException("exception accessing currency exchange api", ex);
        }

    }

    /**
     * 
     * @param localCurrencyCode
     * @param targetCurrencyCode
     * @param amount
     * @return
     * @throws CurrencyExchangeException 
     */
    public BigDecimal convert(ISO4217CurrencyCode localCurrencyCode, ISO4217CurrencyCode targetCurrencyCode, BigDecimal amount)
            throws CurrencyExchangeException {
        
        Map<String, BigDecimal> conversionMap =  conversionMap =getConversionMap(localCurrencyCode);
               
        log.trace("conversionMap size: {}, currencyCode", conversionMap.size(), targetCurrencyCode);
       
        BigDecimal conversionFactor = conversionMap.get(targetCurrencyCode.toString());
        
        return amount.multiply(conversionFactor);
    }

}
