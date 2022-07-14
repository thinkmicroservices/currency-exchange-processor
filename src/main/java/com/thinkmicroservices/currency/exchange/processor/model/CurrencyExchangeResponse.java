
package com.thinkmicroservices.currency.exchange.processor.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cwoodward
 */
@Data
@NoArgsConstructor
public class CurrencyExchangeResponse extends CurrencyExchangeRequest{
 
   BigDecimal targetCurrencyValue;

   public  CurrencyExchangeResponse(CurrencyExchangeRequest request){
        this.localCurrency = request.localCurrency;
        this.targetCurrency = request.targetCurrency;
        this.localCurrencyValue=request.localCurrencyValue;
      
    }
   
}
