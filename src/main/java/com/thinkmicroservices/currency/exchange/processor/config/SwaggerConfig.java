 
package com.thinkmicroservices.currency.exchange.processor.config;

 
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author cwoodward
 */
public class SwaggerConfig {
    @Bean
  public OpenAPI currencyExchangeProcessorAPI() {
      return new OpenAPI()
              .info(new Info().title("Currency Exchange Processor API")
              .description("Back-end Processor service")
              .version("v0.0.1")
              .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  } 
}
