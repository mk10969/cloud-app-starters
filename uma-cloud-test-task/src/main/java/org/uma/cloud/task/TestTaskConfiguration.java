package org.uma.cloud.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@EnableTask
@Configuration
@EnableConfigurationProperties(TestTaskProperties.class)
public class TestTaskConfiguration {

    @Autowired
    private TestTaskProperties testTaskProperties;


    @Bean
    public CommandLineRunner testTaskRunner() {
        return args -> {
            WebClient webClient = WebClient.create(testTaskProperties.getRestUrl());
            webClient.get().uri("/")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            i -> System.out.println(i),
                            e -> new RuntimeException(e),
                            () -> System.out.println("end")
                    );

        };
    }

}
