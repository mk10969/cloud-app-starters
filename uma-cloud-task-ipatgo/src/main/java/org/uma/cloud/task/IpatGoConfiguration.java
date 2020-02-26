package org.uma.cloud.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


@EnableTask
@Configuration
@EnableConfigurationProperties(IpatGoProperties.class)
public class IpatGoConfiguration {

    Logger logger = LoggerFactory.getLogger(IpatGoConfiguration.class);

    @Autowired
    private IpatGoProperties ipatGoProperties;


    @Bean
    public CommandLineRunner testTaskRunner() {
        return args -> {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(ipatGoProperties.getRestUrl() + ipatGoProperties.getMode()))
                    .GET()
                    .build();

            httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        String message = "status code: " + response.statusCode();
                        if (response.statusCode() == 200) {
                            logger.info(message);
                            return response;
                        } else {
                            throw new RuntimeException(message);
                        }
                    })
                    .thenApply(HttpResponse::body)
                    .thenAccept(body -> logger.info("exit code: " + body))
                    .get();
        };
    }

}
