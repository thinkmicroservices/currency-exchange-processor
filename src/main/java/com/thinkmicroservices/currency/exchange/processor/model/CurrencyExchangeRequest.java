 
package com.thinkmicroservices.currency.exchange.processor.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
 

@Data
/**
 *
 * @author cwoodward
 */
public class CurrencyExchangeRequest {
    ISO4217CurrencyCode localCurrency; 
    ISO4217CurrencyCode targetCurrency; 
    BigDecimal localCurrencyValue; 
    

}
