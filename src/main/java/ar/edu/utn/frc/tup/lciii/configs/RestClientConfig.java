package ar.edu.utn.frc.tup.lciii.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestClientConfig {
    private final static int TIME_OUT = 5000;


    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofMillis(TIME_OUT))
                .setConnectTimeout(Duration.ofMillis(TIME_OUT))
                .build();
    }
}