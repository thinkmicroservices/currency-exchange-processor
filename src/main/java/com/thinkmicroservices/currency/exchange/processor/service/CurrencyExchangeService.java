
package com.thinkmicroservices.currency.exchange.processor.service;

import com.thinkmicroservices.currency.exchange.processor.model.ISO4217CurrencyCode;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author cwoodward
 */
@Service
public interface CurrencyExchangeService {
    
    Map<String,BigDecimal> getConversionMap(ISO4217CurrencyCode localCurrency) throws CurrencyExchangeException;

    BigDecimal convert(ISO4217CurrencyCode localCurrencyCode, ISO4217CurrencyCode targetCurrencyCode, BigDecimal amount) throws CurrencyExchangeException;
}
