package org.uma.cloud.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableTask
@Configuration
@EnableConfigurationProperties(TestTaskProperties.class)
public class TestTaskConfiguration {

    @Autowired
    private TestTaskProperties testTaskProperties;


    @Bean
    public CommandLineRunner testTaskRunner() {
        return args -> {
            System.out.println("aaaaaaaaaaaaaa");

//            WebClient webClient = WebClient.create(testTaskProperties.getRestUrl());
//            webClient.get().uri("/")
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .subscribe(
//                            i -> System.out.println(i),
//                            e -> new RuntimeException(e),
//                            () -> System.out.println("end")
//                    );

        };
    }

}
