package com.thinkmicroservices.currency.exchange.processor.model;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class CurrencyExchangeConversionResponse {

    private String result;
    private String documentation;
    private String terms_of_use;
    private long time_last_update_unix;
    private String time_last_update_utc;
    private String time_next_update_unix;
    private String time_next_update_utc;
    private String base_code;
    private Map<String, BigDecimal> conversion_rates;
}
