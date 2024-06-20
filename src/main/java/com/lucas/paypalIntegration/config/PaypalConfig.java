package com.lucas.paypalIntegration.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {
    @Value("${paypal.client-id}")
    private String clientID;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.client-mode}")
    private String mode;

    @Bean
    public APIContext apiContext(){
        return  new APIContext(clientID,clientSecret,mode);
    }

}
