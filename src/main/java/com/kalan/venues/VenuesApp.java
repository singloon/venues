package com.kalan.venues;

import com.kalan.venues.model.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class VenuesApp {
    private final Logger logger = LoggerFactory.getLogger(VenuesApp.class);

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(VenuesApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Credentials credentials() {
        String clientId = env.getProperty("CLIENT_ID");
        String secret = env.getProperty("CLIENT_SECRET");

        if (clientId == null || secret == null) {
            String credentialsErrorMessage = String.format("Missing foursquare client API credentials, client id %s client secret %s", clientId, secret);
            logger.error(credentialsErrorMessage);

            throw new RuntimeException(credentialsErrorMessage);
        }
        return new Credentials(clientId, secret);
    }
}
