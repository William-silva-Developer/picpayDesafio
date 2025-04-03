package com.PicpaySimplificado.infra.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://util.devi.tools/api/v2/authorize")
            .build();
    }
}
